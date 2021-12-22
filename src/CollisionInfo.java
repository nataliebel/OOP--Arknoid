/**
 * a class CollisionInfo.
 *
 * @author Natalie Balulu
 */
public class CollisionInfo {

    private Collidable collisionObject;
    private Point collisionPoint;

    /**
     * the point at which the collision occurs.
     *
     * @param collisionObject collidable
     * @param collisionPoint  point
     */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        this.collisionObject = collisionObject;
        this.collisionPoint = collisionPoint;
    }

    /**
     * the point involved in the collision.
     *
     * @return point - the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;

    }

    /**
     * the collidable object involved in the collision.
     *
     * @return Collidable - the collision Object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}