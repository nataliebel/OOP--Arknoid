
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Block> blocks;
    private Map<String, Integer> spacerWidths;

    /**
     * Instantiates a new Blocks from symbols factory.
     */
    public BlocksFromSymbolsFactory() {
        this.blocks = new TreeMap<String, Block>();
        this.spacerWidths = new TreeMap<String, Integer>();
    }


    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isBlockSymbol(String s) {
        return blocks.containsKey(s);
    }


    /**
     * Gets spacer width.
     *
     * @param s the s
     * @return the spacer width
     */
    public int getSpacerWidth(String s) {
        return this.spacerWidths.get(s);
    }


    /**
     * Gets block.
     *
     * @param s the s
     * @param x the x
     * @param y the y
     * @return the block
     */
    public Block getBlock(String s, int x, int y) {
        Block block = new Block(this.blocks.get(s));
        block.setUpperLeft(x, y);
        return block;
    }

    /**
     * Put block.
     *
     * @param string the string
     * @param block  the block
     */
    public void putBlock(String string, Block block) {
        this.blocks.put(string, block);
    }


    /**
     * Put space width.
     *
     * @param string the string
     * @param num    the num
     */
    public void putSpaceWidth(String string, Integer num) {
        this.spacerWidths.put(string, num);
    }
}
