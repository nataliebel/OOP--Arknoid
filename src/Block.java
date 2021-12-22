import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * a class block.
 *
 * @author Natalie Balulu
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle shape;
    private Color color;
    private int number;
    private int numberCopy;
    private List<HitListener> hitListeners = new ArrayList<>();

    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;
    private Color borderColor;
    private boolean hasBorder;


    /**
     * constructor of the block.
     *
     * @param shape  the shep of the block it is a rectangle.
     * @param color  color
     * @param number int
     */
    public Block(Rectangle shape, Color color, int number) {
        this.shape = shape;
        this.color = color;
        if (number < 0) {
            number = 0;
        }
        this.number = number;
        this.images = new TreeMap<>();
        this.colors = new TreeMap<>();
        this.numberCopy = number;
    }

    /**
     * Instantiates a new Block.
     */
    public Block() {
        this.shape = new Rectangle(0, 0, 1, 1);
        this.colors = new TreeMap<Integer, Color>();
        this.images = new TreeMap<Integer, Image>();
        this.borderColor = Color.BLACK;
        this.hasBorder = false;
        this.number = 0;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Instantiates a new Block.
     *
     * @param other the other
     */
    public Block(Block other) {
        this.shape = other.shape;
        this.colors = other.colors;
        this.images = other.images;
        this.borderColor = other.borderColor;
        this.hasBorder = other.hasBorder;
        this.number = other.number;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return this.number;
    }

    /**
     * Return the color of tne block.
     *
     * @return color. color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Return the shape of tne block.
     *
     * @return shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint  the collision point.
     * @param currentVelocity the current velocity.
     * @param hitter          the current ball.
     * @return the current velocity.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if ((collisionPoint.getY() == shape.getLines()[0].start().getY())
                || (collisionPoint.getY() == shape.getLines()[3].start().getY())) {
            currentVelocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * (-1));
            if (number > 0) {
                number = number - 1;
            }
        }
        if (collisionPoint.getX() == shape.getLines()[1].start().getX()
                || collisionPoint.getX() == shape.getLines()[2].start().getX()) {
            currentVelocity = new Velocity(currentVelocity.getDx() * (-1), currentVelocity.getDy());
            if (number > 0) {
                number = number - 1;
            }
        }
        this.notifyHit(hitter);
        return currentVelocity;
    }

    /**
     * draw the block on the given DrawSurface.
     *
     * @param surface DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        if (this.images.containsKey(this.number)) {
            surface.drawImage(
                    (int) Math.round(this.shape.getUpperLeft().getX()),
                    (int) Math.round(this.shape.getUpperLeft().getY()),
                    this.images.get(this.number));
        } else if (this.colors.containsKey(this.number)) {
            surface.setColor(this.colors.get(this.number));
            surface.fillRectangle(
                    (int) Math.round(this.shape.getUpperLeft().getX()),
                    (int) Math.round(this.shape.getUpperLeft().getY()),
                    (int) Math.round(this.shape.getWidth()),
                    (int) Math.round(this.shape.getHeight()));
        } else if (this.images.containsKey(0)) {
            surface.drawImage(
                    (int) Math.round(this.shape.getUpperLeft().getX()),
                    (int) Math.round(this.shape.getUpperLeft().getY()),
                    this.images.get(0));
        } else if (this.colors.containsKey(0)) {
            surface.setColor(this.colors.get(0));
            surface.fillRectangle(
                    (int) Math.round(this.shape.getUpperLeft().getX()),
                    (int) Math.round(this.shape.getUpperLeft().getY()),
                    (int) Math.round(this.shape.getWidth()),
                    (int) Math.round(this.shape.getHeight()));
        }
        if (this.hasBorder) {
            surface.setColor(this.borderColor);
            surface.drawRectangle(
                    (int) Math.round(this.shape.getUpperLeft().getX()),
                    (int) Math.round(this.shape.getUpperLeft().getY()),
                    (int) Math.round(this.shape.getWidth()),
                    (int) Math.round(this.shape.getHeight()));
        }
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt dt
     */
    public void timePassed(double dt) {

    }

    /**
     * This method will add sprite and collidable to game.
     *
     * @param g game environment
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }

    /**
     * method to Block, which will be called whenever a hit() occurs,
     * and notifiers all of the registered HitListener objects by calling their hitEvent method.
     *
     * @param hitter ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Sets upper left.
     *
     * @param upperLeft the upper left
     */
    public void setUpperLeft(Point upperLeft) {
        this.shape = new Rectangle(upperLeft, this.shape.getWidth(),
                this.shape.getHeight());
    }

    /**
     * this method sets the location of this block.
     *
     * @param x the x coordinate of the upper left corner of the block.
     * @param y the y coordinate of the upper left corner of the block.
     */
    public void setUpperLeft(double x, double y) {
        Point upperLeft = new Point(x, y);
        this.setUpperLeft(upperLeft);
    }

    /**
     * this method sets the width of this block.
     *
     * @param width the new width.
     */
    public void setWidth(int width) {
        this.shape = new Rectangle(this.shape.getUpperLeft(),
                width, this.shape.getHeight());
    }


    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.shape = new Rectangle(this.shape.getUpperLeft(),
                this.shape.getWidth(), height);
    }


    /**
     * Sets number.
     *
     * @param numberOfhits the number ofhits
     */
    public void setNumber(int numberOfhits) {
        this.number = numberOfhits;
    }


    /**
     * Sets border color.
     *
     * @param bordersColor the borders color
     */
    public void setBorderColor(Color bordersColor) {
        this.borderColor = bordersColor;
    }


    /**
     * Sets colors.
     *
     * @param filling the filling
     */
    public void setColors(Map<Integer, Color> filling) {
        this.colors = filling;
    }


    /**
     * Sets images.
     *
     * @param filling the filling
     */
    public void setImages(Map<Integer, Image> filling) {
        this.images = filling;
    }


    /**
     * Sets border.
     *
     * @param isHasBorder the is has border
     */
    public void setBorder(boolean isHasBorder) {
        this.hasBorder = isHasBorder;
    }


    /**
     * Restart life.
     */
    public void restartLife() {
        this.number = numberCopy;
    }
}