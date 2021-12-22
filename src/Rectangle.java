import java.util.ArrayList;

/**
 * a class rectangle.
 *
 * @author Natalie Balulu
 */
public class Rectangle {
    private double width;
    private double height;
    private Point upperLeft;

    private Line[] lines;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper point on the right.
     * @param width     the width of the rectangle.
     * @param height    the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;

        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(upperRight.getX(), lowerLeft.getY());

        this.lines = new Line[4];

        //upper Line
        this.lines[0] = new Line(this.upperLeft, upperRight);
        //right Line
        this.lines[1] = new Line(upperRight, lowerRight);
        //left Line
        this.lines[2] = new Line(this.upperLeft, lowerLeft);
        //lower Line
        this.lines[3] = new Line(lowerLeft, lowerRight);
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public Rectangle(double x, double y, double width, double height) {
        this.upperLeft = new Point(x, y);
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     *
     * @param line line
     * @return list of lines
     */
    public java.util.List intersectionPoints(Line line) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < this.lines.length; i++) {
            if (this.lines[i].isIntersecting(line)) {
                list.add(this.lines[i].intersectionWith(line));
            }
        }

        return list;
    }

    /**
     * Return the width of the rectangle.
     *
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Return the height of the rectangle.
     *
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Return array with all of the rectangle lines.
     *
     * @return array of line
     */
    public Line[] getLines() {
        return this.lines;
    }

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return the upper left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * The function finds the points of the top point.
     *
     * @param upper point.
     */
    public void setUpperLeft(Point upper) {
        this.upperLeft = upper;
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(upperRight.getX(), lowerLeft.getY());
        //upper Line
        this.lines[0] = new Line(this.upperLeft, upperRight);
        //right Line
        this.lines[1] = new Line(upperRight, lowerRight);
        //left Line
        this.lines[2] = new Line(this.upperLeft, lowerLeft);
        //lower Line
        this.lines[3] = new Line(lowerLeft, lowerRight);
    }

    /**
     * The function divides the Rectangle into 5 parts.
     *
     * @param i int
     * @return array of int
     */
    public Rectangle getPartOfRectangle(int i) {
        Rectangle[] recLines = new Rectangle[i];
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY();
        double heigh = this.getHeight();
        double lentgh = this.getWidth() / 5;
        for (int k = 0; k < 5; k++) {
            recLines[k] = new Rectangle(new Point(x, y), lentgh, heigh);
            x += lentgh;
        }
        return recLines[i];
    }

}
