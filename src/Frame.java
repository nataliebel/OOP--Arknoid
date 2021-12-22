/**
 * a class Frame.
 *
 * @author Natalie Balulu
 */
public class Frame {
    private int x;
    private int y;
    private int length;
    private int width;
    private java.awt.Color color;

    /**
     * construct point by four parameter.
     *
     * @param x      int
     * @param y      int
     * @param width  int
     * @param length int
     * @param color  color
     */
    public Frame(int x, int y, int width, int length, java.awt.Color color) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
        this.color = color;
    }

    /**
     * construct point by 5 parameter.
     *
     * @param x      int
     * @param y      int
     * @param width  int
     * @param length int
     */
    public Frame(int x, int y, int width, int length) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.width = width;
    }

    /**
     * Return the x values of this point.
     *
     * @return int
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return the y values of this point.
     *
     * @return int
     */
    public int getY() {
        return this.y;
    }

    /**
     * Return the length values.
     *
     * @return int
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Return the width values.
     *
     * @return int
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Return the color.
     *
     * @return Color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

}
