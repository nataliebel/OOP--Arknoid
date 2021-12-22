/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    private GameLevel g;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param g       the g
     * @param counter the counter
     */
    public BallRemover(GameLevel g, Counter counter) {
        this.g = g;
        this.remainingBalls = counter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getCollisionRectangle().getUpperLeft().getY() == 603) {
            hitter.removeFromGame(g);
            this.remainingBalls.decrease(1);
        }
    }
}
