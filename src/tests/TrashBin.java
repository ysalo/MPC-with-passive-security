package tests;

import java.math.BigInteger;

/* THIS CLASS IS GARBAGE! 
 * ALL THE METHODS I WAS TOO SCARED TO DELETE ARE IN HERE.
 * MAYBE THEY WILL BE HELPFUL LETER?
 * IF NOT, 'EMPTY' THE BIN!
 */
public class TrashBin {
	//TODO maybe a map is better.
	//TODO this breaks really fast when the coefficients are large 
	//	   because the primitive int overflows: 
	//	   changed to long. could use BigInteger for huge numbers.
	/**
     * Interpolate the polynomial at 0 using Lagrange's interpolation
     * @param thePlayers the X values.
     * @param theShares the Y values.
     * @return the constant of the polynomial. 
     */
    public final static long interpolate(final int[] thePlayers, final long[] theShares) {
        if(thePlayers.length != theShares.length) 
            throw new IllegalArgumentException("Player - Share mismatch!");
        long result = 0;
        for (int i = 0; i < thePlayers.length; i++) {
            
            long term = theShares[i]; //get the player's share.
            for (int j = 0; j < thePlayers.length; j++) {
                if (j != i) {
                    //multiply the current share by the corresponding weight.
                    term = posMod(posMod(term * (0 - thePlayers[j])
                            * inverseMod(thePlayers[i] - thePlayers[j], preprocessor.TrustedInitializer.PRIME)));
                }  
            }
            result = posMod(result + term); //sum up the terms.
        }
        return result;
    }
    
    /**
     * Compute the modular inverse of theX and theY using BigInteger modInverse().
     * @param theX the x.
     * @param theY the y.
     * @throws BigInteger not invertible if there is no multiplicative inverse.
     * @return the multiplicative inverse of x and y.
     */
    public static final int inverseMod(final long theX, final long theY) {
        final BigInteger bX = BigInteger.valueOf(theX);
        final BigInteger bY = BigInteger.valueOf(theY);
        final int result = bX.modInverse(bY).intValue();
        return result;
    }
    
    /**
     * remainder >= 0.
     * @param theX theX mod PRIME.
     * @return the modulo.
     */
    public static final long posMod(final long theX) {
        long r = theX % preprocessor.TrustedInitializer.PRIME;
        if (r < 0) r += preprocessor.TrustedInitializer.PRIME;
        return r;
    }
    
    /*
     * Compute the polynomial using Horner's Rule applying mod operator for
     * field arithmetic.
     * @param theVal value to compute at.
     * @return the result of computation.
     *
    public final long modCompute(final long theVal) {
        long result = 0;
        // polynomial is in ascending order
        for (int i = myCoefs.length - 1; i >= 0; i--)
            result = posMod(result * theVal + myCoefs[i]);
        return result;
    }*/
}
