import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The type Animation runner.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    private double currentMillis = 0;

    /**
     * Instantiates a new Animation runner.
     *
     * @param gui             the gui
     * @param framesPerSecond the frames per second
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * Run.
     *
     * @param animation the animation
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            // timing
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            double dt = (double) 1 / this.framesPerSecond;
            animation.doOneFrame(d, dt);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
