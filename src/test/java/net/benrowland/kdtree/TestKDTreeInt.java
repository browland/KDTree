package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import net.benrowland.tree.Point;
import net.benrowland.tree.PointInt;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for KDTreeInt.
 *
 */
public class TestKDTreeInt {

    @Test
    public void testBuildTree() {
        List<PointInt> points = getPoints();
        KDTreeInt tree = KDTreeInt.kdtree(points);
        System.out.println(tree);

        Node root = tree.getRootNode();
        assertEquals(new PointInt(5, 6), root.getElem());

        Node lChild = root.getLeftChild();
        Node rChild = root.getRightChild();
        assertEquals(new PointInt(1, 2), lChild.getElem());
        assertEquals(new PointInt(7, 5), rChild.getElem());
        assertEquals(root, lChild.getParent());
        assertEquals(root, rChild.getParent());

        Node lChildLChild = lChild.getLeftChild();
        Node lChildRChild = lChild.getRightChild();
        assertEquals(new PointInt(4, 2), lChildLChild.getElem());
        assertEquals(new PointInt(3, 4), lChildRChild.getElem());
        assertEquals(lChild, lChildLChild.getParent());
        assertEquals(lChild, lChildRChild.getParent());

        Node rChildLChild = rChild.getLeftChild();
        Node rChildRChild = rChild.getRightChild();
        assertEquals(new PointInt(5, 3), rChildLChild.getElem());
        assertEquals(new PointInt(7, 8), rChildRChild.getElem());
        assertEquals(rChild, rChildLChild.getParent());
        assertEquals(rChild, rChildRChild.getParent());
    }

    /**
     * Test situation where closest point is actually a leaf node.
     */
    @Test
    public void testNearestNeighbourIsLeaf() {
        List<PointInt> points = getPoints();
        KDTreeInt tree = KDTreeInt.kdtree(points);
        System.out.println(tree);

        PointInt testPoint = new PointInt(3, 3);

        Point<Integer> nearest = tree.nearestNeighbour(testPoint).getElem();
        assertEquals(new PointInt(3, 4), nearest);
    }

    /**
     * Test situation where closest point is actually the root node.
     */
    @Test
    public void testNearestNeighbourIsRoot() {
        List<PointInt> points = new ArrayList<PointInt>();
        points.add(new PointInt(43, 71));
        points.add(new PointInt(10, 91));
        points.add(new PointInt(1078, 8876));
        KDTreeInt tree = KDTreeInt.kdtree(points);

        PointInt testPoint = new PointInt(45, 78);
        Point<Integer> nearest = tree.nearestNeighbour(testPoint).getElem();
        assertEquals(new PointInt(43, 71), nearest);

    }

    @Test
    public void testNearestNeighbourIsOnOtherBranch() {
        List<PointInt> points = new ArrayList<PointInt>(getPoints());

        // Add point on other side of median in x (first split axis),
        // compared to search point.
        points.add(new PointInt(6, 8));

        PointInt testPoint = new PointInt(4, 8);

        KDTreeInt tree = KDTreeInt.kdtree(points);
        Point<Integer> nearest = tree.nearestNeighbour(testPoint).getElem();
        assertEquals(new PointInt(6, 8), nearest);
    }

    private List<PointInt> getPoints() {
        List<PointInt> points = Arrays.asList(
                new PointInt[]{
                        new PointInt(5, 3),
                        new PointInt(4, 2),
                        new PointInt(7, 5),
                        new PointInt(1, 1),
                        new PointInt(1, 2),
                        new PointInt(3, 4),
                        new PointInt(5, 6),
                        new PointInt(7, 8)
                }
        );
        return points;
    }
}
