import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T>, Animation {

    private List<String> messages;
    private List<String> keys;
    private List<Menu<T>> subMenus;
    private T status;
    private String backdraond;
    private List<T> task;
    private KeyboardSensor ks;
    private boolean stop;
    private AnimationRunner ar;


    /**
     * Instantiates a new Menu animation.
     *
     * @param backdraond the backdraond
     * @param ks         the ks
     * @param ar         the ar
     */
    public MenuAnimation(String backdraond, KeyboardSensor ks, AnimationRunner ar) {
        this.task = new ArrayList<T>();
        this.messages = new ArrayList<String>();
        this.keys = new ArrayList<String>();
        this.subMenus = new ArrayList<>();
        this.ks = ks;
        this.backdraond = backdraond;
        this.stop = false;
        this.ar = ar;
        this.status = null;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.task.add(returnVal);
    }

    @Override
    public T getStatus() {
        T tempStatus = this.status;
        this.status = null;
        this.stop = false;
        return tempStatus;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(subMenu);

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(new Color(182, 123, 201));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());


        d.setColor(new Color(115, 81, 153));
        d.drawText(100, 90, "Menu", 60);

        d.setColor(new Color(0, 0, 0));
        d.drawText(105, 94, "Menu", 60);

        for (int i = 0; i < this.messages.size(); ++i) {
            int optionX = (d.getWidth() / 5);
            int optionY = (d.getHeight() / 4) + i * 40;
            String optionText = "(" + (String) this.keys.get(i) + ") " + (String) this.messages.get(i);
            d.setColor(Color.BLACK);
            d.drawText(optionX + 1, optionY, optionText, 30);
            d.drawText(optionX - 1, optionY, optionText, 30);
            d.drawText(optionX, optionY + 1, optionText, 30);
            d.drawText(optionX, optionY - 1, optionText, 30);
            d.setColor(Color.WHITE);
            d.drawText(optionX, optionY, optionText, 30);
        }


        for (int i = 0; i < this.task.size(); i++) {
            if (this.ks.isPressed((String) this.keys.get(i))) {
                this.status = this.task.get(i);
                break;
            }
        }

    }

    @Override
    public boolean shouldStop() {
        return this.status != null;
    }

    /**
     * Restart menu.
     */
    public void restartMenu() {
        this.stop = false;
        this.status = null;

    }
}