package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        //try with statement **try(){}** will close the reader and writer after the connection is closed.
        //writer write messages to the the client
        //reader reads messages for the client
        try (final PrintWriter writer = new PrintWriter(mySocket.getOutputStream(), true);  
                final BufferedReader reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));)
        {
            String message; //read the one line of the massage. 
            while((message = reader.readLine()) != null) {
                System.out.println("Incoming message: " + message); //print one line of the client message.
                //message = message.toUpperCase(); //capitalize client input
                writer.println(message);         //send the input back to the client.
            }
            
            mySocket.close(); //close the socket after the connection terminates. 
                              //the thread automatically shuts down after execution. 
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }
}