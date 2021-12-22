
import java.util.List;

/**
 * a line class.
 *
 * @author Natalie Balulu
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * construct point by two parameter.
     *
     * @param start this the start value of the point
     * @param end   this the end value of the poin
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;

    }

    /**
     * construct point by four parameter, that creates two points.
     *
     * @param x1 the value of the x of the p1 point.
     * @param y1 the value of the y of the p1 point.
     * @param x2 the value of the x of the p2 point.
     * @param y2 the value of the y of the p2 point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        this.start = p1;
        this.end = p2;
    }

    /**
     * The function checks the distance of two points
     * According to a formula of distance calculation.
     *
     * @return the length of the line
     */
    public double length() {
        double dx = this.start.getX() - this.end.getX();
        double dy = this.start.getY() - this.end.getY();
        return Math.sqrt((dx * dx) + (dy * dy));

    }

    /**
     * The function calculates what is the midpoint between two points
     * By a formula of finding a midpoint.
     *
     * @return the middle point of the line.
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        Point pointMid = new Point(middleX, middleY);
        return pointMid;
    }

    /**
     * Start point.
     *
     * @return the start point of the line
     */
    public Point start() {

        return this.start;
    }

    /**
     * End point.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * This method checks if the line has point of intersection with anther line
     * by use the others methods. The method returns true if the lines
     * intersect, false otherwise.
     *
     * @param other the line for checking
     * @return boolean true or false
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * This method returns the intersection point if the lines intersect, and
     * null otherwise. The method checks this by calls to other methods.
     *
     * @param other the line that receives to check the intersection with the              other lines
     * @return intersection point or null. returns the intersection point if the lines intersect,and null otherwise.
     */
    public Point intersectionWith(Line other) {
        double x1 = this.start.getX();
        double x2 = this.end.getX();
        double x3 = other.start().getX();
        double x4 = other.end().getX();
        double y1 = this.start.getY();
        double y2 = this.end.getY();
        double y3 = other.start().getY();
        double y4 = other.end().getY();
        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        // the slope of this line
        double slope1 = (this.end.getY() - this.start.getY()) * (this.end.getX() - this.start.getX());
        // the slope of the other line
        double slope2 = (other.end().getY() - other.start().getY()) * (other.end().getX() - other.start().getX());
        if (slope1 == slope2) {
            if (slope2 == 0) {
                if (this.start().getX() == this.end().getX() && other.start().getY() == other.end().getY()) {
                    if (this.isIntersectVertical(other)) {
                        return new Point(this.start().getX(), other.start().getY());
                    }
                }

                if (this.start().getY() == this.end().getY() && other.start().getX() == other.end().getX()) {
                    if (other.isIntersectVertical(this)) {
                        return new Point(other.start().getX(), this.start().getY());
                    }
                }
            }
            Point p1 = equalsSlopesInsect(other);
            return p1;
        } else if (d == 0) {
            return null;
        } else {
            // calculates the point
            Point p = new Point(((x3 - x4) * (x1 * y2 - y1 * x2)
                    - (x1 - x2) * (x3 * y4 - y3 * x4)) / d,
                    ((y3 - y4) * (x1 * y2 - y1 * x2)
                            - (y1 - y2) * (x3 * y4 - y3 * x4)) / d);
            if (this.pointIsInTheLimits(p.getX(), p.getY(), other)) {
                return p;
            } else {
                return null;
            }
        }

    }

    /**
     * is Intersect Vertical.
     *
     * @param other Line
     * @return inRangeX && inRangeY
     */
    private boolean isIntersectVertical(Line other) {
        boolean inRangeX = (this.end().getX() >= other.start().getX() && this.end().getX() <= other.end().getX())
                || (this.end().getX() <= other.start().getX() && this.end().getX() >= other.end().getX());
        boolean inRangeY = (other.end().getY() >= this.start().getY() && other.end().getY() <= this.end().getY())
                || (other.end().getY() <= this.start().getY() && other.end().getY() >= this.end().getY());
        return inRangeX && inRangeY;
    }

    /**
     * the function check if the point is in the limits.
     *
     * @param intersectionX the x
     * @param intersectionY the y
     * @param other         line
     * @return boolean if the point is in the limits return true dnd if the point is not in the limits return false
     */
    public boolean pointIsInTheLimits(double intersectionX, double intersectionY, Line other) {

        if (Math.abs(intersectionX - (int) intersectionX) >= 0.9999999999990) {
            intersectionX = Math.round(intersectionX);
        }
        if (Math.abs(intersectionY - (int) intersectionY) >= 0.9999999999990) {
            intersectionY = Math.round(intersectionY);
        }

        if ((intersectionX >= Math.min(this.start.getX(), this.end.getX()))
                && (intersectionX <= Math.max(this.start.getX(), this.end.getX()))
                && (intersectionX >= Math.min(other.start().getX(), other.end().getX()))
                && (intersectionX <= Math.max(other.start().getX(), other.end().getX()))
                && (intersectionY >= Math.min(this.start.getY(), this.end.getY()))
                && (intersectionY <= Math.max(this.start.getY(), this.end.getY()))
                && (intersectionY >= Math.min(other.start().getY(), other.end().getY()))
                && (intersectionY <= Math.max(other.start().getY(), other.end().getY()))) {
            return true;
        }
        return false;
    }

    /**
     * if the slopes are equals the function return the point.
     *
     * @param other the line that receives to check the slopes with the              other lines.
     * @return point or null
     */
    public Point equalsSlopesInsect(Line other) {
        if (this.start.getX() == other.end().getX()) {
            return this.start;
        } else if (this.end.getX() == other.start().getX()) {
            return this.end;
        } else if (this.start.getX() == other.start().getX()) {
            return this.start;
        } else if (this.end.getX() == other.end().getX()) {
            return this.end;
        }
        return null;
    }

    /**
     * equals -- return true is the lines are equal, false otherwise.
     *
     * @param other line
     * @return boolean - true or false.
     */
    public boolean equals(Line other) {
        if (((this.start == other.start()) && (this.end == other.end()))
                || ((this.start == other.end()) && (this.end == other.start()))) {
            return true;
        }
        return false;
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     *
     * @param rect the object that the involved in the collision with the ball.
     * @return intersectionPoint point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List intersectionPoints = rect.intersectionPoints(this);

        if (intersectionPoints.isEmpty()) {
            return null;
        }

        Point intersectionPoint = (Point) intersectionPoints.get(0);
        for (int i = 1; i < intersectionPoints.size(); i++) {
            Point newPoint = (Point) intersectionPoints.get(i);
            if (this.start.distance(intersectionPoint) > this.start.distance(newPoint)) {
                intersectionPoint = newPoint;
            }
        }

        return intersectionPoint;
    }

}