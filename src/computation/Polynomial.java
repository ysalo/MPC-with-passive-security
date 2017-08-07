package computation;

public class Polynomial {
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
	 * Compute the polynomial using Horner's Rule.
	 * @param theVal value to compute at.
	 * @return the result of computation.
	 */
    public final int compute(final int theVal) {
        int result = 0;
        for (int c : myCoefs) {
        	result = result * theVal + c;
        }
        return result;
    }
    
    // I moved main to the protocol package
}
	