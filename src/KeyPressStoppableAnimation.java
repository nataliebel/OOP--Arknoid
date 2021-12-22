import biuoop.DrawSurface;
import biuoop.KeyboardSensor;


/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the sensor
     * @param key       the key
     * @param animation the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);
        if (!this.sensor.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
        if (this.sensor.isPressed(this.key) && !this.isAlreadyPressed) {
            this.stop = true;
        }

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    /**
     * Restart key.
     */
    public void restartKey() {
        this.stop = false;
        this.isAlreadyPressed = true;
    }
}