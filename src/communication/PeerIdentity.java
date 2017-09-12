package communication;

/**
 * Information needed to identify a peer.
 * @author Yaro Salo
 * @version 1
 */
public class PeerIdentity {

    private final int myNum;
    private final int mySerPort; 
    private final String myHost;
    
    public PeerIdentity(final int theNum, final int theSerPort, 
                        final String theHost) {
        myNum = theNum; 
        mySerPort = theSerPort;
        myHost = theHost;
    }

    public int getMyNum() {
        return myNum;
    }

    public int getMySerPort() {
        return mySerPort;
    }

    public String getMyHost() {
        return myHost;
    }
}