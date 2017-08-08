package CEPS;

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
		
		
		
	}
	
	public static void shareInputs(Player[] players) {
		int player = 0;
		for(Player p : players) {
			int index = 0;
			for(Player px : players) {
					p.addShare(index, px.myInput[player]);
				index++;
			}
			player++;
		}
		
	}
}
