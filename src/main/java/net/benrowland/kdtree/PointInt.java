package net.benrowland.kdtree;

/**
 */
public class PointInt extends Point<Integer> {

    public PointInt(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    protected double distance(Point<Integer> other) {
        double distSquared = Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2);
        return Math.sqrt(distSquared);
    }

    public boolean equals(Object other) {
        Point<Integer> otherPoint = (Point<Integer>) other;
        return (otherPoint.getX().equals(getX()) && otherPoint.getY().equals(getY()));
    }
}
