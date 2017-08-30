package computation;
//TODO maybe a map is better.
/**
 * Implementation of Lagrange Interpolation.
 * 
 * @author Yaro Salo
 * @version 2
 */
public class Lagrange {

    private Lagrange() {
        // Prevent instantiation.
    }
    
    /**
     * Interpolate the polynomial at 0 using Lagrange's interpolation
     * @param thePlayers the X values.
     * @param theShares the Y values.
     * @return the constant of the polynomial. 
     */
    public final static int interpolate(final int[] thePlayers, final int[] theShares) {
        if(thePlayers.length != theShares.length) 
            throw new IllegalArgumentException("Player - Share mismatch!");
        int result = 0;
        for (int i = 0; i < thePlayers.length; i++) {
            int term = theShares[i]; //get the player's share.
            for (int j = 0; j < thePlayers.length; j++) {
                if (j != i)
                    //multiply the current share by the corresponding weight.
                    term = RandPoly.posMod(term * (0 - thePlayers[j])
                            * RandPoly.inverseMod(thePlayers[i] - thePlayers[j], RandPoly.PRIME));
            }
            result = RandPoly.posMod(result + term); //sum up the terms.
        }
        return result;
    }
    
    public static final void main(String[] args) {
        final RandPoly p = new RandPoly(124, 9);
        int[] pl = new int[] {1,2,3,4,5,6,7,8,9,10};
        int[] s = p.computeShares(5);
        int r = interpolate(pl,s);
        System.out.println(p);
        System.out.println(r);
        
    }
}
