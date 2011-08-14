package net.benrowland.kdtree;

import net.benrowland.tree.Node;

/**
 * A Node Of Point (of T)  in a KD Tree.
 */
public abstract class KDTreeNode<T> extends Node<T> {

    public KDTreeNode(Point<T> elem) {
        super(elem);
    }

    /**
     * The Axis in which this node partitions remaining space.
     */
    Axis axis;

    public enum Axis {
        X, Y
    }

    /**
     * Find nearest neighbouring point in this KDTreeNodeInt, given 'point'.
     *
     * @param point
     * @return
     */
    public Point<T> nearestNeighbour(Point<T> point) {
        if(leftChild == null && rightChild == null) {
            return this.getElem();
        }
        else {
            if(compare(point) == -1) {
                return ((KDTreeNode<T>)leftChild).nearestNeighbour(point);
            }
            else {
                return ((KDTreeNode<T>)rightChild).nearestNeighbour(point);
            }
        }
    }

    /**
     * Insert a new Node of Point into this KDTreeNodeInt.
     *
     * @param newNode
     */
    @Override
    public void insert(Node<T> newNode) {
        KDTreeNode kdTreeNewNode = (KDTreeNode) newNode;
        kdTreeNewNode.axis = (axis == KDTreeNodeInt.Axis.X) ? KDTreeNodeInt.Axis.Y : KDTreeNodeInt.Axis.X;
        super.insert(newNode);
    }
}
