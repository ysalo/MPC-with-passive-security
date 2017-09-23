package tests;

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
	
	
	public static void main(String[] args) {
		// Create the tripoles of random matrices
		// 3 Matrices - A, B, C
		// 3 Players  - a, b, c
		preprocessor.TrustedInitializer TI = new preprocessor.TrustedInitializer();
		TI.distribute(N1, N2, N3);
		
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
