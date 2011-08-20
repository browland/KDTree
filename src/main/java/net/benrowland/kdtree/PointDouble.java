package net.benrowland.kdtree;

/**
 */
public class PointDouble extends Point<Double> {

    // For comparison of doubles
    private static final double EPSILON = 0.00001;

    public PointDouble(Double x, Double y) {
        super(x, y);
    }

    @Override
    protected double distance(Point<Double> other) {
        double distSquared = Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2);
        return Math.sqrt(distSquared);
    }

    public boolean equals(Object other) {
        Point<Double> otherPoint = (Point<Double>) other;
        return (otherPoint.getX() > getX() - EPSILON && otherPoint.getX() < getX() + EPSILON
            && otherPoint.getY() > getY() - EPSILON && otherPoint.getY() < getY() + EPSILON);
    }
}
