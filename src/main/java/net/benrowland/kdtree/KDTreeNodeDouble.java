package net.benrowland.kdtree;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.benrowland.tree.Point;
import net.benrowland.tree.PointDouble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Node of Point (of Double), in a KD Tree.
 *
*/
public class KDTreeNodeDouble extends KDTreeNode<Double> {

    // *** Constructors

    public KDTreeNodeDouble() {}

    public KDTreeNodeDouble(KDTreeNodeDouble parent) {
        super(parent);
    }

    public KDTreeNodeDouble(Point<Double> elem) {
        super(elem);
    }

    // *** Static factory methods

    public static KDTreeNodeDouble buildRoot(List<PointDouble> points) {
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
    public static KDTreeNodeDouble buildNode(KDTreeNodeDouble parent, List<PointDouble> points, int depth) {

        // Make 2 copies of points - one sorted in X co-ordinates, the other sorted by Y co-ordinates
        // A bit memory-hungry but prevents having to re-sort points alternately in X and Y, every time we
        // go one level deeper in tree construction.
        List<PointDouble> pointsSortedInX = new ArrayList<PointDouble>(points);
        Collections.sort(pointsSortedInX, comparatorMap.get(Axis.X));

        List<PointDouble> pointsSortedInY = new ArrayList<PointDouble>(points);
        Collections.sort(pointsSortedInY, comparatorMap.get(Axis.Y));

        return buildNode(parent, pointsSortedInX, pointsSortedInY, depth);
    }

    public static KDTreeNodeDouble buildNode(KDTreeNodeDouble parent, List<PointDouble> pointsSortedInX, List<PointDouble> pointsSortedInY, int depth) {
        // Base case (no need to check pointsSortedInY as it contains same elements)
        if(pointsSortedInX.isEmpty()) return null;

        // Create this node
        final KDTreeNodeDouble node = new KDTreeNodeDouble(parent);
        node.axis = (depth % 2 == 1) ? KDTreeNode.Axis.Y : KDTreeNode.Axis.X;

        // Find median in current axis, and add it to the Node.
        // todo probably needs fixing to take the median *value* rather than median *index*.
        int medianIndex = pointsSortedInX.size() / 2;
        final PointDouble medianPoint = node.axis == Axis.X ? pointsSortedInX.get(medianIndex) : pointsSortedInY.get(medianIndex);
        node.elem = medianPoint;

        TreePartitionStore<PointDouble> store =
                TreePartitionStore.<PointDouble>create(node.axis, medianIndex, pointsSortedInX, pointsSortedInY);

        // Build left and right children
        node.leftChild = buildNode(node, store.leftSidePointsSortedInX, store.leftSidePointsSortedInY, depth+1);
        node.rightChild = buildNode(node, store.rightSidePointsSortedInX, store.rightSidePointsSortedInY, depth+1);

        return node;
    }

    // *** Member functions

    @Override
    protected boolean shouldTryOtherBranchForBetterMatch(Point<Double> point, KDTreeNode<Double> currentBest) {
        return point.distance(currentBest.getElem()) > distanceInAxis(point);
    }

    public Double distanceInAxis(Point<Double> point) {
        switch(axis) {
            case X:
                return Math.abs(elem.getX() - point.getX());
            case Y:
                return Math.abs(elem.getY() - point.getY());
            default:
                throw new IllegalStateException("Unknown axis [" + axis + "]");
        }
    }

    @Override
    public int compare(Point<Double> other) {
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
