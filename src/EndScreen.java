import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type End screen.
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private String text;
    private int score;

    /**
     * Instantiates a new End screen.
     *
     * @param k     the k
     * @param text  the text
     * @param score the score
     */
    public EndScreen(KeyboardSensor k, String text, int score) {
        this.keyboard = k;
        this.stop = false;
        this.text = text;
        this.score = score;
    }
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, text + " Your score is " + Integer.toString(score), 40);
    }
    @Override
    public boolean shouldStop() {

        return this.stop;
    }
}
