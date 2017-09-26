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
		printM(C, n1, n3, "C");
		C = subtractMatrices(C, T, n1, n3);
		
    }
    
    /**
     * Takes in the size dimension of a matrix (n x m) and returns a matrix of the given size.
     * 
     * @param n number of rows
     * @param m number of columns
     * @return A random n x m matrix with all values in field Z_p
     */
	private long[][] randMatrix(int n, int m) {
		long[][] randMtrx = new long[n][m];
		long[] randMtrx1D = new long[n * m];
		randMtrx1D = RAND.longs(n * m, 0, PRIME).toArray();
		int counter = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				randMtrx[i][j] = randMtrx1D[counter++];
			}
		}
		return randMtrx;
	}
	
	/* ********** Computations on matrices *********** */
	
	// THIS IS NOT THE STRASSEN ALGORITHM YET.
	//TODO Actually make this a Strassen multiplication, that works with a very large PRIME.
	/**
	 * Strassen algorithm to multiplies two matrices x and y, such that the output is x * y.
	 * 
	 * @param x First input matrix of size n1 x n2
	 * @param y Second input matrix of size n2 x n3
	 * @param n1 Dimension of x
	 * @param n2 Dimension of x and y
	 * @param n3 Dimension of y
	 * @return Dot product of two matrices x and y
	 */
	private long[][] strassenMultiplication(long[][] x, long[][] y, int n1, int n2, int n3) {
		long[][] z = new long[n1][n3];
		for(int i = 0; i < n1; i++) {
			for(int j = 0; j < n3; j++) {
				z[i][j] = 0;
				for(int k = 0; k < n2; k++) {
					z[i][j] += x[i][k] * y[k][j];
		        }
				z[i][j] %= PRIME;
			}
		}
		return z;
	}
	
	/**
	 * Adds to matrices x and y, such that the output is x + y.
	 * 
	 * @param x First input matrix of size n x m
	 * @param y Second input matrix of size n x m
	 * @param n Number of rows in matrices x and y
	 * @param m Number of columns in matrices x and y
	 * @return An n x m matrix that is the result of the sum of x and y
	 */
	private long[][] addMatrices(long[][] x, long[][] y, int n, int m) {
		long[][] z = new long[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				z[i][j] = Math.abs(x[i][j] + y[i][j]) % PRIME;
			}
		}
		return z;
	}
	
	/**
	 * Subtracts two matrices x and y, such that the output is x - y. 
	 * 
	 * @param x First input matrix of size n x m
	 * @param y Second input matrix of size n x m
	 * @param n Number of rows in matrices x and y
	 * @param m Number of columns in matrices x and y
	 * @return An n x m matrix that is the result of the difference between x and y (x - y)
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
