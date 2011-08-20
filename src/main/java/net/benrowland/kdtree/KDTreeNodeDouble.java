package net.benrowland.kdtree;

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

    public static KDTreeNodeDouble buildNode(KDTreeNodeDouble parent, List<PointDouble> points, int depth) {
        // Base case
        if(points.isEmpty()) return null;

        // Create this node
        KDTreeNodeDouble node = new KDTreeNodeDouble(parent);
        node.axis = (depth % 2 == 1) ? KDTreeNode.Axis.Y : KDTreeNode.Axis.X;

        // Sort points in axis and find median
        Collections.sort(points, comparatorMap.get(node.axis));
        int medianIndex = points.size() / 2;
        PointDouble medianInAxis = points.get(medianIndex);

        // Add median to this node
        node.elem = medianInAxis;

        // Build left and right children
        node.leftChild = buildNode(node, points.subList(0, medianIndex), depth+1);
        node.rightChild = buildNode(node, points.subList(medianIndex+1, points.size()), depth+1);

        return node;
    }

    // *** Member functions

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
