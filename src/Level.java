import java.util.ArrayList;
import java.util.List;


/**
 * The type Level.
 */
public class Level implements LevelInformation {
    private String levelName;
    private Sprite background;
    private Integer numberOfBalls;
    private Integer numberOfBlocksToRemove;
    private List<Block> blocks;
    private List<Velocity> initialBallVelocities;
    private Integer paddleSpeed;
    private Integer paddleWidth;
    private Integer paddleHeight;
    private Integer blocksStartX;
    private Integer blocksStartY;
    private Integer rowHeight;

    /**
     * Instantiates a new Level.
     */
    public Level() {
        this.levelName = null;
        this.background = null;
        this.numberOfBalls = null;
        this.numberOfBlocksToRemove = null;
        this.blocks = new ArrayList<Block>();
        this.initialBallVelocities = new ArrayList<Velocity>();
        this.paddleSpeed = null;
        this.paddleWidth = null;
        this.paddleHeight = null;
        this.blocksStartX = null;
        this.blocksStartY = null;
        this.rowHeight = null;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * Sets level name.
     *
     * @param name the name
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Sets background.
     *
     * @param s the s
     */
    public void setBackground(Sprite s) {
        this.background = s;
    }

    @Override
    public Integer numberOfBalls() {
        return this.numberOfBalls;
    }

    /**
     * Sets number of balls.
     *
     * @param numOfBalls the num of balls
     */
    public void setNumberOfBalls(int numOfBalls) {
        this.numberOfBalls = numOfBalls;
    }

    @Override
    public Integer numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }

    /**
     * Sets number of blocks to remove.
     *
     * @param numOfBlocks the num of blocks
     */
    public void setNumberOfBlocksToRemove(int numOfBlocks) {
        this.numberOfBlocksToRemove = numOfBlocks;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Sets blocks.
     *
     * @param blocksList the blocks list
     */
    public void setBlocks(List<Block> blocksList) {
        this.blocks = blocksList;
    }

    /**
     * Add block.
     *
     * @param block the block
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }

    /**
     * Sets initial ball velocities.
     *
     * @param velocities the velocities
     */
    public void setInitialBallVelocities(List<Velocity> velocities) {
        this.initialBallVelocities = velocities;
    }

    /**
     * Add initial ball velocity.
     *
     * @param velocity the velocity
     */
    public void addInitialBallVelocity(Velocity velocity) {
        this.initialBallVelocities.add(velocity);
    }

    @Override
    public Integer paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Sets paddle speed.
     *
     * @param speed the speed
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    @Override
    public Integer paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Sets paddle width.
     *
     * @param width the width
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }

    /**
     * Paddle height integer.
     *
     * @return the integer
     */
    public Integer paddleHeight() {
        return this.paddleHeight;
    }

    /**
     * Sets paddle height.
     *
     * @param height the height
     */
    public void setPaddleHeight(int height) {
        this.paddleHeight = height;
    }

    /**
     * Blocks start x integer.
     *
     * @return the integer
     */
    public Integer blocksStartX() {
        return this.blocksStartX;
    }

    /**
     * Sets blocks start x.
     *
     * @param x the x
     */
    public void setBlocksStartX(int x) {
        this.blocksStartX = x;
    }

    /**
     * Blocks start y integer.
     *
     * @return the integer
     */
    public Integer blocksStartY() {
        return this.blocksStartY;
    }

    /**
     * Sets blocks start y.
     *
     * @param y the y
     */
    public void setBlocksStartY(int y) {
        this.blocksStartY = y;
    }

    /**
     * Row height integer.
     *
     * @return the integer
     */
    public Integer rowHeight() {
        return this.rowHeight;
    }

    /**
     * Sets row height.
     *
     * @param height the height
     */
    public void setRowHeight(int height) {
        this.rowHeight = height;
    }

    @Override
    public void restartLevesBlocks() {
        for (Block b : this.blocks) {
            b.restartLife();
        }
    }

}
