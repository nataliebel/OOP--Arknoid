import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.Reader;
import java.io.LineNumberReader;

/**
 * The type Level sets reader.
 */
public class LevelSetsReader {

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     * @throws ErrorException the error exception
     */
    public static List<Selection> fromReader(Reader reader)
            throws ErrorException {
        List<Selection> returnVal = new ArrayList<Selection>();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        int lineNum = lineNumberReader.getLineNumber();
        List<LevelInformation> levelSet;
        try {
            String key = null;
            String message = null;
            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() % 2 != 0) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        key = parts[0];
                        message = parts[1];
                    } else {
                        throw new ErrorException(
                                "Error in reading file", "At line " + lineNum + ": " + line,
                                "Parts must be 2", "space");
                    }
                } else {
                   /* Reader levelsReader = new InputStreamReader(
                            ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(line));*/
                    Reader levelsReader = new InputStreamReader(new FileInputStream(new File(line)));
                    levelSet = LevelSpecificationReader.fromReader(levelsReader);
                    returnVal.add(new Selection(key, message, levelSet));
                }
            }
        } catch (IOException e) {
            throw new ErrorException("Error in reading file", "LevelSetsReader", "IO Exception", "space");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new ErrorException("Error in closing file", "LevelSetsReader", "IO Exception", "space");
                }
            }
        }
        return returnVal;
    }
}
