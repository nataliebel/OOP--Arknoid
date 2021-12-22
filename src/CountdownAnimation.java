import biuoop.DrawSurface;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {

    private double delay;
    private int countFrom;
    private SpriteCollection gameScreen;
    private long lastTime;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.delay = numOfSeconds / countFrom;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.lastTime = System.currentTimeMillis();
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        String text;

        if (countFrom == 0) {
            text = "GO";
        } else {
            text = Integer.toString(countFrom);
        }

        gameScreen.drawAllOn(d);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, text, 70);
        while (System.currentTimeMillis() < lastTime + (delay * 1000)) {
            System.out.print("");
        }
        lastTime = System.currentTimeMillis();
        countFrom--;
//        if (System.currentTimeMillis() >= lastTime + (delay * 1000)) {
//            lastTime = System.currentTimeMillis();
//            countFrom--;
//        }
    }

    @Override
    public boolean shouldStop() {
        if (this.countFrom < 0) {
            return true;
        }
        return false;
    }
}
