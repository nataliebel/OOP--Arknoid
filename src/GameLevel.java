import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * a class GameLevel.
 *
 * @author Natalie Balulu
 */
public class GameLevel implements Animation {
    private static int width = 800;
    private static int height = 600;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper = new Sleeper();
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter live;
    private ScoreIndicator scoreIndicator;
    private Paddle padd;
    private boolean running = false;
    private biuoop.KeyboardSensor keyboard;
    private AnimationRunner runner;
    private LevelInformation levelInformation;


    /**
     * constructor of the game.
     *
     * @param levelInformation the level information
     * @param ks               the ks
     * @param ar               the ar
     * @param live             the live
     * @param score            the score
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor ks, AnimationRunner ar,
                     Counter live, Counter score) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
        this.score = score;
        this.live = live;
        this.keyboard = ks;
        this.runner = ar;
        this.levelInformation = levelInformation;
        this.scoreIndicator = new ScoreIndicator(score);
    }


    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        this.environment.add(c);
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Sets remaining blocks.
     *
     */
    public void setRemainingBlocks() {
        this.remainingBlocks = new Counter(0);
    }

    /**
     * Add game frame.
     */
    public final void addGameFrame() {
        Map<Integer, Color> map1 = new TreeMap<>();
        map1.put(0, Color.pink);
        Block block1 = new Block(new Rectangle(new Point(0, 20), 20, 600), Color.pink, 0);
        block1.setColors(map1);
        block1.addToGame(this);
        Block block2 = new Block(new Rectangle(new Point(0, 20), 800, 20), Color.pink, 0);
        block2.setColors(map1);
        block2.addToGame(this);
        Block block3 = new Block(new Rectangle(new Point(780, 20), 20, 600), Color.pink, 0);
        block3.setColors(map1);
        block3.addToGame(this);
        Block deathRegion = new Block(new Rectangle(new Point(0, 603), 800, 20), Color.pink, 0);
        deathRegion.addToGame(this);
        BallRemover remover = new BallRemover(this, remainingBalls);
        deathRegion.addHitListener(remover);
    }


    /**
     * Create balls.
     */
    public void createBalls() {
        for (int i = 0; i < levelInformation.numberOfBalls(); ++i) {
            Ball ball = new Ball(new Point(width / 2, 550), 10, Color.PINK, environment);
            ball.setVelocity(levelInformation.initialBallVelocities().get(i));
            ball.addToGame(this);
            remainingBalls.increase(1);
        }
    }


    /**
     * Create paddle paddle.
     *
     * @return the paddle
     */
    public Paddle createPaddle() {
        if (padd != null) {
            padd.removeFromGame(this);
        }
        Paddle paddle = new Paddle(new Rectangle(new Point(400 - (this.levelInformation.paddleWidth() / 2), 560),
                levelInformation.paddleWidth(), 3),
                keyboard, levelInformation.paddleSpeed(),
                new Color(240, 149, 157), 20, 780);
        paddle.addToGame(this);
        return paddle;
    }


    /**
     * Create blocks.
     */
    public void createBlocks() {
        HitListener blockRemover = new BlockRemover(this, remainingBlocks);
        HitListener scoreListener = new ScoreTrackingListener(score);

        for (int i = 0; i < levelInformation.numberOfBlocksToRemove(); ++i) {
            Block block = levelInformation.blocks().get(i);
            block.addHitListener(blockRemover);
            block.addHitListener(scoreListener);
            block.addToGame(this);
            remainingBlocks.increase(1);
        }
    }


    /**
     * Initialize.
     */
    public void initialize() {
        this.addSprite(levelInformation.getBackground());
        this.createBlocks();
        this.addGameFrame();
        scoreIndicator.addToGame(this);
        LivesIndicator li = new LivesIndicator(this.live);
        li.addToGame(this);
        sprites.addSprite(li);
        new NameIndicator(levelInformation.levelName()).addToGame(this);
    }


    /**
     * Play one turn.
     */
    public void playOneTurn() {
        this.createBalls();
        this.padd = this.createPaddle();

        this.runner.run(new CountdownAnimation(2, 3, sprites));

        this.running = true;
        this.runner.run(this);

        if (remainingBalls.getValue() == 0) {
            live.decrease(1);
        }

        if (remainingBlocks.getValue() == 0) {
            score.increase(100);
        }
    }

    @Override
    public boolean shouldStop() {
        if (this.remainingBlocks.getValue() == 0) {
            this.running = false;
        }
        if (this.remainingBalls.getValue() == 0) {
            this.running = false;
        }
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(200, 172, 197));
        d.fillRectangle(0, 0, 800, 600);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
        if (this.keyboard.isPressed("r")) {
            this.setRemainingBlocks();
        }
    }


    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollidabel().remove(c);

    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSpriteList().remove(s);
    }


    /**
     * Gets lives num.
     *
     * @return the lives num
     */
    public int getLivesNum() {
        return live.getValue();
    }


    /**
     * Gets remaining blocks num.
     *
     * @return the remaining blocks num
     */
    public int getRemainingBlocksNum() {
        return remainingBlocks.getValue();
    }


}


