package computation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

//TODO fix how the degree is being set.	
//TODO generate a prime randomly maybe.
//TODO maybe a better way to do multiplication.
//TODO make a better implementation of inverse mod.
//TODO probably prevent negative instantiation.
/**
 * Represent a polynomial used for secret sharing.
 */
public class Polynomial {
	/** The field prime. */
	private static final int PRIME = 127; 
	/** The coefficients of the polynomial. */
	private final int[] myCoefs;
	/** The degree of the polynomial. */
	private final int myDegree;

	/**
	 * Class constructor.
	 * @param theDegree degree of polynomial.
	 * @param theCoefs the coefficients of the polynomial.
	 */
	public Polynomial(final int theDegree, final int[] theCoefs) {
		myDegree = theDegree;
		myCoefs = theCoefs;
	}
	
	public Polynomial(final int theDegree){
		myDegree = theDegree;
		myCoefs = new int[myDegree + 1]; //number of coefficients is +1 the degree
	}
	
    /**
     * @return the degree of this polynomial.
     */
    public final int getDegree() {
    	return myDegree;
    }
	
    /**
     * @return the coefficients of this polynomial.
     */
    public final int[] getCoefs() {
    	return myCoefs;
    }
	
	/**
	 * Compute the polynomial using Horner's Rule
	 * and applying mod operator for field arithmetic.
	 * @param theVal value to compute at.
	 * @return the result of computation.
	 */
    public final int modCompute(final int theVal) {
    	if(theVal <= 0)
        	throw new IllegalArgumentException("Cannot evaluate at less than 1!");
    	int result = 0;
        
    	//Since this polynomial is in ascending order we want to evaluate it 
    	//in the reverse order.
        for (int i = myCoefs.length - 1; i >= 0; i--)
            result = posMod(result * theVal + myCoefs[i]);
        return result;
    }
    
    /**
     * Generate a random polynomial to represent the secret (constant)
     * term.
     * @param theConst the secret.
     * @param theDegree the degree of the polynomial.
     * @return a random polynomial of desired degree.
     */
    public static final Polynomial randPoly(final int theConst, final int theDegree) {
    	
    	if(theConst >= PRIME || theConst < 0) {
    		throw new IllegalArgumentException("The secret is outside the field");
    	}
    	
    	final Random rand = new SecureRandom();
    	//one more coefficient than the degree 
    	final int[] coefs = new int[theDegree + 1]; 
    	coefs[0] = theConst;
    	for(int i = 1; i <= theDegree; i++) {
    		coefs[i] = rand.nextInt(PRIME);
    	}
    	final Polynomial p = new Polynomial(theDegree, coefs);
    	return p;
    }
    
    /**
     * Compute the shares of the polynomial.
     * @param theShareNum the number of shares desired.
     * @return array of shares.
     */
    public final int[] computeShares(final int theShareNum) {
    	final int[] shares = new int[theShareNum];
    	//polynomial(0) = the secret
    	for(int i = 1; i <= theShareNum; i++) {
    		shares[i - 1] = modCompute(i);
    	}
    	return shares;
    }
    
    /**
     * Compute the modular inverse of theX and theY using 
     * BigInteger method.
     * @param theX the x.
     * @param theY the y.
     * @throws BigInteger not invertible if there is  no
     * multiplicative inverse.
     * @return the multiplicative inverse of x and y.
     */
    public static final int inverseMod(final int theX, final int theY) {
    	final BigInteger bX = BigInteger.valueOf(theX);
    	final BigInteger bY = BigInteger.valueOf(theY);
    	final int result = bX.modInverse(bY).intValue();
    	return result;
    }
    
    /**
     * Method that multiplies this polynomial by theP2.
     * @param theP2 the polynomial to multiply this polynomial by.
     * @return the result of multiplying this polynomial by theP2.
     */
    public final Polynomial multiply(final Polynomial theP2) {
    	final Polynomial p1 =  this;
    	final int degree = p1.myDegree +  theP2.myDegree;
    	final Polynomial result = new Polynomial(degree);
    	for (int i = 0; i < p1.myCoefs.length; i++){
    		for (int j = 0; j < theP2.myCoefs.length; j++) {
    			result.myCoefs[i + j] += p1.myCoefs[i] * theP2.myCoefs[j];
    		}	
    	}    	
    	for(int i = 0; i < result.myCoefs.length ; i++) { //mod the resulting coefs by the PRIME.
    		result.myCoefs[i] = posMod(result.myCoefs[i]);
    	}
    	return result;
    }
    
    /**
     * remainder >= 0.
     * @param theX theX mod PRIME.
     * @return the remainder.
     */
    private final int posMod(final int theX) {
    	int r = theX % PRIME;
    	if (r < 0) r += PRIME;
    	return r;
    }
    
    /**
     * String representation of a polynomial in
     * ascending order of degree.
     * example:
     * 		[1,2,3,4]
     * 		f(x) = 1 + 2x + 3x^2 + 4x^3
     */
    @Override 
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("f(x) = ");
    	for(int i = 0; i < myCoefs.length; i++) {
        	if(myCoefs[i] == 0) continue;
        	else {
        		if(i == 0) sb.append(myCoefs[i]);
        		else if(i == 1 && myCoefs[i] != 1) sb.append(" + " + myCoefs[i] + "x");
        		else if(i == 1 && myCoefs[i] == 1) sb.append(" + " + "x");
        		else if(i > 1 && myCoefs[i] != 1) sb.append(" + " + myCoefs[i] + "x^" + i);
        		else if(i > 1 && myCoefs[i] == 1) sb.append(" + " + "x^" + i); 		
        	}
        }
        return sb.toString();
    }    
//    
//    public static void main(String[] args) {
//    	final Polynomial p1 = Polynomial.randPoly(6, 2);
//    	System.out.println("The degree of p1 : " + p1.getDegree() + "\t" + p1);
//    	final Polynomial p2 = Polynomial.randPoly(3, 2);
//    	System.out.println("The degree of p2 : " + p2.getDegree() + "\t" + p2);
//    	final Polynomial p3 = p2.multiply(p1);
//    	System.out.println("The degree of p3 : " + p3.getDegree() + "\t" + p3);
//    	System.out.println("------------------------------------------------------------------");
//    	System.out.println("The shares in p3: ");
//    	for(int i = 1; i <= 5; i++) {
//    		System.out.println("share #" + i + ": " + p3.modCompute(i));
//    	}
//    	System.out.println("------------------------------------------------------------------");
////		book example change PRIME = 11
////    	final int[] coefs = new int [] {7,4,1};
////    	final Polynomial poly = new Polynomial(2, coefs);
////    	System.out.println("The degree of poly : " + poly.getDegree() + "\t" + poly);
////    	System.out.println("The shares are of poly: ");
////    	for(int i = 1; i <= 5; i++) {
////    		System.out.println("share #" + i + ": " + poly.modCompute(i));
////    	}
//    }
}