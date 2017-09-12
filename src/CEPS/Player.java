     package CEPS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import computation.RandPoly;

public class Player {
	
	private final  RandPoly myPoly;
	private final long[] myShares;
	private final List<Long> myReceivedShares;
	private final long mySecret;
	private long myY;
	
	
	public Player(int players) {
	    mySecret = requestSecret();
		myPoly = new RandPoly(mySecret, players / 2);
		System.out.println(myPoly);
		myShares = myPoly.computeShares(players);
		myReceivedShares = new ArrayList<>();
		myY = 0;

	}
    
	private long requestSecret() {
        final Scanner scan = new Scanner(System.in);
        System.out.print("Enter your secret: ");
        final long secret = scan.nextLong();
        return secret; 
    }
	
	public void addShare(long share) {
	    myReceivedShares.add(share);
	}
	
	/* ***** GETTERS & SETTERS ***** */
	public RandPoly getPoly() {
		return myPoly; // Return a copy????
	}
	
	public long[] getMyShares() {
		return myShares; // Return a copy????
	}
	
	// ??? getShares(index i)
	// ???     return myShares[i]
	public List<Long> getMyRecShares() {
		return myReceivedShares; // Return a copy????
	}
	
	public long getY() {
		return myY;
	}
	
	public void setY(long y) {
		myY = y;
	}
}
