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

    public static KDTreeNodeInt buildNode(KDTreeNodeInt parent, List<PointInt> points, int depth) {
        // Base case
        if(points.isEmpty()) return null;

        // Create this node
        KDTreeNodeInt node = new KDTreeNodeInt(parent);
        node.axis = (depth % 2 == 1) ? KDTreeNode.Axis.Y : KDTreeNode.Axis.X;

        // Sort points in axis and find median
        Collections.sort(points, comparatorMap.get(node.axis));
        int medianIndex = points.size() / 2;
        PointInt medianInAxis = points.get(medianIndex);

        // Add median to this node
        node.elem = medianInAxis;

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
