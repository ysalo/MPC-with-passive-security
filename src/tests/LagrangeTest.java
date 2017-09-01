package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import computation.Lagrange;
import computation.RandPoly;

/**
 * 
 * Unit tests for class Lagrange.
 * 
 * @author Yaro Salo
 * @version 1
 */
public class LagrangeTest {
    /**
     * Test interpolation method with two different degrees and secrets.
     */
    @Test
    public void testInterpolate() {
        final RandPoly poly1 = new RandPoly(43, 2);
        final int[] y1 = poly1.computeShares(3);
        final int[] x1 = new int[] {1, 2, 3};
        final int secret = poly1.modCompute(0);
        final int rsecret = Lagrange.interpolate(x1, y1);
        assertEquals("Interpolation returned an incorrect result with degree 2!", secret, rsecret);

        final RandPoly poly2 = new RandPoly(78, 11);
        final int[] y2 = poly2.computeShares(12);
        final int[] x2 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        final int secret2 = poly2.modCompute(0);
        final int rsecret2 = Lagrange.interpolate(x2, y2);
        assertEquals("Interpolation returned an incorrect result with degree 11!", secret2, rsecret2);
    }

    /**
     * Test that the x's and y's are the same length. 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgument() {
        final int[] x = new int[] {1, 2, 3};
        final int[] y = new int[] {1, 2};
        Lagrange.interpolate(x, y);
    }
}
