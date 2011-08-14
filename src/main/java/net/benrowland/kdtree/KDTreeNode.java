package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import java.lang.Override;

/**
 * A Node of Point, in a KD Tree.
 *
*/
public class KDTreeNode extends Node<Integer> {
    /**
     * The Axis in which this node partitions remaining space.
     */
    Axis axis;

    public enum Axis {
        X, Y
    }

    public KDTreeNode(Point<Integer> point) {
        super(point);
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

    /**
     * Find nearest neighbouring point in this KDTreeNode, given 'point'.
     *
     * @param point
     * @return
     */
    public Point<Integer> nearestNeighbour(Point<Integer> point) {
        if(leftChild == null && rightChild == null) {
            return this.getElem();
        }
        else {
            if(compare(point) == -1) {
                return ((KDTreeNode)leftChild).nearestNeighbour(point);
            }
            else {
                return ((KDTreeNode)rightChild).nearestNeighbour(point);
            }
        }
    }

    /**
     * Insert a new Node of Point into this KDTreeNode.
     *
     * @param newNode
     */
    @Override
    public void insert(Node<Integer> newNode) {
        KDTreeNode kdTreeNewNode = (KDTreeNode) newNode;
        kdTreeNewNode.axis = (axis == KDTreeNode.Axis.X) ? KDTreeNode.Axis.Y : KDTreeNode.Axis.X;
        super.insert(newNode);
    }
}
