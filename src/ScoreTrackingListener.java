/**
 * The type Score tracking listener.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;


    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() > 0) {
            this.currentScore.increase(5);
        }
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        }
    }
}
