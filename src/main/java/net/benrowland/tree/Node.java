package net.benrowland.tree;

import net.benrowland.kdtree.Point;

/**
 * Represents a Node in a Tree.
 *
 * @see Tree
*/
public abstract class Node<T> {

    protected Point<T> elem;
    protected Node<T> leftChild;
    protected Node<T> rightChild;

    public Node() {

    }

    public Node(Point<T> elem) {
        this.elem = elem;
    }

    /**
     * Compare this node to the element in the other node.
     *
     * @param other
     * @return -1 if this node is "less than" the other node,
     *         +1 if this node is "more than" the other node.
     */
    public abstract int compare(Point<T> other);

    /**
     * Insert newNode into this node.
     *
     * @param newNode
     */
    public void insert(Node<T> newNode) {
        int result = this.compare(newNode.getElem());

        // If newNode < this node
        if(result == -1) {
            if(leftChild == null) {
                leftChild = newNode;
            }
            else {
                leftChild.insert(newNode);
            }
        }
        // if newNode >= this node
        else {
            if(rightChild == null) {
                rightChild = newNode;
            }
            else {
                rightChild.insert(newNode);
            }
        }
    }

    public Point<T> getElem() {
        return elem;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        for(Integer i=0;i<=depth; i++) sb.append("\t");
        sb.append(elem.toString());
        sb.append("\n");

        if(leftChild != null) sb.append(leftChild.toString(depth+1));
        if(rightChild != null) sb.append(rightChild.toString(depth+1));
        return sb.toString();
    }
}
