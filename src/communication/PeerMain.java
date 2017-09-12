package communication;

import java.util.Scanner;

public class PeerMain {
    private final static String HOST = "localhost";
    
    public static void main(String[] theArgs) {
        try {if (theArgs.length != 2) System.err.println("Server usage <player number> <port>");
        
            final int number = Integer.parseInt(theArgs[0]);
            //final String host = theArgs[1];
            final int port = Integer.parseInt(theArgs[1]);
            

            Peer peer = new Peer(number, 10, port, HOST);
            final Scanner scan = new Scanner(System.in);
            System.out.print("Enter client host: ");
            final String cHost = scan.nextLine();
            System.out.print("Enter the port: ");
            final int cPort = scan.nextInt();
            scan.nextLine();
            //Peer peer = new Peer(number, 10, port, HOST);
            peer.sendMessage(1212, cPort, cHost);
            scan.close();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
}
