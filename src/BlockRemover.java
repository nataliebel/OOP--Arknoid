/**
 * The type Block remover.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel     the game level
     * @param removedBlocks the removed blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;

    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the gameLevel. Remember to remove this listener from the block
     * that is being removed from the gameLevel.
     *
     * @param beingHit block.
     * @param hitter   ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.gameLevel);
            this.remainingBlocks.decrease(1);
        }
    }
}
