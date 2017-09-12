//package CEPS;
//
//import java.util.Arrays; // Delete this eventually
//
//import computation.Gates;
//
//public class CEPSmain {
//
//	private static final int PLAYERS = 5;
//	
//	public static void main(String[] args) {
//		Player[] players = new Player[PLAYERS];
//		
//		players[0] = new Player(121, PLAYERS);
//		players[1] = new Player(7, PLAYERS);
//		players[2] = new Player(18, PLAYERS);
//		players[3] = new Player(82, PLAYERS);
//		players[4] = new Player(73, PLAYERS);
//		
//		shareInputs(players);
//		
//		// Print Player's information: Polynomial & Shares
//		int count = 0;
//		for(Player p : players) {
//			System.out.println("Player: " + count);
//			System.out.println(p.getPoly().toString());
//			System.out.println(Arrays.toString(p.getShares()));
//			System.out.println("");
//			count++;
//		}
//		
//		System.out.println("\nAddition Gate on index 0 & 1:");
//		
//		Gates.additionGate(players, 0, 1);
//		
//		// Print value after addition gate
//		count = 0;
//		for(Player p : players) {
//			System.out.println("Player: " + count + " Sum: " + p.getY());
//			count++;
//		}
//		System.out.println("");
//		
//		System.out.println("\nMultiplication-by-constant by 2 on index 2:");
//		
//		Gates.multiplyByConst(players, 2, 2);
//		
//		// Print value after Multiplication gate
//		count = 0;
//		for(Player p : players) {
//			System.out.println("Player: " + count + " Product: " + p.getY());
//			count++;
//		}
//		System.out.println("");
//		
//		System.out.println("\nMultiplication on index 3 & 4:");
//		
//		Gates.multiply(players, 3, 4);
//		
//		// Print value after multiplication gate
//		count = 0;
//		for(Player p : players) {
//			System.out.println("Player: " + count + " Product: " + p.getY());
//			count++;
//		}
//	}
//	
//	
//	
//	/**
//	 * Distributes all shares to all players.
//	 * 
//	 * @param players Array of players 
//	 */
//	public static void shareInputs(Player[] players) {
//		int player = 0;
//		for(Player p : players) {
//			int index = 0;
//			for(Player px : players) {
//					p.addShare(index, px.getInput()[player]);
//				index++;
//			}
//			player++;
//		}
//		
//	}
//}
