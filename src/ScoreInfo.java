import java.io.Serializable;

/**
 * The type Score info.
 */
public class ScoreInfo implements Comparable, Serializable {

    private String name;
    private int score;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name
     * @param score the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;

    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ScoreInfo) {
            ScoreInfo otherScore = (ScoreInfo) o;
            return this.score - otherScore.score;
        }
        return 0;
    }
}