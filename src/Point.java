/**
 * a point class.
 *
 * @author Natalie Balulu
 */
public class Point {
    private double x;
    private double y;

    /**
     * construct point by two parameter.
     *
     * @param x this the x value of the point
     * @param y this the y value of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * distance -- return the distance of this point to the other point.
     *
     * @param other a point.
     * @return double
     */
    public double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * equals return true is the points are equal, false otherwise.
     *
     * @param other a point.
     * @return boolean- true or false.
     */
    public boolean equals(Point other) {
        if ((this.x == other.getX()) && (this.y == other.getY())) {
            return true;
        }
        return false;
    }

    /**
     * Return the x values of this point.
     *
     * @return double.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y values of this point.
     *
     * @return double.
     */
    public double getY() {
        return this.y;
    }
}
