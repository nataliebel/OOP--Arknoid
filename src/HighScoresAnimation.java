import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;


/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;
    private String endKey;
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * The constant WIDTH.
     */
    public static final int WIDTH = 800;
    /**
     * The constant HEIGHT.
     */
    public static final int HEIGHT = 600;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores   the scores
     * @param endKey   the end key
     * @param keyboard the keyboard
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, KeyboardSensor keyboard) {
        this.scores = scores;
        this.endKey = endKey;
        this.keyboard = keyboard;
        this.stop = false;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(172, 154, 255));
        d.fillRectangle(0, 0, WIDTH, HEIGHT);
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 7, d.getHeight() / 6, "High Scores", 46);
        d.drawText(d.getWidth() / 7, d.getHeight() / 4, "Player Name", 30);
        d.drawText((d.getWidth() / 2) + 20, d.getHeight() / 4, "Score", 30);
        d.setColor(Color.BLACK);
        d.drawRectangle((d.getWidth() / 7), (d.getHeight() / 4) + 10, 600, 5);
        d.setColor(Color.GRAY);
        d.fillRectangle((d.getWidth() / 7), (d.getHeight() / 4) + 10, 600, 4);
        for (int i = 0, j = d.getHeight() / 3; i < scores.size(); i++, j += 50) {
            d.drawText(d.getWidth() / 7, j, this.scores.getHighScores().get(i).getName(), 25);
            d.drawText((d.getWidth() / 2) + 20, j, Integer.toString(scores.getHighScores().get(i).getScore()), 25);
        }
        d.setColor(Color.MAGENTA);
        d.drawText(235, 540, "Press space to continue", 30);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}