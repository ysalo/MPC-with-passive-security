package communication;

import java.util.Scanner;

public class PeerMain {
    
    
    public static void main(String[] theArgs) {
        try {if (theArgs.length != 3) System.err.println("Server usage <name> <host> <port>");
        
        final String name = theArgs[0];
        final String host = theArgs[1];
        final int port = Integer.parseInt(theArgs[2]);
        
        
        Peer peer = new Peer(name, port, 10);
        System.out.println("before thread");
        //Crucial line runs the listen loop of this peer in a new thread.
        (new Thread() { public void run() { peer.listenLoop(); }}).start();
        final Scanner scan = new Scanner(System.in);
        System.out.print("Enter client name: ");
        final String cName = scan.nextLine();
        System.out.print("Enter client host: ");
        final String cHOst = scan.nextLine();
        System.out.print("Enter the port: ");
        final int cPOrt = scan.nextInt();
       
        peer.sendMessage(cHOst, cPOrt, cName);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
