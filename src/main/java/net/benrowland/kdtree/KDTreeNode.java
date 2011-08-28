package net.benrowland.kdtree;

import com.sun.org.apache.xalan.internal.xsltc.dom.CurrentNodeListFilter;
import net.benrowland.tree.Node;
import net.benrowland.tree.Point;

import java.util.*;

/**
 * A Node Of Point (of T) in a KD Tree.
 */
public abstract class KDTreeNode<T> extends Node<T> {

    public KDTreeNode() {
        super();
    }

    public KDTreeNode(KDTreeNode parent) {
        super(parent);
    }

    public KDTreeNode(Point<T> elem) {
        super(elem);
    }

    /**
     * Map for looking up the appropriate Comparator based on the split axis.
     * Used to sort nodes by the axis, when constructing the tree.
     */
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
    public KDTreeNode<T> nearestNeighbour(Point<T> point) {

        // First find leaf node closest to point.
        KDTreeNode<T> currentBest = findNearestLeaf(point);

        // Now back-track up the tree, and if a parent node is closer to
        // point, save that as the new current best.
        currentBest = currentBest.unwindForBetterMatch(point, this);

        return currentBest;
    }

    /**
     * Finds the leaf node which is closest to point, by simple depth-first
     * recursion.
     *
     * @param point
     * @return
     */
    protected KDTreeNode<T> findNearestLeaf(Point<T> point) {
        // If we've reached a leaf node
        if(leftChild == null && rightChild == null) {
            return this;
        }
        // Non-leaf node
        else {
            if(compare(point) == -1) {
                return ((KDTreeNode<T>)leftChild).findNearestLeaf(point);
            }
            else {
                return ((KDTreeNode<T>)rightChild).findNearestLeaf(point);
            }
        }
    }

    /**
     * Instructs the current node (always a nearest leaf) to back-track up the tree, looking
     * for a parent node which is closer to point, or trying the other branch if necessary.
     *
     * Passes the current node as the current best.
     *
     * @param point
     * @param callingNode
     * @return
     */
    protected KDTreeNode<T> unwindForBetterMatch(Point<T> point, KDTreeNode<T> callingNode) {
        return unwindForBetterMatch(point, callingNode, this);
    }

    /**
     * Instructs the current node (always a nearest leaf) to back-track up the tree, looking
     * for a parent node which is closer to point, or trying the other branch if necessary.
     *
     * @param point
     * @param callingNode
     * @param currentBest
     * @return
     */
    protected KDTreeNode<T> unwindForBetterMatch(Point<T> point, KDTreeNode<T> callingNode, KDTreeNode<T> currentBest) {
        KDTreeNode<T> parentKdTreeNode = (KDTreeNode<T>) getParent();

        // If we have back-tracked as far as the node which initiated the back-tracking, then terminate the back-tracking.
        // This is necessary (a) to prevent back-tracking beyond the root node, and (b) to prevent inner back-tracking from
        // back-tracking further than the outer back-tracking (this happens because we repeat the whole nearest neighbour
        // search within the existing one, when trying the other branch).
        if(this == callingNode) {
            return currentBest;
        }

        // Check whether the parent node is closer to point than the currentBest
        if(parentKdTreeNode.getElem().closer(point, currentBest.getElem())) {
            currentBest = parentKdTreeNode;
        }

        // Check whether there could be closer points on the other branch, and if so, traverse down that branch, looking
        // for a closer match.  There could be closer points on the other branch if the distance in the split plane for
        // 'parent' from search point to parent is less than the distance between the search point and the current best.
        if(parentKdTreeNode.shouldTryOtherBranchForBetterMatch(point, currentBest)) {
            KDTreeNode<T> otherChild = (KDTreeNode<T>) parentKdTreeNode.getOtherChild(this);
            if(otherChild != null) {
                KDTreeNode<T> betterCandidate = otherChild.nearestNeighbour(point);
                if(betterCandidate.getElem().closer(point, currentBest.getElem())) {
                    currentBest = betterCandidate;
                }
            }
        }

        // Continue to unwind, by recursing
        return parentKdTreeNode.unwindForBetterMatch(point, callingNode, currentBest);
    }

    /**
     * Determines whether the other branch could possibly contain a better match for point.  This is true if
     * the distance between point and currentBest is greater than the distance between point and the split plane
     * of the current point.
     *
     * Declared abstract so subclasses can use comparison operations with the actual type.
     *
     * @param point
     * @param currentBest
     * @return
     */
    protected abstract boolean shouldTryOtherBranchForBetterMatch(Point<T> point, KDTreeNode<T> currentBest);

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

    /**
     * The distance between this node, and the Point 'other', purely in the axis that this node splits upon.
     *
     * Declared abstract so subclasses can perform comparison operations on the actual type.
     *
     * @param other
     * @return
     */
    public abstract T distanceInAxis(Point<T> other);

    /**
     * Compares 2 Points according to the value in the X axis.
     *
     * @param <T>
     */
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

/**
     * Compares 2 Points according to the value in the Y axis.
     *
     * @param <T>
     */
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

    static class TreePartitionStore<T extends Point> {
        List<T> leftSidePointsSortedInX;
        List<T> leftSidePointsSortedInY;
        List<T> rightSidePointsSortedInX;
        List<T> rightSidePointsSortedInY;

        static <T extends Point> TreePartitionStore create(Axis axis, int medianIndex, List<T> pointsSortedInX, List<T> pointsSortedInY) {
            TreePartitionStore store = new TreePartitionStore();
            if(Axis.X == axis) {
                store.leftSidePointsSortedInX = pointsSortedInX.subList(0, medianIndex);
                store.rightSidePointsSortedInX = pointsSortedInX.subList(medianIndex+1, pointsSortedInX.size());

                store.leftSidePointsSortedInY = new ArrayList<T>(pointsSortedInY);
                store.leftSidePointsSortedInY.retainAll(store.leftSidePointsSortedInX);

                store.rightSidePointsSortedInY = new ArrayList<T>(pointsSortedInY);
                store.rightSidePointsSortedInY.retainAll(store.rightSidePointsSortedInX);
            }
            else if(Axis.Y == axis) {
                store.leftSidePointsSortedInY = pointsSortedInY.subList(0, medianIndex);
                store.rightSidePointsSortedInY = pointsSortedInY.subList(medianIndex+1, pointsSortedInY.size());

                store.leftSidePointsSortedInX = new ArrayList<T>(pointsSortedInX);
                store.leftSidePointsSortedInX.retainAll(store.leftSidePointsSortedInY);

                store.rightSidePointsSortedInX = new ArrayList<T>(pointsSortedInX);
                store.rightSidePointsSortedInX.retainAll(store.rightSidePointsSortedInY);
            }
            return store;
        }
    }
}
