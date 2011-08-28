package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import net.benrowland.tree.Point;
import net.benrowland.tree.PointDouble;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for KDTreeDouble.
 *
 */
public class TestKDTreeDouble {

    @Test
    public void testBuildTree() {
        List<PointDouble> points = getPoints();
        KDTreeDouble tree = KDTreeDouble.kdtree(points);
        System.out.println(tree);

        Node root = tree.getRootNode();
        assertEquals(new PointDouble(5d, 5d), root.getElem());

        Node lChild = root.getLeftChild();
        Node rChild = root.getRightChild();
        assertEquals(new PointDouble(4d, 2d), lChild.getElem());
        assertEquals(new PointDouble(7d, 5d), rChild.getElem());
        assertEquals(root, lChild.getParent());
        assertEquals(root, rChild.getParent());

        Node lChildLChild = lChild.getLeftChild();
        Node lChildRChild = lChild.getRightChild();
        assertEquals(new PointDouble(1d, 1d), lChildLChild.getElem());
        assertEquals(new PointDouble(3d, 4d), lChildRChild.getElem());
        assertEquals(lChild, lChildLChild.getParent());
        assertEquals(lChild, lChildRChild.getParent());

        Node rChildLChild = rChild.getLeftChild();
        Node rChildRChild = rChild.getRightChild();
        assertEquals(new PointDouble(5d, 3d), rChildLChild.getElem());
        assertEquals(new PointDouble(7d, 8d), rChildRChild.getElem());
        assertEquals(rChild, rChildLChild.getParent());
        assertEquals(rChild, rChildRChild.getParent());
    }

    /**
     * Test situation where closest point is actually a leaf node.
     */
    @Test
    public void testNearestNeighbourIsLeaf() {
        List<PointDouble> points = getPoints();
        KDTreeDouble tree = KDTreeDouble.kdtree(points);
        System.out.println(tree);

        PointDouble testPoint = new PointDouble(3d, 3d);

        Point<Double> nearest = tree.nearestNeighbour(testPoint).getElem();
        assertEquals(new PointDouble(3d, 4d), nearest);
    }

    /**
     * Test situation where closest point is actually the root node.
     */
    @Test
    public void testNearestNeighbourIsRoot() {
        List<PointDouble> points = new ArrayList<PointDouble>();
        points.add(new PointDouble(43d, 71d));
        points.add(new PointDouble(10d, 91d));
        points.add(new PointDouble(1078d, 8876d));
        KDTreeDouble tree = KDTreeDouble.kdtree(points);

        PointDouble testPoint = new PointDouble(45d, 78d);
        Point<Double> nearest = tree.nearestNeighbour(testPoint).getElem();
        assertEquals(new PointDouble(43d, 71d), nearest);

    }

    @Test
    public void testNearestNeighbourIsOnOtherBranch() {
        List<PointDouble> points = new ArrayList<PointDouble>(getPoints());

        // Add point on other side of median in x (first split axis),
        // compared to search point.
        points.add(new PointDouble(6d, 8d));

        PointDouble testPoint = new PointDouble(4d, 8d);

        KDTreeDouble tree = KDTreeDouble.kdtree(points);
        Point<Double> nearest = tree.nearestNeighbour(testPoint).getElem();
        assertEquals(new PointDouble(6d, 8d), nearest);
    }

    private List<PointDouble> getPoints() {
        List<PointDouble> points = Arrays.asList(
                new PointDouble[]{
                        new PointDouble(5d, 3d),
                        new PointDouble(4d, 2d),
                        new PointDouble(7d, 5d),
                        new PointDouble(1d, 1d),
                        new PointDouble(1d, 2d),
                        new PointDouble(3d, 4d),
                        new PointDouble(5d, 5d),
                        new PointDouble(7d, 8d)
                }
        );
        return points;
    }
}
