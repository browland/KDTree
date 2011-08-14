package net.benrowland.kdtree;

/**
 * A Node of Point (of Double), in a KD Tree.
 *
*/
public class KDTreeNodeDouble extends KDTreeNode<Double> {

    public KDTreeNodeDouble(Point<Double> elem) {
        super(elem);
    }

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
}
