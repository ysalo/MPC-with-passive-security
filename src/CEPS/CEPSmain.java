package CEPS;

import computation.Polynomial;

public class CEPSmain {

	public static void main(String[] args) {
		// y = 2(6)^2 + 3(6) + 6 = 96
		int c1[] = {2, 3, 6};
		Polynomial p1 = new Polynomial(2, c1);
		System.out.println(p1.compute(6));
	}
	
	

}
