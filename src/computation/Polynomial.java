package computation;

public class Polynomial {
	private final int[] myCoefs;
	private final int myDegree;
	
	Polynomial(final int theDegree, final int[] theCoefs) {
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
    
    public static void main(String[] theArgs) {
    	// y = 2(6)^2 + 3(6) + 6 = 96
    	int a[] ={2,3,6};
    	Polynomial p = new Polynomial(2, a);
    	System.out.println(p.compute(6));
    }
}
	