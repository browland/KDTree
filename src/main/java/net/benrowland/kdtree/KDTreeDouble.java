package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import net.benrowland.tree.Tree;

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
 * todo Re-balancing (currently relies on Nodes being added in a convenient order)
 *
 */
public class KDTreeDouble {

    // Prefer composition over inheritance ...
    private Tree<Double> tree;

    /**
     * Inserts a 2 dimensional Point into the KD Tree.  Does not re-balance the tree,
     * so add points mindfully.
     *
     * @param point
     */
    public void insert(Point<Double> point) {
        KDTreeNodeDouble node = new KDTreeNodeDouble(point);
        if(tree == null) {
            // Root node partitions in X dimension (arbitrary choice)
            node.axis = KDTreeNode.Axis.X;
            tree = new Tree<Double>();
        }
        tree.insert(node);
    }

    /**
     * Find nearest neighbouring point in the KD Tree, given 'point'.
     *
     * @param point
     * @return nearest neighbouring Point in the KD Tree.
     */
    // todo back-track to find better matches
    public Point<Double> nearestNeighbour(Point<Double> point) {
        Point<Double> currentBest = ((KDTreeNodeDouble)tree.getRootNode()).nearestNeighbour(point);
        return currentBest;
    }

    public Node getRootNode() {
        return tree.getRootNode();
    }
}
