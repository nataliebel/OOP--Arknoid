import java.util.List;
import java.util.ArrayList;

/**
 * a class GameEnvironment.
 *
 * @author Natalie Balulu
 */
public class GameEnvironment {

    private List<Collidable> collidabel = new ArrayList<Collidable>();


    /**
     * add the given collidable to the environment.
     *
     * @param c Collidable
     */
    public void add(Collidable c) {
        this.collidabel.add(c);

    }

    /**
     * Gets collidabel.
     *
     * @return the collidabel
     */
    public List<Collidable> getCollidabel() {
        return this.collidabel;
    }


    /**
     * Assume an object moving from line.start() to line.end().
     * If this object wi ll not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory line
     * @return CollisionInfo - finalInfo or null
     */
    public CollisionInfo getClosestCollision(Line trajectory) {

        if (this.collidabel.isEmpty()) {
            return null;
        }
        Point minClosestPoint = trajectory.
                closestIntersectionToStartOfLine(this.collidabel.get(0).getCollisionRectangle());
        double minPointsDistance = Double.MAX_VALUE;
        CollisionInfo finalInfo = null;
        for (Collidable collidableItem : this.collidabel) {
            Point closestPoint = trajectory.closestIntersectionToStartOfLine(collidableItem.getCollisionRectangle());
            if (closestPoint != null) {
                if (minPointsDistance >= trajectory.start().distance(closestPoint)) {
                    minPointsDistance = trajectory.start().distance(closestPoint);
                    minClosestPoint = closestPoint;
                    finalInfo = new CollisionInfo(collidableItem, minClosestPoint);
                }
            }
        }
        if (minClosestPoint == null) {
            return null;
        }
        return finalInfo;
    }

}