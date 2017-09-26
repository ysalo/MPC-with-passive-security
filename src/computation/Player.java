package computation;

import java.security.SecureRandom;

public class Player {
	/* ********** Computations on matrices *********** */
	/**
	 * Multiplies two matrices x and y, such that the output is x * y.
	 * 
	 * @param x First input matrix of size n1 x n2
	 * @param y Second input matrix of size n2 x n3
	 * @param n1 Dimension of x
	 * @param n2 Dimension of x and y
	 * @param n3 Dimension of y
	 * @return Dot product of two matrices x and y
	 */
	private static long[][] multiply(long[][] x, long[][] y, int n1, int n2, int n3) {
		long[][] z = new long[n1][n3];
		for(int i = 0; i < n1; i++) {
			for(int j = 0; j < n3; j++) {
				z[i][j] = 0;
				for(int k = 0; k < n2; k++) {
					z[i][j] += x[i][k] * y[k][j];
		        }
				z[i][j] %= preprocessor.TrustedInitializer.PRIME;
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
	private static long[][] addMatrices(long[][] x, long[][] y, int n, int m) {
		long[][] z = new long[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				z[i][j] = Math.abs(x[i][j] + y[i][j]) % preprocessor.TrustedInitializer.PRIME;
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
	private static long[][] subtractMatrices(long[][] x, long[][] y, int n, int m) {
		long[][] z = new long[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				z[i][j] = (x[i][j] - y[i][j]) % preprocessor.TrustedInitializer.PRIME;
			}
		}
		return z;
	}
}
