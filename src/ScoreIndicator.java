import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter number;


    /**
     * Instantiates a new Score indicator.
     *
     * @param number the number
     */
    public ScoreIndicator(Counter number) {
        this.number = number;
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.GRAY);
        d.fillRectangle(0, 0, 800, 20);
        d.drawRectangle(0, 0, 800, 20);

        d.setColor(Color.white);
        d.drawText(400, 13, "Score: " + this.number.getValue(), 15);

    }
    @Override
    public void timePassed(double dt) {

    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
