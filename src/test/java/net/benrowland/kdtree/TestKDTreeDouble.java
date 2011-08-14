package net.benrowland.kdtree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for KDTreeInt.
 *
 */
public class TestKDTreeDouble {

    @Test
    public void testLevelByLevelConstruction() {
        KDTreeDouble tree = new KDTreeDouble();

        // *** ROOT NODE
        // Check addition of very first point
        Point<Double> point1 = new Point<Double>(4d, 1d);
        tree.insert(point1);
        assertEquals(point1, tree.getRootNode().getElem());

        // *** FIRST LEVEL
        // Check addition of next point with higher X than parent node is
        // inserted as right child of parent.
        Point<Double> point2 = new Point<Double>(6d, 5d);
        tree.insert(point2);
        assertEquals(point2, tree.getRootNode().getRightChild().getElem());

        // Check addition of next point with lower X than parent node is
        // inserted as left child of parent.
        Point<Double> point3 = new Point<Double>(3d, 5d);
        tree.insert(point3);
        assertEquals(point3, tree.getRootNode().getLeftChild().getElem());

        // *** SECOND LEVEL - LEFT
        // Check addition of next point with lower X than root node, and
        // lower Y than left child of root is inserted as left child of
        // left child of root node.
        Point<Double> point4 = new Point<Double>(2d, 4d);
        tree.insert(point4);
        assertEquals(point4, tree.getRootNode().getLeftChild().getLeftChild().getElem());

        // Check addition of next point with lower X than root node, and
        // higher Y than left child of root is inserted as right child of
        // left child of root node.
        Point<Double> point5 = new Point<Double>(1d, 6d);
        tree.insert(point5);
        assertEquals(point5, tree.getRootNode().getLeftChild().getRightChild().getElem());

        // *** SECOND LEVEL - RIGHT
        // Check addition of next point with higher X than root node, and
        // lower Y than right child of root is inserted as left child of
        // right child of root node.
        Point<Double> point6 = new Point<Double>(5d, 4d);
        tree.insert(point6);
        assertEquals(point6, tree.getRootNode().getRightChild().getLeftChild().getElem());

        // Check addition of next point with higher X than root node, and
        // higher Y than right child of root is inserted as right child of
        // right child of root node.
        Point<Double> point7 = new Point<Double>(6d, 6d);
        tree.insert(point7);
        assertEquals(point7, tree.getRootNode().getRightChild().getRightChild().getElem());
    }

    @Test
    public void testNearestNeighbour() {
        Point<Double> testPoint = new Point<Double>(1d, 1d);

        KDTreeDouble tree = new KDTreeDouble();

        // Root
        Point<Double> point1 = new Point<Double>(5d, 5d);
        tree.insert(point1);

        // Left of root (contains test point)
        Point<Double> point2 = new Point<Double>(3d, 2d);
        tree.insert(point2);

        // Right of root (does not contain test point)
        Point<Double> point3 = new Point<Double>(7d, 6d);
        tree.insert(point3);

        // Below left of root
        Point<Double> point4 = new Point<Double>(2d, 1d);
        tree.insert(point4);

        // Above left of root
        Point<Double> point5 = new Point<Double>(3d, 3d);
        tree.insert(point5);

        // Below right of root
        Point<Double> point6 = new Point<Double>(6d, 4d);
        tree.insert(point6);

        // Above right of root
        Point<Double> point7 = new Point<Double>(7d, 7d);
        tree.insert(point7);

        Point<Double> nearest = tree.nearestNeighbour(testPoint);
        assertEquals(point4, nearest);
    }
}
