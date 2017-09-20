package communication;

import java.util.List;

public interface PeerInterface {

    /**
     * Listen and accept incoming connections in a separate thread. 
     * When a connections is accepted a new thread is started to
     * handle that connection. Essentially the server behavior of a Peer.
     * @param theServerPort is the port to listen on.
     */
    public void listen(final int theServerPort);
    
    /**
     * Attempts to send a message to the specified host on the specified port.
     * Essentially the client behavior of a Peer.
     * @param theMessage the message to send, could be any object.
     * @param theHost the host to send the massage to.
     * @param theServerPort the port on which the host listens for incoming messages.
     */
    public void sendMsgTo(final Object theMessage, final String theHost, final int theServerPort); 
    
    /**
     * Reads a file of peer hosts and port.
     * @param theFileName the file to read.
     * @return a map of {host:port} key:value pairs.
     */
    public List<PeerInfo> registerPeers(final String theFileName);
    
}
