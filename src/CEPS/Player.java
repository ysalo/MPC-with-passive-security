     package CEPS;

import computation.RandPoly;

public class Player {
	
	private RandPoly myPoly;
	private long[] myInput;
	private long[] myShares;
	private long myY;
	
	
	public Player(int secret, int players) {
		myPoly = new RandPoly(secret, players / 2);
		myInput = myPoly.computeShares(players);
		myShares = new long[players];
		myY = 0; //?????
	}
	
	public void addShare(int i, long share) {
		myShares[i] = share;
	}
	
	/* ***** GETTERS & SETTERS ***** */
	public RandPoly getPoly() {
		return myPoly; // Return a copy????
	}
	
	public long[] getInput() {
		return myInput; // Return a copy????
	}
	
	// ??? getShares(index i)
	// ???     return myShares[i]
	public long[] getShares() {
		return myShares; // Return a copy????
	}
	
	public long getY() {
		return myY;
	}
	
	public void setY(long y) {
		myY = y;
	}
}
