package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent a peer in a peer-to-peer network.
 * @author Yaro Salo
 * @version 1
 */
public class Peer {

    /** Name identifier of a peer. */
    private final String myName;
    /** The port on which the peer will listen to for connections. */
    private final int myServerPort;
    /** Maximum numbers of peers that can connect. */
    private final int myMaxPeers;
    /** List of connected peers. */ 
    private final List<Peer> myKnownPeers;
    /** The server socket. */
    private final PeerServer myPeerServer;
    /** The client socket. */
   // private final PeerClient myPeerClient;
    /**
     * Class constructor. 
     * @param theName name identifier.
     * @param theServerPort the port on which the peer will listen for connections.
     * @param theMaxPeers maximum number of peers that can connect at one time.
     */
    public Peer(final String theName, final int theServerPort, final int theMaxPeers) {
        myName = theName;
        myServerPort = theServerPort;
        myMaxPeers = theMaxPeers;
        myKnownPeers = new ArrayList<>();
        myPeerServer = new PeerServer(theServerPort);
        //myPeerClient = new PeerClient();
    }
    public void listenLoop() {
        try {
            myPeerServer.runServer();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sendMessage(final String theHost, final int thePort, 
                            final String theName) {
        try (
                final Socket socket = new Socket(theHost, thePort);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                BufferedReader inputReader = new BufferedReader( new InputStreamReader(System.in));
            ) {
                String userInput;
                while (!(userInput = inputReader.readLine()).equals("quit")) {
                    userInput = theName +  " says: " + userInput;
                    writer.println(userInput);
                    //System.out.println("Server says: " + reader.readLine());
                }
            } catch (UnknownHostException e) {
                System.err.println("Who dat" + theHost + "?");
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " + theName);
            } 
    }
    
}
