package computation;


public class Polynomial {
	private final int[] coefs;
	private final int degree;
	
	Polynomial(final int theDegree, final int[] theCoefs) {
		degree = theDegree;
		coefs = theCoefs;
	}
	
	/**
	 * Compute the polynomial using Horner's Rule.
	 * @param theVal value to compute at.
	 * @return the result of computation.
	 */
    public final int compute(final int theVal) {
        int result = 0;
        for (int coef : coefs) {
        	result = result * theVal + coef;
        }
        return result;
    }
    
    public static void main(String[] theArgs) {
    	// y = 2(6)^2 + 3(6) + 6 = 96
    	int a[] ={2,3,6};
    	Polynomial p = new Polynomial(2, a);
    	System.out.println(p.compute(7));
    	
    	
    }
}
	