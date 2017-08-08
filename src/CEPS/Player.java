package CEPS;

import computation.Polynomial;

public class Player {
	
	private Polynomial myPoly;
	int[] myShares;
	
	
	public Player(int secret, int players) {
		myPoly = Polynomial.randPoly(secret, players / 2);
		myShares = myPoly.computeShares(players);
	}
}
