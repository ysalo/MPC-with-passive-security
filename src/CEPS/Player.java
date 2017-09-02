     package CEPS;

import computation.RandPoly;

public class Player {
	
	private RandPoly myPoly;
	private int[] myInput;
	private int[] myShares;
	private int myY;
	
	
	public Player(int secret, int players) {
		myPoly = new RandPoly(secret, players / 2);
		myInput = myPoly.computeShares(players);
		myShares = new int[players];
		myY = 0; //?????
	}
	
	public void addShare(int i, int share) {
		myShares[i] = share;
	}
	
	/* ***** GETTERS & SETTERS ***** */
	public RandPoly getPoly() {
		return myPoly; // Return a copy????
	}
	
	public int[] getInput() {
		return myInput; // Return a copy????
	}
	
	// ??? getShares(index i)
	// ???     return myShares[i]
	public int[] getShares() {
		return myShares; // Return a copy????
	}
	
	public int getY() {
		return myY;
	}
	
	public void setY(int y) {
		myY = y;
	}
}
