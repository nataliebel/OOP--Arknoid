import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.List;

/**
 * The main to run the game.
 */
public class Ass6Game {

    /**
     * The entry point of application.
     *
     * @param args arguments.
     */
    public static void main(String[] args) {

        //creating the gui
        GUI gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor ks = gui.getKeyboardSensor();
        AnimationRunner ar = new AnimationRunner(gui, 60);


        File file = new File("highscores");
        HighScoresTable hScoresTable = null;

        try {
            if (file.exists()) {
                hScoresTable = HighScoresTable.loadFromFile(file);
            } else {
                hScoresTable = new HighScoresTable(5);
                hScoresTable.save(file);
            }
        } catch (IOException e) {
            System.out.println("failed loading highscores table");
        }

        LevelSetsReader levelSetsReader = new LevelSetsReader();

        MenuAnimation<Task> menuAnimation = new MenuAnimation<>("Arkanoid", ks,
                ar);

        GameFlow gameFlow = new GameFlow(ar, gui.getKeyboardSensor(), gui, gui.getDialogManager(),
                file, hScoresTable);

        InputStreamReader is = null;
        List<Selection> sets = null;
        try {
            if (args.length != 0) {
                File f = new File(args[0]);
                InputStream input = new FileInputStream(f);
                is = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]));
                sets = levelSetsReader.fromReader(is);
            } else {
                is = new InputStreamReader(
                        ClassLoader.getSystemClassLoader().
                                getResourceAsStream("level_sets.txt"));
                sets = levelSetsReader.fromReader(is);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Problem reading the file");
            System.exit(1);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                System.out.println("problem reading file");
            }
        }

        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>(
                "Arkanoid", ks, ar);

        Task gameTask;
        for (Selection selection : sets) {
            gameTask = gameTask(gameFlow, selection);
            subMenu.addSelection(selection.getKey(),
                    selection.getMessage(), gameTask);

        }

        Task runSubMenu = runSubMenu(ar, subMenu);

        menuAnimation.addSelection("s", "startGame", runSubMenu);
        Task<Void> quitTask = quitTask();


        menuAnimation.addSelection("q", "Quit", quitTask);

        HighScoresAnimation high = new HighScoresAnimation(hScoresTable, "SPACE", ks);

        KeyPressStoppableAnimation key = new KeyPressStoppableAnimation(ks, KeyboardSensor.SPACE_KEY, high);

        Task<Void> hiTask = showHighscoresTask(ar, key);

        menuAnimation.addSelection("h", "hiscore", hiTask);


        while (true) {
            ar.run(menuAnimation);
            Task<Void> task = menuAnimation.getStatus();
            task.run();
            menuAnimation.restartMenu();
        }
    }

    /**
     * @param runner Animation Runner.
     * @param key    Key Press StoppableA nimation.
     * @return null
     */
    private static Task<Void> showHighscoresTask(AnimationRunner runner, KeyPressStoppableAnimation key) {
        return new Task<Void>() {
            @Override
            public Void run() {
                key.restartKey();
                runner.run(key);
                return null;
            }
        };
    }

    /**
     * @return null
     */
    private static Task<Void> quitTask() {
        return new Task<Void>() {

            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };
    }

    /**
     * @param runner  Animation Runner.
     * @param subMenu Menu
     * @return null
     */
    private static Task runSubMenu(AnimationRunner runner, Menu<Task<Void>> subMenu) {
        return new Task() {
            @Override
            public Void run() {
                runner.run(subMenu);
                Task<Void> task = subMenu.getStatus();
                task.run();
                return null;
            }
        };
    }

    /**
     * @param gameFlow  game flow
     * @param selection selection
     * @return null
     */
    private static Task gameTask(GameFlow gameFlow, Selection selection) {
        return new Task<Void>() {
            @Override
            public Void run() {
                List<LevelInformation> levels;
                levels = (List<LevelInformation>) selection.getReturnVal();
                gameFlow.runLevels(levels);
                for (LevelInformation l : levels) {
                    l.restartLevesBlocks();
                }
                return null;
            }
        };
    }
}