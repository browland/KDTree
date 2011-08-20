package net.benrowland.tree;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Point.
 */
public class TestPoint {

    /**
     * Test Point.distance()
     */
    @Test
    public void testDistance() {
        // Use 3, 4, 5 triangle for simplicity
        PointInt one = new PointInt(1, 1);
        PointInt two = new PointInt(4, 5);
        assertEquals(5, one.distance(two), 0.00001);
    }

    /**
     * Test Point.closer()
     */
    @Test
    public void testCloser() {
        PointInt one = new PointInt(4, 4);
        PointInt two = new PointInt(5, 5);
        PointInt test = new PointInt(3, 3);
        assertTrue(one.closer(test, two));
    }
}
