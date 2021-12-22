import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * a class Paddle.
 *
 * @author Natalie Balulu
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle r;
    private Color color;
    private int limitRight;
    private int limitLeft;
    private int speed;

    /**
     * constructor of the Paddle.
     *
     * @param r          Rectangle
     * @param keyboard   biuoop.KeyboardSensor
     * @param speed      int
     * @param color      Color
     * @param limitLeft  int
     * @param limitRight int
     */
    public Paddle(Rectangle r, biuoop.KeyboardSensor keyboard, int speed, Color color, int limitLeft, int limitRight) {
        this.r = r;
        this.limitRight = limitRight;
        this.limitLeft = limitLeft;
        this.keyboard = keyboard;
        this.speed = speed;
        if (speed < 0) {
            this.speed = speed * -1;
        }
        this.color = color;
    }

    /**
     * Return the color of tne block.
     *
     * @return color. color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Move the paddle to the left.
     *
     * @param dt the dt
     */
    public void moveLeft(double dt) {
        if (this.limitLeft < this.r.getUpperLeft().getX()) {
            Velocity velocityLeft = new Velocity(dt * this.speed * -1, 0);
            this.r.setUpperLeft(velocityLeft.applyToPoint(this.r.getUpperLeft()));
        }
    }

    /**
     * Move the paddle to the right.
     *
     * @param dt the dt
     */
    public void moveRight(double dt) {
        if (this.limitRight > this.r.getUpperLeft().getX() + this.r.getWidth()) {
            Velocity velocityRight = new Velocity(dt * this.speed, 0);
            this.r.setUpperLeft(velocityRight.applyToPoint(this.r.getUpperLeft()));
        }
    }

    /**
     * method should check if the "left" or "right" keys are pressed, and if so move it accordingly.
     *
     * @param dt dt
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        }
    }

    /**
     * draw the paddle on the given DrawSurface.
     *
     * @param d DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.getColor());
        d.fillRectangle((int) this.r.getUpperLeft().getX(), (int) this.r.getUpperLeft().getY(),
                (int) this.r.getWidth(), (int) this.r.getHeight());

    }

    /**
     * get Collision Rectangle.
     *
     * @return rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.r;
    }

    /**
     * Enter the function if someone hits the paddle and then change its direction.
     *
     * @param collisionPoint  point
     * @param currentVelocity velocity
     * @param hitter          ball
     * @return the velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Values.
        double pointXValue = Math.round(collisionPoint.getX());
        double pointYValue = Math.round(collisionPoint.getY());
        double leftWallXvalue = this.r.getUpperLeft().getX();
        double rightWallXvalue = leftWallXvalue + this.r.getWidth();
        double upperWallYvalue = this.r.getUpperLeft().getY();
        double lowerWallYvalue = upperWallYvalue + this.r.getHeight();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double theSpeed = Math.sqrt(dx * dx + dy * dy);
        double difference = (rightWallXvalue - leftWallXvalue) / 5;
        double zoneA = leftWallXvalue + 1 * difference;
        double zoneB = leftWallXvalue + 2 * difference;
        double zoneC = leftWallXvalue + 3 * difference;
        double zoneD = leftWallXvalue + 4 * difference;

        // New Velocity object which will be returned, finally.
        Velocity velocity = null;

        //upper hit - categorized by the 5 Parts!
        if (pointYValue == upperWallYvalue) {
            if (pointXValue >= leftWallXvalue && pointXValue < zoneA) {
                velocity = Velocity.fromAngleAndSpeed(300, theSpeed);
            } else if (pointXValue >= zoneA && pointXValue < zoneB) {
                velocity = Velocity.fromAngleAndSpeed(330, theSpeed);
            } else if (pointXValue >= zoneB && pointXValue < zoneC) {
                velocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
            } else if (pointXValue >= zoneC && pointXValue < zoneD) {
                velocity = Velocity.fromAngleAndSpeed(30, theSpeed);
            } else if (pointXValue >= zoneD && pointXValue <= rightWallXvalue) {
                velocity = Velocity.fromAngleAndSpeed(60, theSpeed);
            }
        }
        //sides hit
        if (pointXValue == rightWallXvalue || pointXValue == leftWallXvalue) {
            velocity = new Velocity((currentVelocity.getDx() * -1), currentVelocity.getDy());
        }

        //down hit
        if (pointYValue == lowerWallYvalue) {
            velocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        }
        return velocity;
    }


    /**
     * Add this paddle to the game.
     *
     * @param g game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);

    }

    /**
     * Remove from game.
     *
     * @param g the g
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

}