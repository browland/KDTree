package net.benrowland.kdtree;

import net.benrowland.tree.Node;
import java.lang.Override;

/**
 * A Node of Point (of Integer), in a KD Tree.
 *
*/
public class KDTreeNodeInt extends KDTreeNode<Integer> {

    public KDTreeNodeInt(Point<Integer> point) {
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
}
