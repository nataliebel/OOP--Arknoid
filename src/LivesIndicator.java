import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Counter livsCounter;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param livsCounter the livs counter
     */
    public LivesIndicator(Counter livsCounter) {
        this.livsCounter = livsCounter;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.drawText(100, 14, "lives: " + this.livsCounter.getValue(), 15);
    }

    @Override
    public void timePassed(double dt) {

    }

    /**
     * add to game.
     *
     * @param g gamelevel.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
