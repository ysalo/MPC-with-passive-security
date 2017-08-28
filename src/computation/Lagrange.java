package computation;

//TODO this works but it's a mess.
/**
 * Implementation of Lagrange Interpolation.
 * 
 * @author Yaro Salo
 * @version 1
 */
public class Lagrange {

    /** Prevent instantiation. */
    private Lagrange() {
    }

    /**
     * Lagrange interpolation for secret sharing.
     * @param thePlayers the x values.
     * @param theShares the y values.
     * @return the reconstructed polynomial.
     */
    public final static Polynomial interpolate(final int[] thePlayers, final int[] theShares) {
        Polynomial result = null;
        int idx = 0;
        for (int i : thePlayers) {
            Polynomial temp = null;
            for (int j : thePlayers) {
                if (i != j) {
                    if (temp == null) temp = new Polynomial(new int[] { -j, 1 }, 1);
                    else temp = temp.multiply(new Polynomial(new int[] { -j, 1 }, 1));
                    temp.multConst(Polynomial.inverseMod(i - j, Polynomial.PRIME));
                }
            }
            temp.multConst(theShares[idx++]);
            if (result == null) result = temp;
            else result = result.add(temp);
        }
        return result;
    }
}
