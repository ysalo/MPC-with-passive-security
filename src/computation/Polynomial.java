package computation;

import java.security.SecureRandom;
import java.util.Random;

public class Polynomial {
	private static final int PRIME = 127; //TODO generate a prime randomly
	private final int[] myCoefs;
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
	
	/**
	 * Compute the polynomial using Horner's Rule
	 * and applying mod operator for field arithmetic.
	 * @param theVal value to compute at.
	 * @return the result of computation.
	 */
    public final int modCompute(final int theVal) {
        //Evaluating at 0 will reveal the secret.
    	if(theVal <= 0)
        	throw new IllegalArgumentException("Cannot evaluate at less than 1!");
    	int result = 0;
        
    	//Since this polynomial is in ascending order we want to evaluate it 
    	//in the reverse order.
        for (int i = myCoefs.length -1 ; i >= 0; i--)
            result = result*theVal + myCoefs[i];
        	result = result % PRIME;
  
        return result;
    }
    /**
     * Generate a random polynomial to represent the secret (constant)
     * term. 
     * TODO add prime generation.
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
    	Polynomial p = new Polynomial(theDegree, coefs);
    	return p;
    }
    
    /**
     * Compute the shares of the polynomial.
     * @param theNumP the number of players.
     * @return array of shares.
     */
    public final int[] computeShares(final int theNumP) {
    	final int[] shares = new int[theNumP];
    	//polynomial(0) = the secret
    	for(int i = 1; i <= theNumP; i++) {
    		shares[i - 1] = modCompute(i);
    	}
    	return shares;
    }
    //TODO maybe will have to delete this method.
    /**
     * String representation of a polynomial with 
     * the constant term being the first in the list.
     * example:
     * 		[1,2,3,4]
     * 		f(x) = 1 + 2x + 3x^2 + 4x^3
     */
    @Override 
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("f(x) = ");
    	for(int i = 0; i <= myDegree; i++) {
        	if(myCoefs[i] == 0) continue;
        	else { //perhaps simplify
        		if(i == 0) sb.append(myCoefs[i]);
        		else if(i == 1 && myCoefs[i] != 1) sb.append(" + " + myCoefs[i] + "x");
        		else if(i == 1 && myCoefs[i] == 1) sb.append(" + " + "x");
        		else if(i > 1 && myCoefs[i] != 1) sb.append(" + " + myCoefs[i] + "x^" + i);
        		else if(i > 1 && myCoefs[i] == 1) sb.append(" + " + "x^" + i);
        	}
        }
        return sb.toString();
    }
    
    public final int[] getCoefs() {
    	return myCoefs;
    }
}