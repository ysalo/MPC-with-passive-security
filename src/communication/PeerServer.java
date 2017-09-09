package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Implementation of server behavior of a peer.
 * @author Yaro Salo
 * @version 1
 */
public class PeerServer {
    
    /** The port on which the server will listen. */
    final private int myPort;
    
    /**
     * Class constructor.
     * @param thePort
     */
    public PeerServer(final int thePort){
        myPort = thePort;
    }

    public void runServer() throws SocketException{
        
        //a server socket to listen for incoming connections.
        ServerSocket serverSocket;
        
        try {
            
            serverSocket = new ServerSocket(myPort);
            //serverSocket.setSoTimeout(20000);
            System.out.println("Server side waiting for connections...");
            while(true) { //Run the server indefinitely
                //accept the client connection
                final Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(0);
                //Start a new thread to handle the each new connection.
                new ServerHandler(clientSocket).start();
            }
        } catch (final IOException e) {
            System.out.println("Cannot listen on port " + myPort); 
            e.printStackTrace();
        }

    }
}
