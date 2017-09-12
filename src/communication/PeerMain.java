package communication;

import java.util.Scanner;

import communication.Peer.PeerIdentity;

public class PeerMain {
    private final static String HOST = "localhost";
    
    public static void main(String[] theArgs) {
        try {if (theArgs.length != 2) System.err.println("Server usage <player number> <port>");
        
            final int number = Integer.parseInt(theArgs[0]);
            //final String host = theArgs[1];
            final int port = Integer.parseInt(theArgs[1]);
            
            
            Peer peer = new Peer(number, 10, port, HOST);
            for(PeerIdentity pi: peer.getPeers()) {
                System.out.println("Peer Id Number: " + pi.getMyNum() + 
                        "\nPeer Listen Port: " + pi.getMySerPort() + 
                        "\nPeer Host: " + pi.getMyHost());
                System.out.println("------------------------------------------------------------------");
                
            }
            final Scanner scan = new Scanner(System.in);
//            System.out.print("Enter client name: ");
//            final String cName = scan.nextLine();
//            System.out.print("Enter client host: ");
//            final String cHOst = scan.nextLine();
            System.out.print("Enter the port: ");
            final int cPort = scan.nextInt();
            scan.nextLine();
            peer.sendMessage(number, cPort, HOST);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
