import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * a class SpriteCollection.
 *
 * @author Natalie Balulu
 */
public class SpriteCollection {
    private List<Sprite> spriteList = new ArrayList<>();

    /**
     * Gets sprite list.
     *
     * @return the sprite list
     */
    public List<Sprite> getSpriteList() {
        return this.spriteList;
    }

    /**
     * list of Sprite Collection.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList();
    }

    /**
     * This method will add sprite.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * call timePassed() on all sprites.
     *
     * @param dt the dt
     */
    public void notifyAllTimePassed(double dt) {
        List<Sprite> list = new ArrayList<Sprite>(this.spriteList);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).timePassed(dt);
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d DrawSurface
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < spriteList.size(); i++) {
            spriteList.get(i).drawOn(d);
        }
    }
}