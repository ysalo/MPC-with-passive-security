package communication;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Peer implements PeerInterface {

    @Override
    public void listen(final int theServerPort) {
        //Create a separate thread to listen for connections so that 
        //this peer could also act as a client. Without a new thread 
        //the peer would be stuck in the listening loop.
        (new Thread() { public void run() { 
            runServer(theServerPort);  
         }}).start();   
    }
    
    
    private void runServer(final int theServerPort) {
        
        //a server socket to listen for incoming connections.
        ServerSocket serverSocket;
        
        try {
            serverSocket = new ServerSocket(theServerPort);
            //serverSocket.setSoTimeout(20000);
            System.out.println("Server side waiting for connections...");
            while(true) { //Run the server indefinitely
                //accept the client connection
                final Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(0);
                //Start a new thread to handle each new connection.
                new ConnectionHandler(clientSocket).start();
            }
        } catch (final IOException e) {
            System.out.println("Cannot listen on port " + theServerPort); 
            e.printStackTrace();
        }

    }
    


    //TODO maybe keep this not sure yet. 
    public Socket openConnectionTo(final String theHost, final int thePort) {
        Socket socket;
        try {
            socket = new Socket(theHost, thePort);
        } catch (IOException e) {
            socket = null;
            e.printStackTrace();
        }
        return socket;
    }
    
    @Override //Should I close the connection right after the message is sent?
    public void sendMsgTo(final Object theMessage, final String theHost, final int thePort) {

        try {            
            final Socket socket = new Socket(theHost, thePort); //open connection to the peer.
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            //ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            
            os.writeObject(theMessage); //send the message to the peer.
            os.close(); //close the connection.
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       
    @Override
    public List<PeerInfo> registerPeers(String theFileName) {
        final List<PeerInfo> peers = new ArrayList<>();
        try {
            final File f = new File(theFileName);
            final Scanner scan = new Scanner(f);
            scan.nextLine(); //skip the header.
            while(scan.hasNext()) {
                final String[] s = scan.nextLine().split(",");
                final int number = Integer.parseInt(s[0]);
                final String name = s[1];
                final String host = s[2];
                final int port = Integer.parseInt(s[3]);
                peers.add(new PeerInfo(number, name, host, port));
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return peers;
    }
    
    /**
     * Inner class to handle server operations. 
     * @author Yaro Salo
     * @version 2
     */
    public class ConnectionHandler extends Thread{
        
        /** The socket on which the connection is established. */
        private final Socket mySocket; 
        
        /**
         * Class constructor. 
         * @param theSocket the socket on which the connection is open.
         */
        public ConnectionHandler(final Socket theSocket) {
            mySocket = theSocket;
        }
       
        public void run() { 
            //try with statement **try(){}** will close the writer after the
            try {    
                //ObjectOutputStream os = new ObjectOutputStream(mySocket.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(mySocket.getInputStream());
                
                
                Object obj = is.readObject();
                System.out.println(obj);    
                
                mySocket.close(); //close the socket after the connection terminates. 
                                  //the thread automatically shuts down after execution. 
            } catch (final IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
