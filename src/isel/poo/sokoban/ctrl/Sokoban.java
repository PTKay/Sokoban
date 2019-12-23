package isel.poo.sokoban.ctrl;

import isel.leic.pg.Console;
import isel.poo.console.Window;
import isel.poo.console.tile.TilePanel;
import isel.poo.sokoban.model.*;
import isel.poo.sokoban.view.*;

import static java.awt.event.KeyEvent.*;
import java.io.*;

/**
 * Main class in console mode for the "Sokoban" game.
 * Performs interaction with the user.<br/>
 * Implements the Controller in the MVC model,
 * making the connection between the model and the viewer specific to the console mode.
 */
public class Sokoban {
    public static void main(String[] args) {
        Console.fontSize(25);
        Sokoban ctrl = new Sokoban();
        ctrl.run();
    }

    private static final int WIN_HEIGHT = 24, WIN_WIDTH = 28;
    private Window win = new Window("Sokoban",WIN_HEIGHT, WIN_WIDTH + StatusPanel.WIDTH); // The console window
    private StatusPanel status = new StatusPanel(WIN_WIDTH); // View of Level, Apples and Score

    private static final String LEVELS_FILE = "levels.txt"; // Name of levels file
    private Game model;                                     // Model of game
    private Level level;                                    // Model of current level
    private TilePanel view;                                 // View of cell
    private boolean escaped = false;

    /**
     * Main game loop.
     * Returns when there are no more levels or the player gives up.
     */
    private void run() {
        try (InputStream file = new FileInputStream(LEVELS_FILE)) { // Open description file
            model = new Game(file);                                 // Create game model
            while ((level = model.loadNextLevel() ) != null )     // Load level model
                if (!playLevel() || !win.question("Next level")) {  // Play level
                    win.message("Bye.");
                    return;
                }
            win.message("No more Levels");
        } catch (Loader.LevelFormatException e) {
            win.message(e.getMessage());
            System.out.println(e+", Line "+e.getLineNumber()+": "+e.getLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally { win.close(); }                                    // Close console window
    }


    /**
     * Main loop of each level.
     * @return true - the level has been completed. false - the player has given up.
     */
    private boolean playLevel() {
        // Opens panel of tiles with dimensions appropriate to the current level.
        // Starts the viewer for each model cell.
        // Shows the initial state of all cell in the model.
        int height = level.getHeight(), width = level.getWidth();
        view = new TilePanel(height,width, CellTile.SIDE);               // Create view for cell
        win.clear();                                                    // Clear area of previous level
        view.center(WIN_HEIGHT,WIN_WIDTH);                              // Center view in area
        level.setObserver(updater);                                     // Set listener of level
        level.setGameListener(soundPlayer);                             // Set GameEventListener of level
        refreshView();

        soundPlayer.levelStarted(level.getNumber());                    // Starts the level bg music.

        do
            play();                                                      // Process keys and make a step
        while ( !escaped && !level.isFinished() );
        if (escaped || level.manIsDead()) return false;
        win.message("You win");
        return true;                   // Verify win conditions; false: finished without complete
    }

    private void refreshView() {
        status.setLevel(level.getNumber());                             // Update status View
        status.setBoxes(level.getRemainingBoxes());
        status.setMoves(level.getMoves());
        status.repaint();
        int height = level.getHeight(), width = level.getWidth();
        for (int l = 0; l < height; l++)                                // Create each tile for each cell
            for (int c = 0; c < width; c++)
                view.setTile(l,c, CellTile.tileOf( level.getCell(l,c) ));
    }

    /**
     * Listener of model (Game and Level) to update View
     */
    private class Updater implements Level.Observer {
        @Override
        public void cellUpdated(int l, int c, Cell cell) { view.getTile(l,c).repaint(); }
        @Override
        public void cellReplaced(int l, int c, Cell cell) { view.setTile(l,c, CellTile.tileOf(cell)); }
    }

    /**
     * Listener of model, with the intent of playing sound effects.
     */
    private class SoundPlayer implements Level.GameEventListener {
        @Override
        public void manMoved() {
            Console.playSound("step" + (int)(Math.random() * 5));
        }

        @Override
        public void manNotMoved() {
            Console.playSound("nomove" + (int)(Math.random() * 3));
            if ((int)(Math.random() * 50) == 5)
                Console.playSound("egg");
        }

        @Override
        public void levelStarted(int levelNumber) {
            Console.startMusic("#bg_music" + levelNumber);
        }

        @Override
        public void levelFinished() {
            Console.stopMusic();
            Console.playSound("level_complete");
        }

        @Override
        public void manDied() {
            Console.stopMusic();
            Console.playSound("dead");
        }

        @Override
        public void boxPlaced() {
            Console.playSound("got_box");
        }

        @Override
        public void boxMoved() {
            Console.playSound("box_moved");
        }

        @Override
        public void boxInHole() {
            Console.playSound("box_hole");
        }

        @Override
        public void boxRemoved() {
            Console.playSound("removed_box");


        }
    }

    private Updater updater = new Updater();
    private SoundPlayer soundPlayer = new SoundPlayer();

    /**
     * Process key pressed and makes one step.
     */
    private void play() {
        int key = getKeyPressed();          // Wait a step time and read a key
        if (key > 0) {
            Dir dir = null;
            switch (key) {
                case VK_UP: dir = Dir.UP; break;
                case VK_DOWN: dir = Dir.DOWN; break;
                case VK_LEFT: dir = Dir.LEFT; break;
                case VK_RIGHT: dir = Dir.RIGHT; break;
                case VK_S: model.restart(); refreshView(); break;
                case VK_ESCAPE: escaped=true; return;
            }
            if (dir!=null) {
                level.moveMan(dir);
                status.setMoves(level.getMoves());
                status.setBoxes(level.getRemainingBoxes());
            }
        }
    }

    private int lastKey = 0;
    /**
     * Reads a key and expects it to be released
     * @return the key code or Console.NOKEY (negative value)
     */
    private int getKeyPressed() {
        if (lastKey>0 && Console.isKeyPressed(lastKey))
            Console.waitKeyReleased(lastKey);
        lastKey = Console.waitKeyPressed(0);
        return lastKey;
    }
}
