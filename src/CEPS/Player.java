package CEPS;

import computation.Polynomial;

public class Player {
	
	private Polynomial myPoly;
	int[] myShares;
	
	
	public Player(int secret, int players) {
		myPoly = Polynomial.randPoly(secret, (players / 2) + 1);
		//myPoly = Polynomial.randPoly(7, 6);
		myShares = myPoly.computeShares(players);
		
	}
}
