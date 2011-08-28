package net.benrowland.kdtree;

import net.benrowland.tree.Point;
import net.benrowland.tree.PointInt;

import java.util.*;

/**
 * A Node of Point (of Integer), in a KD Tree.
 *
*/
public class KDTreeNodeInt extends KDTreeNode<Integer> {

    // *** Constructors

    public KDTreeNodeInt() {
        super();
    }

    public KDTreeNodeInt(KDTreeNodeInt parent) {
        super(parent);
    }

    public KDTreeNodeInt(Point<Integer> point) {
        super(point);
    }

    // *** Static factory methods

    public static KDTreeNodeInt buildRoot(List<PointInt> points) {
        return buildNode(null, points, 0);
    }

    /**
     * Builds the current Node (and all below it).  The split axis alternates between X and Y.  The current
     * node is defined as the median point (in remaining points) in the current axis.
     *
     * @param parent
     * @param points
     * @param depth
     * @return
     */
    public static KDTreeNodeInt buildNode(KDTreeNodeInt parent, List<PointInt> points, int depth) {

        // Make 2 copies of points - one sorted in X co-ordinates, the other sorted by Y co-ordinates
        // A bit memory-hungry but prevents having to re-sort points alternately in X and Y, every time we
        // go one level deeper in tree construction.
        List<PointInt> pointsSortedInX = new ArrayList<PointInt>(points);
        Collections.sort(pointsSortedInX, comparatorMap.get(Axis.X));

        List<PointInt> pointsSortedInY = new ArrayList<PointInt>(points);
        Collections.sort(pointsSortedInY, comparatorMap.get(Axis.Y));

        return buildNode(parent, pointsSortedInX, pointsSortedInY, depth);
    }

    public static KDTreeNodeInt buildNode(KDTreeNodeInt parent, List<PointInt> pointsSortedInX, List<PointInt> pointsSortedInY, int depth) {
        // Base case (no need to check pointsSortedInY as it contains same elements)
        if(pointsSortedInX.isEmpty()) return null;

        // Create this node
        final KDTreeNodeInt node = new KDTreeNodeInt(parent);
        node.axis = (depth % 2 == 1) ? KDTreeNode.Axis.Y : KDTreeNode.Axis.X;

        // Find median in current axis, and add it to the Node.
        // todo probably needs fixing to take the median *value* rather than median *index*.
        int medianIndex = pointsSortedInX.size() / 2;
        final PointInt medianPoint = node.axis == Axis.X ? pointsSortedInX.get(medianIndex) : pointsSortedInY.get(medianIndex);
        node.elem = medianPoint;

        TreePartitionStore<PointInt> store =
                TreePartitionStore.<PointInt>create(node.axis, medianIndex, pointsSortedInX, pointsSortedInY);

        // Build left and right children
        node.leftChild = buildNode(node, store.leftSidePointsSortedInX, store.leftSidePointsSortedInY, depth+1);
        node.rightChild = buildNode(node, store.rightSidePointsSortedInX, store.rightSidePointsSortedInY, depth+1);

        return node;
    }

    // *** Member functions

    @Override
    protected boolean shouldTryOtherBranchForBetterMatch(Point<Integer> point, KDTreeNode<Integer> currentBest) {
        return point.distance(currentBest.getElem()) > distanceInAxis(point);
    }

    public Integer distanceInAxis(Point<Integer> point) {
           switch(axis) {
               case X:
                   return Math.abs(elem.getX() - point.getX());
               case Y:
                   return Math.abs(elem.getY() - point.getY());
               default:
                   throw new IllegalStateException("Unknown axis [" + axis + "]");
           }
       }

    /**
     * Compare the Point in this Node to the other Point.
     *
     * @param other
     * @return -1 if other is &lt; this node, +1 if other is &gt;= this node
     */
    public int compare(Point<Integer> other) {
        if (axis == Axis.X) {
            if(other.getX() < elem.getX()) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else {
            if(other.getY() < elem.getY()) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

    public String toString() {
        return toString(0);
    }
}
