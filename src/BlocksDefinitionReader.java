
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;


/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     * @throws ErrorException the error exception
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader)
            throws ErrorException {
        BlocksFromSymbolsFactory blockFactory = new BlocksFromSymbolsFactory();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        int lineNum = lineNumberReader.getLineNumber();
        try {
            Map<String, String> blockMap = new TreeMap<String, String>();
            Map<String, String> defultMap = new TreeMap<String, String>();
            Map<String, String> tmpMap = new TreeMap<String, String>();
            while ((line = lineNumberReader.readLine()) != null) {
                line = line.trim();
                if (!line.equals("") && !line.startsWith("#")) {
                    if (line.startsWith("default")) {
                        line = line.substring("default".length()).trim();
                        defultMap = parseLine(line, lineNum);
                    } else if (line.startsWith("bdef")) {
                        line = line.substring("bdef".length()).trim();
                        tmpMap.clear();
                        tmpMap = parseLine(line, lineNum);
                        blockMap.clear();
                        blockMap.putAll(defultMap);
                        blockMap.putAll(tmpMap);
                        if (blockMap.containsKey("symbol") && blockMap.containsKey("width")
                                && blockMap.containsKey("height") && blockMap.containsKey("hit_points")) {
                            String symbol = blockMap.get("symbol");
                            Block block = new Block();
                            try {
                                if (Integer.valueOf(blockMap.get("width")) <= 0) {
                                    throw new NumberFormatException();
                                }
                                if (Integer.valueOf(blockMap.get("height")) <= 0) {
                                    throw new NumberFormatException();
                                }
                                if (Integer.valueOf(blockMap.get("hit_points")) <= 0) {
                                    throw new NumberFormatException();
                                }
                                block.setWidth(
                                        Integer.valueOf(blockMap.get("width")));
                                block.setHeight(
                                        Integer.valueOf(blockMap.get("height")));
                                block.setNumber(
                                        Integer.valueOf(blockMap.get("hit_points")));
                            } catch (NumberFormatException e) {
                                throw new ErrorException(
                                        "Error in reading file", "At line " + lineNum + ": " + line,
                                        "Illegal block property", "space");
                            }
                            if (blockMap.containsKey("stroke")) {
                                block.setBorderColor(ColorsParser.
                                        colorFromString(blockMap.get("stroke"),
                                                lineNum));
                                block.setBorder(true);
                            } else {
                                block.setBorder(false);
                            }
                            Map<Integer, Color> colors =
                                    new TreeMap<Integer, Color>();
                            Map<Integer, Image> images =
                                    new TreeMap<Integer, Image>();
                            if (blockMap.containsKey("fill")) {
                                blockMap.put("fill-0", blockMap.get("fill"));
                                blockMap.remove("fill");
                            }
                            for (int i = 0; i <= Integer.valueOf(
                                    blockMap.get("hit_points")); i++) {
                                if (blockMap.containsKey("fill-" + i)) {
                                    String fillString = blockMap.get("fill-" + i);
                                    if (fillString.startsWith("image(")) {
                                        fillString = fillString.
                                                substring("image(".length());
                                        fillString = fillString.replace(")", "");
                                        Image image = ImageIO.read(ClassLoader.getSystemClassLoader().
                                                getResourceAsStream(fillString));
                                        images.put(i, image);
                                    } else if (fillString.startsWith("color(")) {
                                        Color color = ColorsParser.
                                                colorFromString(fillString, lineNum);
                                        colors.put(i, color);
                                    } else {
                                        throw new ErrorException(
                                                "Error in reading file", "At line " + lineNum + ": " + line,
                                                "Invallid \"fill\" line: " + fillString, "space");
                                    }
                                }
                            }
                            block.setColors(colors);
                            block.setImages(images);
                            blockFactory.putBlock(symbol, block);
                        } else {
                            throw new ErrorException(
                                    "Error in reading file", "At line " + lineNum + ": " + line,
                                    "Block must include: symbol, width, " + "height and hit points", "space");
                        }
                    } else if (line.startsWith("sdef")) {
                        line = line.substring("sdef".length()).trim();
                        Map<String, String> spacerMap = parseLine(line, lineNum);
                        if (spacerMap.containsKey("symbol")
                                && spacerMap.containsKey("width")) {
                            String symbol = spacerMap.get("symbol");
                            Integer width = Integer.valueOf(spacerMap.get("width"));
                            blockFactory.putSpaceWidth(symbol, width);
                        } else {
                            throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                                    "Spacer definition must include: " + "symbol and width", "space");
                        }
                    } else {
                        throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line,
                                "Invallid line", "space");
                    }
                }
            }
        } catch (IOException e) {
            throw new ErrorException("Error in reading file", "BlocksDefinitionReader", "IO Exception", "space");
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (IOException e) {
                    throw new ErrorException("Error in closing file", "BlocksDefinitionReader", "IO Exception",
                            "space");
                }
            }
        }
        return blockFactory;
    }

    /**
     * this method gets a string and a number and returns.
     *
     * @param line    the line.
     * @param lineNum the line number.
     * @return reternVal.
     * @throws ErrorException if the line is not valid.
     */
    private static Map<String, String> parseLine(String line, int lineNum)
            throws ErrorException {
        Map<String, String> returnVal = new TreeMap<String, String>();

        String[] pairs = line.split(" ");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            if (parts.length != 2) {
                throw new ErrorException("Error in reading file", "At line " + lineNum + ": " + line, "Parts must be 2",
                        "space");
            }
            returnVal.put(parts[0], parts[1]);
        }
        return returnVal;
    }

}
