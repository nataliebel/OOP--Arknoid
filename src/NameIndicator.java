import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Name indicator.
 */
public class NameIndicator implements Sprite {
    private String name;


    /**
     * Instantiates a new Name indicator.
     *
     * @param name the name
     */
    public NameIndicator(String name) {
        this.name = name;
    }
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.white);
        d.drawText(600, 13, "Level Name: " + this.name, 15);

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
