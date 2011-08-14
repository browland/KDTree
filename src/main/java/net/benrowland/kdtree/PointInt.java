package net.benrowland.kdtree;

/**
 */
public class PointInt extends Point<Integer> {

    public PointInt(Integer x, Integer y) {
        super(x, y);
    }

    public boolean equals(Object other) {
        Point<Integer> otherPoint = (Point<Integer>) other;
        return (otherPoint.getX().equals(getX()) && otherPoint.getY().equals(getY()));
    }
}
