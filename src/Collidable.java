/**
 * a interface Collidable.
 *
 * @author Natalie Balulu
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return //
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint  point
     * @param currentVelocity velocity
     * @param hitter          ball
     * @return //
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
