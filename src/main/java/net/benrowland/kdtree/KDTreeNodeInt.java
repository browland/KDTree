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

        if(points.isEmpty()) return null;

        // Create this node
        final KDTreeNodeInt node = new KDTreeNodeInt(parent);
        node.axis = (depth % 2 == 1) ? KDTreeNode.Axis.Y : KDTreeNode.Axis.X;

        // Sort points by split axis
        Collections.sort(points, comparatorMap.get(node.axis));

        // Find median in current axis, and add it to the Node.
        // todo probably needs fixing to take the median *value* rather than median *index*.
        int medianIndex = points.size() / 2;
        final PointInt medianPoint = points.get(medianIndex);
        node.elem = medianPoint;

        // Build left and right children
        node.leftChild = buildNode(node, points.subList(0, medianIndex), depth+1);
        node.rightChild = buildNode(node, points.subList(medianIndex+1, points.size()), depth+1);

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
