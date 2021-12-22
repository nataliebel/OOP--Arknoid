import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * The type High scores table.
 */
public class HighScoresTable implements Comparable, Serializable {

    private List<ScoreInfo> scores;
    private int counter = 0;
    private List<ScoreInfo> scoresList;
    private int sizeOf;

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.scores = new ArrayList<ScoreInfo>(size);
        this.sizeOf = size;
    }

    /**
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        if (this.counter < this.sizeOf) {
            this.scores.add(score);
            counter++;
        } else {
            if (this.scores.get(this.counter - 1).getScore() < score.getScore()) {
                this.scores.set(this.counter - 1, score);
            }
        }
        Collections.sort(scores);
        Collections.reverse(scores);
    }

    /**
     * Return table size.
     *
     * @return the int
     */
    public int size() {
        return counter;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        for (int i = 0; i < this.scores.size(); i++) {
            if (score >= this.scores.get(i).getScore()) {
                return i + 1;
            }
        }
        if (this.scores.size() < this.sizeOf) {
            return this.scores.size() + 1;
        } else {
            return -1;
        }
    }

    /**
     * Clears the table.
     */
    public void clear() {
        for (int i = 0; i < this.scores.size(); i++) {
            this.scores.set(i, null);
        }
        this.counter = 0;
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        if (filename.exists()) {
            if (loadFromFile(filename) != null) {
                this.scoresList = loadFromFile(filename).getHighScores();
            } else {
                throw new IOException("Failed reading object");
            }
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            // unsafe down casting, we better be sure that the stream really contains a HighScoresTable!
            table = (HighScoresTable) objectInputStream.readObject();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return null;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return null;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
        return table;

    }

    /**
     * Print table.
     */
    public void printTable() {
        int i = 1;
        for (ScoreInfo currScore : this.getHighScores()) {
            System.out.println("Rank(" + i + ") - name: " + currScore.getName() + "score: " + currScore.getScore());
            i++;
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}