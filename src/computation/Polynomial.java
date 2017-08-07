package computation;


public class Polynomial {
	private final int[] coefs;
	private final int degree;
	
	Polynomial(final int theDegree, final int[] theCoefs) {
		degree = theDegree;
		coefs = theCoefs;
	}
	
    public final int compute(final int theVal) {
        int total = 0;
        for (int i = degree; i >= 0; i--)
            total = coefs[i] + (theVal * total);
        return total;
    }
    
    public static void main(String[] theArgs) {
    	int a[] ={6,3,2};
    	Polynomial p = new Polynomial(2, a);
    	System.out.println(p.compute(6));
    	
    	
    }
}
	