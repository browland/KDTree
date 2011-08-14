package net.benrowland.kdtree;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Tests for KDTreeInt.
 *
 */
public class TestKDTreeInt {

    @Test
    public void testLevelByLevelConstruction() {
        KDTreeInt tree = new KDTreeInt();

        // *** ROOT NODE
        // Check addition of very first point
        Point<Integer> point1 = new Point<Integer>(4, 1);
        tree.insert(point1);
        assertEquals(point1, tree.getRootNode().getElem());

        // *** FIRST LEVEL
        // Check addition of next point with higher X than parent node is
        // inserted as right child of parent.
        Point<Integer> point2 = new Point<Integer>(6, 5);
        tree.insert(point2);
        assertEquals(point2, tree.getRootNode().getRightChild().getElem());

        // Check addition of next point with lower X than parent node is
        // inserted as left child of parent.
        Point<Integer> point3 = new Point<Integer>(3, 5);
        tree.insert(point3);
        assertEquals(point3, tree.getRootNode().getLeftChild().getElem());

        // *** SECOND LEVEL - LEFT
        // Check addition of next point with lower X than root node, and
        // lower Y than left child of root is inserted as left child of
        // left child of root node.
        Point<Integer> point4 = new Point<Integer>(2, 4);
        tree.insert(point4);
        assertEquals(point4, tree.getRootNode().getLeftChild().getLeftChild().getElem());

        // Check addition of next point with lower X than root node, and
        // higher Y than left child of root is inserted as right child of
        // left child of root node.
        Point<Integer> point5 = new Point<Integer>(1, 6);
        tree.insert(point5);
        assertEquals(point5, tree.getRootNode().getLeftChild().getRightChild().getElem());

        // *** SECOND LEVEL - RIGHT
        // Check addition of next point with higher X than root node, and
        // lower Y than right child of root is inserted as left child of
        // right child of root node.
        Point<Integer> point6 = new Point<Integer>(5, 4);
        tree.insert(point6);
        assertEquals(point6, tree.getRootNode().getRightChild().getLeftChild().getElem());

        // Check addition of next point with higher X than root node, and
        // higher Y than right child of root is inserted as right child of
        // right child of root node.
        Point<Integer> point7 = new Point<Integer>(6, 6);
        tree.insert(point7);
        assertEquals(point7, tree.getRootNode().getRightChild().getRightChild().getElem());
    }

    @Test
    public void testNearestNeighbour() {
        Point<Integer> testPoint = new Point<Integer>(1, 1);

        KDTreeInt tree = new KDTreeInt();

        // Root
        Point<Integer> point1 = new Point<Integer>(5, 5);
        tree.insert(point1);

        // Left of root (contains test point)
        Point<Integer> point2 = new Point<Integer>(3, 2);
        tree.insert(point2);

        // Right of root (does not contain test point)
        Point<Integer> point3 = new Point<Integer>(7, 6);
        tree.insert(point3);

        // Below left of root
        Point<Integer> point4 = new Point<Integer>(2, 1);
        tree.insert(point4);

        // Above left of root
        Point<Integer> point5 = new Point<Integer>(3, 3);
        tree.insert(point5);

        // Below right of root
        Point<Integer> point6 = new Point<Integer>(6, 4);
        tree.insert(point6);

        // Above right of root
        Point<Integer> point7 = new Point<Integer>(7, 7);
        tree.insert(point7);

        Point<Integer> nearest = tree.nearestNeighbour(testPoint);
        assertEquals(point4, nearest);
    }
}
