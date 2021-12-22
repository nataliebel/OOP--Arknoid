import biuoop.DrawSurface;

/**
 * a interface Sprite.
 *
 * @author Natalie Balulu
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d  DrawSurface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     * @param dt the dt
     */
    void timePassed(double dt);
}