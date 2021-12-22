import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private static BlocksFromSymbolsFactory blocksFromSymbolsFactory;

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     * @throws ErrorException the error exception
     */
    public static List<LevelInformation> fromReader(Reader reader)
            throws ErrorException {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        Level level = null;
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        int lineNum;
        int rowCounter = 0;
        boolean isInBlockSection = false;
        blocksFromSymbolsFactory = null;
        try {
            while ((line = lineNumberReader.readLine()) != null) {
                line = line.trim();
                lineNum = lineNumberReader.getLineNumber();
                if (!line.equals("") && !line.startsWith("#")) {
                    if (!isInBlockSection) {
                        if (line.equals("START_LEVEL")) {
                            level = new Level();
                        } else if (line.equals("END_LEVEL")) {
                            if (level.levelName() == null
                                    || level.numberOfBalls() == null || level.getBackground() == null
                                    || level.numberOfBlocksToRemove() == null || level.paddleSpeed() == null
                                    || level.paddleWidth() == null || level.paddleHeight() == null
                                    || level.blocksStartX() == null || level.blocksStartY() == null
                                    || level.rowHeight() == null) {
                                throw new ErrorException(
                                        "Error in reading file", "", "At least one of the level's"
                                        + " ingredients is missing", "space");
                            }
                            levels.add(level);
                            level = null;
                        } else if (line.equals("START_BLOCKS")) {
                            isInBlockSection = true;
                            rowCounter = 0;
                        } else {
                            parseRegularLine(line, lineNum, level);
                            level.setPaddleHeight(15);
                        }
                    } else if (line.equals("END_BLOCKS")) {
                        isInBlockSection = false;
                        blocksFromSymbolsFactory = null;
                    } else {
                        parseBlockSectionLine(line, lineNum, rowCounter, level);
                        rowCounter++;
                    }
                }
            }
        } catch (IOException e) {
            throw new ErrorException(
                    "Error in reading file", "LevelSpecificationReader", "IO Exception", "space");
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (IOException e) {
                    throw new ErrorException(
                            "Error in closing file", "LevelSpecificationReader", "IO Exception", "space");
                }
            }
        }
        return levels;
    }

    /**
     * Parse regular line.
     *
     * @param line    the line
     * @param lineNum the line num
     * @param level   the level
     * @throws ErrorException the error exception
     */
    public static void parseRegularLine(String line, int lineNum, Level level)
            throws ErrorException {
        String[] parts = line.trim().split(":");
        if (!(parts.length == 2)) {
            throw new ErrorException(
                    "Error in reading file", "At line " + lineNum + ": " + line,
                    "Parts must be 2", "space");
        }
        String key = parts[0];
        String value = parts[1];
        if (key.equals("level_name")) {
            level.setLevelName(value);
        }
        if (key.equals("ball_velocities")) {
            int numberOfBalls = 0;
            String[] velocities = value.split(" ");
            for (String velocity : velocities) {
                String[] velocityComponents = velocity.split(",");
                if (!(velocityComponents.length == 2)) {
                    throw new ErrorException(
                            "Error in reading file", "At line " + lineNum + ": " + line,
                            "Velocity must have 2 components", "space");
                }
                String angle = velocityComponents[0];
                String speed = velocityComponents[1];
                try {
                    if (Double.valueOf(speed) <= 0) {
                        throw new NumberFormatException();
                    }
                    level.addInitialBallVelocity(
                            Velocity.fromAngleAndSpeed(Double.valueOf(angle),
                                    Double.valueOf(speed)));
                    numberOfBalls++;
                } catch (NumberFormatException e) {
                    throw new ErrorException(
                            "Error in reading file", "At line " + lineNum + ": " + line,
                            "Illegal velocity", "space");
                }
            }
            level.setNumberOfBalls(numberOfBalls);
        }
        if (key.equals("background")) {
            if (value.startsWith("image(")) {
                value = value.substring("image(".length());
                value = value.replace(")", "");
                try {
                    Image image = ImageIO.read(
                            ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(value));
                    Background background = new Background(image);
                    level.setBackground(background);
                } catch (IOException e) {
                    throw new ErrorException(
                            "Error in reading file", "At line " + lineNum + ": " + line,
                            "Unable to find background image", "space");
                }
            } else if (value.startsWith("color(")) {
                Color color = ColorsParser.colorFromString(value, lineNum);
                Background background = new Background(color);
                level.setBackground(background);
            } else {
                throw new ErrorException(
                        "Error in reading file", "At line " + lineNum + ": " + line,
                        "No color or image definition", "space");
            }
        }
        if (key.equals("paddle_speed")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new NumberFormatException();
                }
                level.setPaddleSpeed(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                        "Illegal paddle speed", "space");
            }
        }
        if (key.equals("paddle_width")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new NumberFormatException();
                }
                level.setPaddleWidth(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                        "Illegal paddle width", "space");
            }
        }
        if (key.equals("block_definitions")) {
            Reader blockReader = new InputStreamReader(
                    ClassLoader.getSystemClassLoader().
                            getResourceAsStream(value));
            blocksFromSymbolsFactory =
                    BlocksDefinitionReader.fromReader(blockReader);
        }
        if (key.equals("blocks_start_x")) {
            try {
                if (Integer.valueOf(value) < 0) {
                    throw new NumberFormatException();
                }
                level.setBlocksStartX(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                        "Illegal block start x", "space");
            }
        }
        if (key.equals("blocks_start_y")) {
            try {
                if (Integer.valueOf(value) < 0) {
                    throw new NumberFormatException();
                }
                level.setBlocksStartY(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                        "Illegal block start y", "space");
            }
        }
        if (key.equals("row_height")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new NumberFormatException();
                }
                level.setRowHeight(Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                        "Illegal row height", "space");
            }
        }
        if (key.equals("num_blocks")) {
            try {
                if (Integer.valueOf(value) <= 0) {
                    throw new NumberFormatException();
                }
                level.setNumberOfBlocksToRemove(
                        Integer.valueOf(value));
            } catch (NumberFormatException e) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                        "Illegal number of blocks", "space");
            }
        }
    }

    /**
     * this method gets a block section line from
     * the file and a levelInformation object.
     * the method parses the line and updates the
     * fields in the given levelInformation object.
     *
     * @param level      the given levelInformation object.
     * @param line       the given line.
     * @param lineNum    the line number.
     * @param rowCounter the line number in the block section.
     * @throws ErrorException if an error occurs.
     */
    private static void parseBlockSectionLine(String line, int lineNum,
                                              int rowCounter, Level level) throws ErrorException {
        if (level.levelName() == null
                || level.numberOfBalls() == null
                || level.getBackground() == null
                || level.numberOfBlocksToRemove() == null
                || level.paddleSpeed() == null
                || level.paddleWidth() == null
                || level.paddleHeight() == null
                || level.blocksStartX() == null
                || level.blocksStartY() == null
                || level.rowHeight() == null) {
            throw new ErrorException(
                    "Error in reading file", "LevelSpecificationReader.parseBlockSection",
                    "At least one of the level's ingredients is missing",
                    "space");
        }
        int currentX = level.blocksStartX();
        int currentY = level.blocksStartY() + rowCounter * level.rowHeight();
        for (int i = 0; i < line.length(); i++) {
            String symbol = String.valueOf(line.charAt(i));
            if (blocksFromSymbolsFactory.isSpaceSymbol(symbol)) {
                currentX += blocksFromSymbolsFactory.getSpacerWidth(symbol);
            } else if (blocksFromSymbolsFactory.isBlockSymbol(symbol)) {
                Block block = blocksFromSymbolsFactory.
                        getBlock(symbol, currentX, currentY);
                currentX = (int) (currentX + block.
                        getCollisionRectangle().getWidth());
                level.addBlock(block);
            } else {
                throw new ErrorException(
                        "Error in reading file", "At line " + lineNum + ": " + line, "Undefined symbol: " + symbol,
                        "space");
            }
        }
    }
}
