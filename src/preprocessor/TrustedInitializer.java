package preprocessor;

import java.security.SecureRandom;

public class TrustedInitializer {
	/** The field prime. */
    public static final long PRIME = 127;
	/** Random generator for the class. */
    private static final SecureRandom RAND = new SecureRandom();

    
    public void distribute(int n1, int n2, int n3) {
    	// ------ Create the tripoles of random matrices
    	// ------ 3 Matrices - A, B, C
    	// ------ 3 Players  - a, b, c
    	
    	// Random matrices to send to Alice
    	long[][] Aa = randMatrix(n1, n2);
		long[][] Ab = randMatrix(n1, n2);
		long[][] T = randMatrix(n1, n3);
		
		// Random Matrices to send to Bob
		long[][] Ba = randMatrix(n2, n3);
		long[][] Bb = randMatrix(n2, n3);
		
		long[][] AaBb = strassenMultiplication(Aa, Bb, n1, n2, n3);
		printM(AaBb, n1, n3, "AaBb");
		long[][] AbBa = strassenMultiplication(Ab, Ba, n1, n2, n3);
		printM(AbBa, n1, n3, "AbBa");
		long[][] C = addMatrices(AaBb, AbBa, n1, n3);
		printM(C, n1, n3, "C (pre)");
		C = subtractMatrices(C, T, n1, n3);
		
		printM(C, n1, n3, "C (post)");
		
    }
    
    /**
     * Takes in the size dimension of a matrix (n,m) and returns a matrix of the given size.
     * 
     * @param n number of rows
     * @param m number of columns
     * @return A random n x m matrix with all values in field Z_p
     */
	public long[][] randMatrix(int n, int m) {
		long[][] randMtrx = new long[n][m];
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				randMtrx[i][j] = ((long) RAND.nextInt((int) PRIME)); // Cast as int???? prolly not the best
			}
		}
		
		return randMtrx;
	}
	
	/**
	 * 
	 * @param x 
	 * @param y 
	 * @param n Number of rows
	 * @param m Number of columns
	 * @return An n x m matrix that is the result of the difference between x and y
	 */
	private long[][] subtractMatrices(long[][] x, long[][] y, int n, int m) {
		long[][] z = new long[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				z[i][j] = (x[i][j] - y[i][j]) % PRIME;
			}
		}
		return z;
	}
	
	private long[][] addMatrices(long[][] x, long[][] y, int n, int m) {
		long[][] z = new long[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				z[i][j] = Math.abs(x[i][j] + y[i][j]) % PRIME;
			}
		}
		return z;
	}
	
	// THIS IS NOT THE STRASSEN ALGORITHM YET.
	//TODO Actually make this a Strassen multiplication
	private long[][] strassenMultiplication(long[][] x, long[][] y, int n1, int n2, int n3) {
		long[][] z = new long[n1][n3];
		for(int i = 0; i < n1; i++) {
			for(int j = 0; j < n3; j++) {
				z[i][j] = 0;
				for(int k = 0; k < n2; k++) {
					z[i][j] += x[i][k] * y[k][j];
		        }
				z[i][j] %= PRIME; // ??????????????
			}
		}
		return z;
	}
	
	/* *************** Print Statement *************** */
	private static void printM(long[][] theMatrix, int n, int m, String title) {
		System.out.println(title + " Matrix");
		System.out.println("---------------------");
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				System.out.print(theMatrix[i][j] + ", ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
