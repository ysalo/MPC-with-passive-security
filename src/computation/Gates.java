package computation;

import computation.Polynomial;

public class Gates {
	
	private Polynomial myPoly;
	
	public Gates(Polynomial thePoly) {
		myPoly = thePoly;
	}
	
	/**
	 * Addition gate takes two shares and returns the sum.
	 * 
	 * @param a First share
	 * @param b Second share
	 * @return Sum of two shares
	 */
	public int additionGate(int a, int b) {
		return a + b;
	}
	
	/**
	 * Multiply-By-Constant gate takes a share and a constant, returning the sum.
	 * 
	 * @param a Share
	 * @param b Multiplying constant
	 * @return Product of the share and constant
	 */
	public int multiplyByConst(int a, int b) {
		return a * b;
	}
	
	public int multiply(int a, int b) {
		int y = 0;
		
		return y;
	}
}
