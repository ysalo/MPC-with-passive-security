package communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CEPS.Player;

//TODO figure out how to add peers to the network. 

/**
 * Represent a peer in a peer-to-peer network.
 * @author Yaro Salo
 * @version 1
 */
public class Peer {

    /** The number of players. */ 
    private static final int NUMPLAYER = 2;
    /** Maximum numbers of peers that can connect. */
    private final int myMaxPeers;
    /** List of connected peers. */ 
    private final List<PeerIdentity> myKnownPeers;
    /** The server socket. */
    private final PeerServer myPeerServer;
    /** Info needed to identify a peer. */
    private final PeerIdentity myPeerId;
    /** The player. */
    private final Player myPlayer;
    

    /**
     * Class constructor. 
     * @param theName name identifier.
     * @param theServerPort the port on which the peer will listen for connections.
     * @param theMaxPeers maximum number of peers that can connect at one time.
     */
    public Peer(final int thePeerNum, final int theMaxPeers,
                 final int theServerPort ,final String theHost) {
        myMaxPeers = theMaxPeers;
        myKnownPeers = new ArrayList<>();
        myPeerServer = new PeerServer();
        myPeerId = new PeerIdentity(thePeerNum, theServerPort, theHost);
        myPlayer = new Player(NUMPLAYER); 
        System.out.println("In the PEER\n-------------------------------------\n");
        for(long x: myPlayer.getMyShares()) System.out.println("The shares: " + x); 
        registerPeers();
        
    }
      
    
    public void sendShares() {
        
        for(PeerIdentity pi: myKnownPeers) {
            final int pNum = pi.getMyNum();
            sendMessage(pNum, pi.getMySerPort(), pi.getMyHost(), myPlayer.getMyShares()[pNum - 1]);   
        }
    }
    
    /**
     * Add known peers to the peer list. 
     */
    private void registerPeers() {
        final File f = new File("../files/knownPeers.csv");
        try {
            final Scanner scan = new Scanner(f);
            while(scan.hasNext()) {
                final String[] s = scan.nextLine().split(",");
                addPeer(Integer.parseInt(s[0]), Integer.parseInt(s[1]), s[2]);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Begin listening for connections.
     */
    public void startListening() {
        
        //Create a separate thread to listen for connections so that 
        //this peer could also act as a client. Without a new thread 
        //the peer would be stuck in the listening loop.
        (new Thread() { public void run() { 
            try {
                myPeerServer.runServer();
            } catch (SocketException e) {
                e.printStackTrace();
            }  
         }}).start();
    }
    
    /**
     * Add a peer to the know list of connected peers.
     * @param thePort the listen port of the peer.
     * @param theHost the host of the peer.
     */
    public void addPeer(final int thePeerNum, final int thePort, final String theHost) {
        
        if(myKnownPeers.size() <= myMaxPeers ){ 
            final PeerIdentity pi = new PeerIdentity(thePeerNum, thePort, theHost);
            myKnownPeers.add(pi);
        }
    }
    
    public void sendMessage(final int theNumber, final int thePort,
                            final String theHost, final long share) {
        try (
                
                final Socket socket = new Socket(theHost, thePort);
                final PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                final BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                final BufferedReader inputReader = new BufferedReader( new InputStreamReader(System.in));
            ) {

                writer.println(share);
                System.out.println();
            } catch (UnknownHostException e) {
                System.err.println("Who dat" + theHost + "?");
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " + theNumber);
            } 
    }
    
    //GETTERS
    
    /**
     * @return the server port.
     */
    public int getServerPort() {
        return myPeerId.getMySerPort();
    }
    
    public Player getPlayer() {
        return myPlayer;
    }
    /**
     * @return the host of this peer.
     */
    public String getHost() {
        return myPeerId.getMyHost();
    }
    
    /**
     * @return the list of connected peers.
     */
    public List<PeerIdentity> getPeers() {
        return myKnownPeers;
    }
    
    @Override
    public String toString() {
        return new String("Peer Number: " + myPeerId.getMyNum() + 
                          "\nListen Port: " + myPeerId.getMySerPort() + 
                          "\nPeer Host: " + myPeerId.getMyHost());        
    }

    /**
     * Implementation of server behavior of a peer.
     * @author Yaro Salo
     * @version 1
     */
    private class PeerServer {
        
        public void runServer() throws SocketException{
            
            //a server socket to listen for incoming connections.
            ServerSocket serverSocket;
            
            try {
                serverSocket = new ServerSocket(myPeerId.getMySerPort());
                //serverSocket.setSoTimeout(20000);
                System.out.println("Server side waiting for connections...");
                while(true) { //Run the server indefinitely
                    //accept the client connection
                    final Socket clientSocket = serverSocket.accept();
                    clientSocket.setSoTimeout(0);
                    //Start a new thread to handle each new connection.
                    new ServerHandler(clientSocket).start();
                }
            } catch (final IOException e) {
                System.out.println("Cannot listen on port " + myPeerId.getMySerPort()); 
                e.printStackTrace();
            }

        }
    }
    
    /**
     * Inner class to handle server operations. 
     * @author Yaro Salo
     * @version 2
     */
    public class ServerHandler extends Thread{
        
        /** The socket on which the connection is established. */
        private final Socket mySocket; 
        
        /**
         * Class constructor. 
         * @param theSocket the socket on which the connection is open.
         */
        public ServerHandler(final Socket theSocket) {
            mySocket = theSocket;
        }
       
        public void run() { 
            //try with statement **try(){}** will close the writer after the connection is closed.
            //writer writes messages to the the client
            try (final PrintWriter writer = new PrintWriter(mySocket.getOutputStream(), true);  
                    final BufferedReader reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));)
            {    
                String temp;
                long share; //read the one line of the massage. 
                while((temp = reader.readLine()) != null) {
                    share = Long.parseLong(temp);
                    myPlayer.addShare(share);
//                    System.out.println("Incoming message: " + message); //print one line of the client message.
//                    //message = message.toUpperCase(); //capitalize client input
//                    writer.println(message);         //send the input back to the client.
                }
                
                mySocket.close(); //close the socket after the connection terminates. 
                                  //the thread automatically shuts down after execution. 
            } catch (final IOException e) {
                e.printStackTrace();
            }

        }
    }
}