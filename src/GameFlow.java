
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.util.List;

import biuoop.DialogManager;

/**
 * The type Game flow.
 */
public class GameFlow {

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private GUI gui;
    private HighScoresTable hScoresTable;
    private File file;
    private DialogManager dialogManager;
    private Animation hScoresAnimation;

    /**
     * Instantiates a new Game flow.
     *
     * @param ar            the ar
     * @param ks            the ks
     * @param gui           the gui
     * @param dialogManager the dialog manager
     * @param file          the file
     * @param hScoresTable  the h scores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, DialogManager dialogManager,
                    File file, HighScoresTable hScoresTable) {
        this.hScoresTable = hScoresTable;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.hScoresTable = hScoresTable;
        this.file = file;
        this.dialogManager = this.gui.getDialogManager();
        try {
            this.hScoresTable.load(this.file);
        } catch (Exception e) {
            System.err.println("Failed persisting the high-scores table");
            e.printStackTrace(System.err);
        }
        this.dialogManager = gui.getDialogManager();
        Animation scores = new HighScoresAnimation(this.hScoresTable, KeyboardSensor.SPACE_KEY,
                this.keyboardSensor);
        Animation scoresAnimation = new KeyPressStoppableAnimation(this.keyboardSensor,
                KeyboardSensor.SPACE_KEY, scores);
        this.hScoresAnimation = scoresAnimation;
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        Counter live = new Counter(7);
        Counter score = new Counter(0);
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, live, score);
            level.initialize();

            while (level.getLivesNum() > 0 && level.getRemainingBlocksNum() > 0) {
                level.playOneTurn();
            }

            if (level.getLivesNum() <= 0) {
                animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                        new EndScreen(keyboardSensor, "Game Over", score.getValue())));
                if (this.hScoresTable.getRank(score.getValue()) != -1) {
                    String name = this.dialogManager.showQuestionDialog("Name", "What is your name?", "");
                    ScoreInfo playerInfo = new ScoreInfo(name, score.getValue());
                    this.hScoresTable.add(playerInfo);
                    try {
                        this.hScoresTable.save(this.file);
                    } catch (Exception e) {
                        System.err.println("Failed persisting the high-scores table");
                        e.printStackTrace(System.err);
                    }
                }
                HighScoresAnimation scoresAnimation = new HighScoresAnimation(this.hScoresTable,
                        KeyboardSensor.SPACE_KEY,
                        this.keyboardSensor);
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                        scoresAnimation));
                return;
            }


        }
        animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                new EndScreen(keyboardSensor, "You Win!", score.getValue())));
        if (this.hScoresTable.getRank(score.getValue()) != -1) {
            String name = this.dialogManager.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo playerInfo = new ScoreInfo(name, score.getValue());
            this.hScoresTable.add(playerInfo);
            try {
                this.hScoresTable.save(this.file);
            } catch (Exception e) {
                System.err.println("Failed persisting the high-scores table");
                e.printStackTrace(System.err);
            }
        }
        HighScoresAnimation scoresAnimation = new HighScoresAnimation(this.hScoresTable, "SPACE",
                this.keyboardSensor);
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                scoresAnimation));
    }
}

