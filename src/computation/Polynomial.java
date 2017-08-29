package computation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

//TODO fix how the degree is being set or remove.	
//TODO maybe a better way to do multiplication.
/**
 * Represent a polynomial used for secret sharing.
 */
public class Polynomial {
    /** The field prime. */
    public static final int PRIME = 127;
    /** The coefficients of the polynomial. */
    private final int[] myCoefs;
    /** The degree of the polynomial. */
    private final int myDegree;

    /**
     * Class constructor, creates a random polynomial of desired degree.
     * @param theSecret player's secret share.
     * @param theDegree the degree of the polynomial.
     */
    public Polynomial(final int theSecret, final int theDegree) {
        if (theSecret >= PRIME || theSecret < 0)
            throw new IllegalArgumentException("The secret is outside the field!");
        if (theDegree < 0)
            throw new IllegalArgumentException("Invalid degree!");
        myCoefs = randPoly(theSecret, theDegree);
        myDegree = theDegree;
    }

    /**
     * Used by package methods.
     * @param theCoefs the coefficients.
     * @param theDegree the degree.
     */
    protected Polynomial(int[] theCoefs, int theDegree) {
        myCoefs = theCoefs;
        myDegree = theDegree;
    }
  
    /**
     * Used by class methods.
     * @param theDegree the degree.
     */
    private Polynomial(final int theDegree) {
        myDegree = theDegree;
        myCoefs = new int[myDegree + 1];
    }

    /**
     * Generate a random polynomial to represent the secret.
     * @param theSecret the secret.
     * @param theDegree the degree of the polynomial.
     * @return a random polynomial of desired degree.
     */
    private final int[] randPoly(final int theSecret, final int theDegree) {
        final Random rand = new SecureRandom();
        final int[] coefs = new int[theDegree + 1];
        coefs[0] = theSecret;
        for (int i = 1; i <= theDegree; i++) {
            coefs[i] = rand.nextInt(PRIME);
        }
        return coefs;
    }

    /**
     * Compute the polynomial using Horner's Rule applying mod operator for
     * field arithmetic.
     * @param theVal value to compute at.
     * @return the result of computation.
     */
    public final int modCompute(final int theVal) {
        int result = 0;
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
    public final int[] computeShares(final int theShareNum) {
        final int[] shares = new int[theShareNum];
        for (int i = 1; i <= theShareNum; i++) {
            shares[i - 1] = modCompute(i);
        }
        return shares;
    }

    /**
     * Create a new polynomial the result of this + theP2
     */
    public final Polynomial add(final Polynomial theP2) {
        Polynomial result = new Polynomial(Math.max(myDegree, theP2.myDegree));
        for(int i = 0; i <= myDegree; i++) { //this could be a problem.
            result.myCoefs[i] = posMod(myCoefs[i] + theP2.myCoefs[i]);
        }
        return result;
    }
    
    /**
     * Create a new polynomial the result of this * theP2.
     * @param theP2 the polynomial to multiply this polynomial by.
     * @return the result of multiplying this polynomial by theP2.
     */
    public final Polynomial multiply(final Polynomial theP2) {
        final int degree = myDegree + theP2.myDegree;
        final Polynomial result = new Polynomial(degree);
        for (int i = 0; i < myCoefs.length; i++) {
            for (int j = 0; j < theP2.myCoefs.length; j++) {
                result.myCoefs[i + j] = 
                        posMod(result.myCoefs[i + j] + (myCoefs[i] * theP2.myCoefs[j]));
            }
        }
        return result;
    }

    /**
     * Multiply this polynomial by a constant.
     * @param theConst the constant.
     */
    public final void multConst(final int theConst) {
        for (int i = 0; i < myCoefs.length; i++) {
            myCoefs[i] = posMod(myCoefs[i] * theConst);
        }
    }

    /**
     * Compute the modular inverse of theX and theY using BigInteger modInverse.
     * @param theXvthe x.
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
     * @return the remainder.
     */
    public static final int posMod(final int theX) {
        int r = theX % PRIME;
        if (r < 0) r += PRIME;
        return r;
    }

    public final boolean isZero() {
        for(int i =0; i < myCoefs.length; i++) {
            if(myCoefs[i] != 0) {
                return false;
            }
        }
        return true;
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
}