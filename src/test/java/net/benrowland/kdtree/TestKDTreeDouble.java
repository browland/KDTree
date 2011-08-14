package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals(new PointDouble(5d, 6d), root.getElem());

        Node lChild = root.getLeftChild();
        Node rChild = root.getRightChild();
        assertEquals(new PointDouble(1d, 2d), lChild.getElem());
        assertEquals(new PointDouble(7d, 5d), rChild.getElem());

        Node lChildLChild = lChild.getLeftChild();
        Node lChildRChild = lChild.getRightChild();
        assertEquals(new PointDouble(4d, 2d), lChildLChild.getElem());
        assertEquals(new PointDouble(3d, 4d), lChildRChild.getElem());

        Node rChildLChild = rChild.getLeftChild();
        Node rChildRChild = rChild.getRightChild();
        assertEquals(new PointDouble(5d, 3d), rChildLChild.getElem());
        assertEquals(new PointDouble(7d, 8d), rChildRChild.getElem());
    }

    @Test
    public void testNearestNeighbour() {
        List<PointDouble> points = getPoints();
        KDTreeDouble tree = KDTreeDouble.kdtree(points);
        System.out.println(tree);

        PointDouble testPoint = new PointDouble(3d, 3d);

        Point<Double> nearest = tree.nearestNeighbour(testPoint);
        assertEquals(new PointDouble(3d, 4d), nearest);
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
                        new PointDouble(5d, 6d),
                        new PointDouble(7d, 8d)
                }
        );
        return points;
    }
}
