package net.benrowland.kdtree;

/**
 * A Point in 2-dimensional space.
 */
public abstract class Point<T> {

    protected T x;
    protected T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    /**
     * Determines whether the current Point is closer to testPoint, than
     * otherPoint.
     *
     * @param testPoint
     * @param otherPoint
     * @return
     */
    public boolean closer(Point<T> testPoint, Point<T> otherPoint) {
        return this.distance(testPoint) < otherPoint.distance(testPoint);
    }

    /**
     * Find Euclidian distance between this point and other.
     *
     * @param other
     * @return
     */
    protected abstract double distance(Point<T> other);

    public String toString() {
        return getClass().getSimpleName() + " [x: " + x + ", y: " + y + "]";
    }
}
