/**
 * a class Velocity.
 *
 * @author Natalie Balulu
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * construct point by two parameter.
     *
     * @param dx double
     * @param dy double
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * return the dx values of this dx.
     *
     * @return double
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * return the dx values of this dy.
     *
     * @return double
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy).
     *
     * @param p Point
     * @return point
     */
    public Point applyToPoint(Point p) {
        Point thePoint = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return thePoint;
    }

    /**
     * Gives the ball a speed in a certain direction.
     *
     * @param angle the new angle that the ball will move in the surface
     * @param speed the new speed of the ball
     * @return a new velocity to the ball
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        double angleRad = Math.toRadians(angle - 90.0);
        double dx = Math.round(Math.cos(angleRad) * speed);
        double dy = Math.round(Math.sin(angleRad) * speed);
        return new Velocity(dx, dy);
    }

}