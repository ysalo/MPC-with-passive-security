package CEPS;

import computation.Gates;
import computation.Polynomial;

public class CEPSmain {

	private static final int PLAYERS = 5;
	
	public static void main(String[] args) {
		Player[] players = new Player[PLAYERS];
		
		players[0] = new Player(121, PLAYERS);
		players[1] = new Player(7, PLAYERS);
		players[2] = new Player(18, PLAYERS);
		players[3] = new Player(82, PLAYERS);
		players[4] = new Player(73, PLAYERS);
		
		shareInputs(players);
		
		for(Player p : players) {
			int a = p.getShares()[0];
			int b = p.getShares()[1];
			System.out.println(a + ", " + b);
		}
		
		Gates.additionGate(players, 0, 1);
		
		for(Player p : players) {
			System.out.println(p.getY());
		}
		
	}
	
	
	
	/**
	 * Distributes all shares to all players.
	 * 
	 * @param players Array of players 
	 */
	public static void shareInputs(Player[] players) {
		int player = 0;
		for(Player p : players) {
			int index = 0;
			for(Player px : players) {
					p.addShare(index, px.getInput()[player]);
				index++;
			}
			player++;
		}
		
	}
}
