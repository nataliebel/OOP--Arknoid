import biuoop.DrawSurface;

import java.awt.Color;

/**
 * a class Ball.
 *
 * @author Natalie Balulu
 */
public class Ball implements Sprite {
    private Point middle;
    private int radius;
    private java.awt.Color color;
    private Velocity v;
    private Frame f;
    private GameEnvironment gameEnvironment;

    /**
     * constructor point by four parameter.
     *
     * @param center          point
     * @param r               int
     * @param color           java.awt.Color
     * @param gameEnvironment GameEnvironment
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.middle = center;
        this.radius = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }


    /**
     * Return the x values of this point.
     *
     * @return int. x
     */
    public int getX() {
        return (int) this.middle.getX();
    }

    /**
     * Return the y values of this point.
     *
     * @return int. y
     */
    public int getY() {
        return (int) this.middle.getY();
    }

    /**
     * Return the radius values.
     *
     * @return int. size
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Return the color of tne ball.
     *
     * @return color. color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on the given DrawSurface.
     *
     * @param surface DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(this.getColor());
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }

    @Override
    public void timePassed(double dt) {
        this.v = new Velocity(v.getDx() * dt, v.getDy() * dt);
        this.moveOneStep(dt);
        this.v = new Velocity(v.getDx() * 60, v.getDy() * 60);
    }

    /**
     * Insert velocity.
     *
     * @param newV Velocity
     */
    public void setVelocity(Velocity newV) {
        this.v = newV;
    }

    /**
     * Insert Velocity to v.
     *
     * @param dx double
     * @param dy double
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Velocity of v.
     *
     * @return v velocity
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * insert newF in Frame.
     *
     * @param newF Frame
     */
    public void setFrame(Frame newF) {
        this.f = newF;
    }

    /**
     * This method makes the movement of the ball and confirms that the ball.
     * won't get out of the borders.
     *
     * @param dt the dt
     */
    public void moveOneStep(double dt) {
        Velocity vel1 = new Velocity(this.getVelocity().getDx(), this.getVelocity().getDy());
        setVelocity(vel1);
        Line colLine = new Line(Math.round(this.middle.getX()), Math.round(this.middle.getY()),
                Math.round(this.middle.getX() + this.v.getDx()), Math.round(this.middle.getY() + this.v.getDy()));

        if (gameEnvironment.getClosestCollision(colLine) == null) {
            Velocity vel2 = new Velocity(this.getVelocity().getDx(), this.getVelocity().getDy());
            setVelocity(vel2);
            this.middle = vel1.applyToPoint(this.middle);
        } else {
            Point collisionPoint = gameEnvironment.getClosestCollision(colLine).collisionPoint();
            Collidable collitionObject = gameEnvironment.getClosestCollision(colLine).collisionObject();
            Velocity newVelocity = collitionObject.hit(this, collisionPoint, getVelocity());
            setVelocity(newVelocity);
            Velocity vel3 = new Velocity(this.getVelocity().getDx(), this.getVelocity().getDy());
            setVelocity(vel3);
            this.middle = vel3.applyToPoint(this.middle);
        }
    }

    /**
     * takes an angle and speed of object, and changes it to velocity.
     *
     * @param angle double.
     * @param speed double.
     * @return velocity. velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //dx=sinus of angle*speed
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        //dy=cosinus of angle*spped.
        //there are changes due to different direction in window.
        double dy = (-1) * Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * This method will add sprite to game.
     *
     * @param g game environment
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove from game.
     *
     * @param gameLevel the game level
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);

    }
}
