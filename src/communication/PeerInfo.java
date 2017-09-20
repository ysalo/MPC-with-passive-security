package communication;

import java.io.Serializable;

/**
 * Encapsulate identifying data of a peer.
 * @author Yaro Salo
 * @version 2
 */
public class PeerInfo implements Serializable {
    
    
    private static final long serialVersionUID = -2359222719693266557L;
    private String myName;
    private String myHost;
    private int myNumber;
    private int myServerPort;
    
    public PeerInfo(final int theNumber, final String theName, 
                    final String theHost,final int theServerPort) {
        myName = theName;
        myHost = theHost;
        myNumber = theNumber;
        myServerPort = theServerPort;
    }
    
    
    public String getMyName() {
        return myName;
    }

    public String getMyHost() {
        return myHost;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public int getMyServerPort() {
        return myServerPort;
    }
    
    public void setMyName(final String theName) {
        myName = theName;
    }
    
    public void setMyHost(final String theHost) {
        myHost = theHost;
    }
    
    public void setMyNumber(final int theNumber) {
        myNumber = theNumber;
    }
    
    public void setMyServerPort(final int theServerPort) {
        myServerPort = theServerPort;
    }
    
    @Override 
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nPeer Information \n---------------\n");
        sb.append("Name: ");
        sb.append(myName);
        sb.append("\nNumber: ");
        sb.append(myNumber);
        sb.append("\nHost: ");
        sb.append(myHost);
        sb.append(" \nPort: ");
        sb.append(myServerPort);
        sb.append("\n---------------");
        return sb.toString();
    }
}
