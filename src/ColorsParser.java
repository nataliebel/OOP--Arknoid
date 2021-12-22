
import java.awt.Color;

/**
 * this class is a color parser.
 */
public class ColorsParser {

    /**
     * Color from string color.
     *
     * @param s       the s
     * @param lineNum the line num
     * @return the color
     * @throws ErrorException the error exception
     */
    public static Color colorFromString(String s, int lineNum)
            throws ErrorException {
        if (s.startsWith("color(")) {
            s = s.substring("color(".length());
            if (s.startsWith("RGB(")) {
                s = s.substring("RGB(".length());
                s = s.replace("))", "");
                String[] parts = s.split(",");
                if (!(parts.length == 3)) {
                    throw new ErrorException(
                            "Error in reading file",
                            "At line " + lineNum + ": " + s,
                            "RGB definition must contain 3 parameters",
                            "space");
                }
                int r = Integer.valueOf(parts[0]);
                int g = Integer.valueOf(parts[1]);
                int b = Integer.valueOf(parts[2]);
                return new Color(r, g, b);
            } else {
                s = s.replace(")", "");
                if (s.equals("black")) {
                    return Color.BLACK;
                } else if (s.equals("blue")) {
                    return Color.BLUE;
                } else if (s.equals("cyan")) {
                    return Color.CYAN;
                } else if (s.equals("gray")) {
                    return Color.GRAY;
                } else if (s.equals("lightGray")) {
                    return Color.LIGHT_GRAY;
                } else if (s.equals("green")) {
                    return Color.GREEN;
                } else if (s.equals("orange")) {
                    return Color.ORANGE;
                } else if (s.equals("pink")) {
                    return Color.PINK;
                } else if (s.equals("red")) {
                    return Color.RED;
                } else if (s.equals("white")) {
                    return Color.WHITE;
                } else if (s.equals("yellow")) {
                    return Color.YELLOW;
                } else {
                    throw new ErrorException(
                            "Error in reading file",
                            "At line " + lineNum + ": " + s,
                            "The color is not defined",
                            "space");
                }
            }
        } else {
            throw new ErrorException(
                    "error in reading file",
                    "At line " + lineNum + ": " + s,
                    "invalid format",
                    "space");
        }
    }
}
