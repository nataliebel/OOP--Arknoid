import java.awt.Color;
import java.awt.Image;

import biuoop.DrawSurface;

/**
 * The type Background.
 */
public class Background implements Sprite {
    private Image image;
    private Color color;
    private boolean isImage;

    /**
     * Instantiates a new Background.
     *
     * @param image the image
     */
    public Background(Image image) {
        this.image = image;
        this.isImage = true;
    }

    /**
     * Instantiates a new Background.
     *
     * @param color the color
     */
    public Background(Color color) {
        this.color = color;
        this.isImage = false;
    }

    @Override
    public void drawOn(DrawSurface d) {
        if (this.isImage) {
            d.drawImage(0, 0, this.image);
        } else {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
    }

    @Override
    public void timePassed(double dt) {
    }

}
