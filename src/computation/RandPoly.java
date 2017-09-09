package computation;

import java.math.BigInteger;
import java.security.SecureRandom;

//TODO maybe a better way to do multiplication.
/**
 * Represent a polynomial used for secret sharing.
 */
public class RandPoly {    
    /** The field prime. */
    public static final int PRIME = 127;
    /** Random generator for the class. */
    private static final SecureRandom RAND = new SecureRandom();
    /** The coefficients of the polynomial. */
    private long[] myCoefs;
    /** The degree of the polynomial. */
    private final int myDegree;

    /**
     * Class constructor, creates a random polynomial of desired degree.
     * @param theSecret player's secret share.
     * @param theDegree the degree of the polynomial.
     */
    public RandPoly(final long theSecret, final int theDegree) {
        if (theSecret >= PRIME || theSecret < 0)
            throw new IllegalArgumentException("The secret is outside the field!");
        if (theDegree < 0)
            throw new IllegalArgumentException("Invalid degree!");
        myCoefs = randPoly(theSecret, theDegree);
        myDegree = theDegree;
    }
  
    /**
     * Used by class methods.
     * @param theDegree the degree.
     */
    private RandPoly(final int theDegree) {
        myDegree = theDegree;
        myCoefs = new long[myDegree + 1];
    }

    /**
     * Generate a random polynomial to represent the secret.
     * @param theSecret the secret.
     * @param theDegree the degree of the polynomial.
     * @return a random polynomial of desired degree.
     */
    private final long[] randPoly(final long theSecret, final int theDegree) {
        long[] stream = RAND.longs(theDegree, 0, PRIME).toArray();
        final long[] coefs = new long[theDegree + 1];
        coefs[0] = theSecret;
        for (int i = 1; i <= stream.length; i++) {
            coefs[i] = stream[i - 1];
        }
        //make sure that the polynomial is not of degree < t.
        //ex: f(x) = 2 + x + 0x^2 is of degree 1.
        while(coefs[theDegree] == 0) coefs[theDegree] = RAND.longs(1, 0, PRIME).toArray()[0]; 
        return coefs;
    }

    /**
     * Compute the polynomial using Horner's Rule applying mod operator for
     * field arithmetic.
     * @param theVal value to compute at.
     * @return the result of computation.
     */
    public final long modCompute(final long theVal) {
        long result = 0;
        // polynomial is in ascending order
        for (int i = myCoefs.length - 1; i >= 0; i--)
            result = posMod(result * theVal + myCoefs[i]);
        return result;
    }

    /**
     * Compute the shares of the polynomial.
     * @param theShareNum the number of shares desired.
     * @return array of shares.
     */
    public final long[] computeShares(final int theShareNum) {
        final long[] shares = new long[theShareNum];
        for (int i = 1; i <= theShareNum; i++) {
            shares[i - 1] = modCompute(i);
        }
        return shares;
    }

    /**xxxxxxxxxxxxxx
     * Create a new polynomial the result of this + theP2.
     * @param theP2 the polynomial to add to this polynomial.
     * @return the result of addition.
     */
    public final RandPoly add(final RandPoly theP2) {
        RandPoly result = new RandPoly(Math.max(myDegree, theP2.myDegree));
        for(int i = 0; i <= myDegree; i++) { //this could be a problem.
            result.myCoefs[i] = posMod(myCoefs[i] + theP2.myCoefs[i]);
        }
        return result;
    }
    
    /**xxxxxxxxxxxxxxx
     * Create a new polynomial the result of this * theP2.
     * @param theP2 the polynomial to multiply this polynomial by.
     * @return the result of multiplying this polynomial by theP2.
     */
    public final RandPoly multiply(final RandPoly theP2) {
        final int degree = myDegree + theP2.myDegree;
        final RandPoly result = new RandPoly(degree);
        for (int i = 0; i < myCoefs.length; i++) {
            for (int j = 0; j < theP2.myCoefs.length; j++) {
                result.myCoefs[i + j] = 
                        posMod(result.myCoefs[i + j] + (myCoefs[i] * theP2.myCoefs[j]));
            }
        }
        return result;
    }

    /**xxxxxxxxxxxx
     * Multiply this polynomial by a constant.
     * @param theConst the constant.
     */
    public final void multConst(final int theConst) {
        for (int i = 0; i < myCoefs.length; i++) {
            myCoefs[i] = posMod(myCoefs[i] * theConst);
        }
    }

    /**
     * Compute the modular inverse of theX and theY using BigInteger modInverse().
     * @param theX the x.
     * @param theY the y.
     * @throws BigInteger not invertible if there is no multiplicative inverse.
     * @return the multiplicative inverse of x and y.
     */
    public static final int inverseMod(final int theX, final int theY) {
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
        long r = theX % PRIME;
        if (r < 0) r += PRIME;
        return r;
    }
    
    /**
     * String representation of a polynomial in ascending order of degree.
     * example: [1,2,3,4] f(x) = 1 + 2x + 3x^2 + 4x^3
     */
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("f(x) = ");
        for (int i = 0; i < myCoefs.length; i++) {
            if (myCoefs[i] == 0) continue;
            else {
                if (i == 0) sb.append(myCoefs[i]);
                else if (i == 1 && myCoefs[i] != 1) sb.append(" + " + myCoefs[i] + "x");
                else if (i == 1 && myCoefs[i] == 1) sb.append(" + " + "x");
                else if (i > 1  && myCoefs[i] != 1) sb.append(" + " + myCoefs[i] + "x^" + i);
                else if (i > 1  && myCoefs[i] == 1) sb.append(" + " + "x^" + i);
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] theArgs) {
        
    }
}