package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import net.benrowland.tree.Point;
import net.benrowland.tree.PointDouble;
import net.benrowland.tree.Tree;

import java.util.List;

/**
 * A KD-Tree is a Binary Tree representing points in k dimensions (this implementation is limited to
 * 2 dimensions).  It provides means for an efficient nearest neighbour search, where k is low.
 *
 * Each node represents a point in X-Y space, and has an axis (X or Y, alternating with the level
 * within the tree).  Its children are 'less than' or 'greater than' the parent in the axis of the
 * parent.
 *
 * E.g.:
 * - The root node divides space by 2 in X.
 *   - The left child is lower than the parent in X, and divides remaining space in Y.
 *     - The left child is lower than the parent in Y, and divides remaining space in X.
 *     - The right child is higher than the parent in Y, and divides remaining space in X.
 *   - The right child is higher than the parent in X, and divides remaining space in Y.
 *     - The left child is lower than the parent in Y, and divides remaining space in X.
 *     - The right child is higher than the parent in Y, and divides remaining space in X.
 *
 * ... and so on.
 *
 */
public class KDTreeDouble {

    // Prefer composition over inheritance ...
    private Tree<Double> tree;

    // *** Constructors

    public KDTreeDouble() {
        tree = new Tree<Double>();
    }

    // *** Static factory methods

    public static KDTreeDouble kdtree(List<PointDouble> points) {
        KDTreeDouble tree = new KDTreeDouble();
        tree.insert(KDTreeNodeDouble.buildRoot(points));

        return tree;
    }

    // *** Member functions

    public void insert(KDTreeNode<Double> node) {
        tree.insert(node);
    }

    /**
     * Find nearest neighbouring point in the KD Tree, given 'point'.
     *
     * @param point
     * @return nearest neighbouring Point in the KD Tree.
     */
    public Point<Double> nearestNeighbour(Point<Double> point) {
        return ((KDTreeNodeDouble)tree.getRootNode()).nearestNeighbour(point);
    }

    public Node getRootNode() {
        return tree.getRootNode();
    }

    public String toString() {
        return tree.getRootNode().toString();
    }
}
