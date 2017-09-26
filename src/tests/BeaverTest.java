package tests;

import java.security.SecureRandom;

public class BeaverTest {
	// X - matrix size N1 by N2
	// Y - matrix size N2 by N3
	private static final int N1 = 4;
	private static final int N2 = 4;
	private static final int N3 = 4;
	
	// Matrix X & Y - GOAL: X * Y
	private static final long[][] X = {{1, 2, 3, 4},
									   {5, 6, 7, 8},
									   {9, 8, 7, 6},
									   {5, 4, 3, 2}};
	
	private static final long[][] Y = {{1, 4, 7, 9},
									   {7, 5, 2, 8},
									   {6, 0, 3, 1},
									   {3, 4, 7, 1}};
	
	/** MAIN! Runs the test of multiplication of matrices via Beaver's protocol. */
	public static void main(String[] args) {
		// Print the desired result - GOAL: X * Y
		long[][] goal = multiply(X, Y, N1, N2, N3);
		printM(goal, N1, N3, "Goal (X * Y)");
		
		startPlayers();
		
		// Create the tripoles of random matrices
		// 3 Matrices - A, B, C
		// 3 Players  - a, b, c
		preprocessor.TrustedInitializer TI = new preprocessor.TrustedInitializer();
		TI.distribute(N1, N2, N3);
		
		
	}
	
	private static computation.Player[] startPlayers(){
		computation.Player alice = new computation.Alice();
		computation.Player bob = new computation.Bob();
		
		// Split X
		long[][] Xa = randMatrix(N1, N2);
		long[][] Xb = subtractMatrices(X, Xa, N1, N2);
		
		// Split Y
		long[][] Ya = randMatrix(N2, N3);
		long[][] Yb = subtractMatrices(Y, Ya, N2, N3);
		
		// Check
		//printM(addMatrices(Xa, Xb, N1, N2), N1, N2, "Check of X");
		
		computation.Player[] players = {alice, bob};
		return players;
	}

	/**
     * Takes in the size dimension of a matrix (n x m) and returns a matrix of the given size.
     * This is used to split the matrix X and Y into parts. The actual protocol will have the 
     * 'users' split their own data into parts and give them to the players.
     *  
     * @param n number of rows
     * @param m number of columns
     * @return A random n x m matrix with all values in field Z_p
     */
	private static long[][] randMatrix(int n, int m) {
		long[][] randMtrx = new long[n][m];
		long[] randMtrx1D = new long[n * m];
		randMtrx1D = new SecureRandom().longs(n * m, 0, 
												preprocessor.TrustedInitializer.PRIME).toArray();
		int counter = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				randMtrx[i][j] = randMtrx1D[counter++];
			}
		}
		return randMtrx;
	}
	
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
