import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(182, 123, 201));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        d.setColor(new Color(0, 0, 0));
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);

    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
