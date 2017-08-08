package CEPS;

import computation.Polynomial;

public class Player {
	
	private Polynomial myPoly;
	int[] myInput;
	int[] myShares;
	
	
	public Player(int secret, int players) {
		myPoly = Polynomial.randPoly(secret, players / 2);
		myInput = myPoly.computeShares(players);
		myShares = new int[players];
	}
	
	public void addShare(int i, int share) {
		myShares[i] = share;
	}
}
