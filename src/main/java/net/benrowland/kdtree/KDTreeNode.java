package net.benrowland.kdtree;

import net.benrowland.tree.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A Node Of Point (of T) in a KD Tree.
 */
public abstract class KDTreeNode<T> extends Node<T> {

    public KDTreeNode() {
        super();
    }

    public KDTreeNode(Point<T> elem) {
        super(elem);
    }

    static Map<Axis, Comparator> comparatorMap = new HashMap<Axis, Comparator>();
    static {
        comparatorMap.put(Axis.X, new XComparator());
        comparatorMap.put(Axis.Y, new YComparator());
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

    static class XComparator<T extends Number> implements Comparator<Point<T>> {
        public int compare(Point<T> o1, Point<T> o2) {
            if(o1 == null || o2 == null) {
                throw new IllegalArgumentException("Point is null! point 1: [" + o1 + "[  point 2: [" + o2 + "]");
            }

            Number x1 = o1.getX();
            Number x2 = o2.getX();

            // Convert to double to cover all scenarios
            if(x1.doubleValue() < x2.doubleValue()) {
                return -1;
            }
            else {
                return +1;
            }
        }
    }

    static class YComparator<T extends Number> implements Comparator<Point<T>> {
        public int compare(Point<T> o1, Point<T> o2) {
            if(o1 == null || o2 == null) {
                throw new IllegalArgumentException("Point is null! point 1: [" + o1 + "[  point 2: [" + o2 + "]");
            }

            Number y1 = o1.getY();
            Number y2 = o2.getY();

            // Convert to double to cover all scenarios
            if(y1.doubleValue() < y2.doubleValue()) {
                return -1;
            }
            else {
                return +1;
            }
        }
    }
}
