//package computation;
//
//import CEPS.Player;
//
//public class Gates {
//	private static final int PRIME = 127;
//	private RandPoly myPoly;
//	
//	public Gates(RandPoly thePoly) {
//		myPoly = thePoly;
//	}
//	
//	/**
//	 * Addition gate takes two shares and returns the sum.
//	 * 
//	 * @param a First share
//	 * @param b Second share
//	 * @return Sum of two shares
//	 */
//	public static void additionGate(Player[] players, int a, int b) {
//		for(Player p : players) {
//			p.setY((RandPoly.posMod(p.getShares()[a] + p.getShares()[b]))); 
//		}
//		
//	}
//	
//	/**
//	 * Multiply-By-Constant gate takes a share and a constant, returning the sum.
//	 * 
//	 * @param a Share
//	 * @param constant Multiplying constant
//	 * @return Product of the share and constant
//	 */
//	public static void multiplyByConst(Player[] players, int a, int constant) {
//		for(Player p : players) {
//			p.setY(RandPoly.posMod((p.getShares()[a] * constant))); 
//		}
//	}
//	
//	public static void multiply(Player[] players, int a, int b) {
//		for(Player p : players) {
//			p.setY(RandPoly.posMod(p.getShares()[a] * p.getShares()[b])); 
//		}
//	}
//}
