package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import net.benrowland.tree.Point;
import net.benrowland.tree.PointInt;
import org.junit.Test;

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

        Node lChildLChild = lChild.getLeftChild();
        Node lChildRChild = lChild.getRightChild();
        assertEquals(new PointInt(4, 2), lChildLChild.getElem());
        assertEquals(new PointInt(3, 4), lChildRChild.getElem());

        Node rChildLChild = rChild.getLeftChild();
        Node rChildRChild = rChild.getRightChild();
        assertEquals(new PointInt(5, 3), rChildLChild.getElem());
        assertEquals(new PointInt(7, 8), rChildRChild.getElem());
    }

    @Test
    public void testNearestNeighbour() {
        List<PointInt> points = getPoints();
        KDTreeInt tree = KDTreeInt.kdtree(points);
        System.out.println(tree);

        PointInt testPoint = new PointInt(3, 3);

        Point<Integer> nearest = tree.nearestNeighbour(testPoint);
        assertEquals(new PointInt(3, 4), nearest);
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
