package hungrycat.ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import hungrycat.model.Cat;
import hungrycat.model.Direction;
import hungrycat.model.Game;
import hungrycat.model.GameState;
import hungrycat.ui.renderer.GameOverRenderer;
import hungrycat.ui.renderer.MainRenderer;
import hungrycat.ui.renderer.PauseRenderer;
import hungrycat.ui.renderer.TitleRenderer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import static hungrycat.model.Cell.CELL_PIXELS;
import static hungrycat.model.Game.BOARD_COLS;
import static hungrycat.model.Game.BOARD_ROWS;

/**
 * Represents the Hungry Cat game application.
 */
public class HungryCatApp extends JPanel {
    public static final int WIDTH = BOARD_COLS * CELL_PIXELS;
    public static final int HEIGHT = BOARD_ROWS * CELL_PIXELS;
    private static final int INTERVAL = 200;
    private static final int INTERVAL_INC = 5;

    private static final String BGM_PATH = "../../resources/music/lv0.wav";            // Main BGM:         "Level 0" by Monplaisir
    private static final String INTENSE_BGM_PATH = "../../resources/music/lv3.wav";    // Intense Mode BGM: "Level 3" by Monplaisir
    private static final String GAME_OVER_BGM_PATH = "../../resources/music/over.wav"; // Game Over BGM:    "At Rest" by Kevin MacLeod
    private static final String BANG_SFX_PATH = "../../resources/sfx/bang.au";
    private static final String MEOW_SFX_PATH = "../../resources/sfx/meow.au";         // "Cat, Screaming, A.wav" by InspectorJ of Freesound.org
    private static final String POP_SFX_PATH = "../../resources/sfx/pop.au";           // "Pop sound"             by deraj of Freesound.org

    private Game game;
    private TitleRenderer title;
    private MainRenderer main;
    private PauseRenderer pause;
    private GameOverRenderer gameOver;
    private Timer timer;
    private Clip clip;

    // TODO: Implement and music corresponding to interval decrements
    public static GameState unpauseState;

    /**
     * Creates and sets up the game window.
     */
    private HungryCatApp() {
        JFrame frame = new JFrame("Hungry Cat");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);

        setDoubleBuffered(true);
        setSize(WIDTH, HEIGHT);

        game = new Game();
        title = new TitleRenderer(game);
        main = new MainRenderer(game);
        pause = new PauseRenderer(game);
        gameOver = new GameOverRenderer(game);

        frame.addKeyListener(new KeyHandler());
        centerOnScreen(frame);
        timer = new Timer(INTERVAL, null);
        configureTimer();

        frame.add(this);
        frame.setVisible(true);

        unpauseState = GameState.MAIN_STATE;
    }

    /**
     * Clears the screen and paints the game onto the given graphics.
     */
    @Override
    public void paint(Graphics graphics) {
        switch (game.getState()) {
            case TITLE_STATE:
                title.draw(graphics);
                break;
            case MAIN_STATE:
                main.draw(graphics);
                break;
            case INTENSE_STATE:
                main.draw(graphics);
                break;
            case PAUSE_STATE:
                pause.draw(graphics);
                break;
            case GAME_OVER_STATE:
                gameOver.draw(graphics);
                break;
        }
    }

    /**
     * Updates the game every INTERVAL milliseconds.
     */
    private void configureTimer() {
        timer.addActionListener(e -> {
            switch (game.getState()) {
                case TITLE_STATE:
                    // music(this.getClass().getResource(BGM_PATH).getPath());
                    music(BGM_PATH);
                    break;
                case PAUSE_STATE:
                    if (!timer.isRunning())
                        timer.stop();
                    break;
                default:
                    game.update();
                    speedUp();
                    if (game.isGameOver()) {
                        handleGameOver();
                        break;
                    }
                    if (!timer.isRunning())
                        timer.start();
            }
            repaint();
        });
        timer.start();
    }

    /**
     * If GAME OVER, stop the timer, play the appropriate sounds, and display the GAME OVER screen.
     */
    private void handleGameOver() {
        game.setState(GameState.GAME_OVER_STATE);
        clip.stop();
//        sfx(this.getClass().getResource(BANG_SFX_PATH).getPath());
//        sfx(this.getClass().getResource(MEOW_SFX_PATH).getPath());
        sfx(BANG_SFX_PATH);
        sfx(MEOW_SFX_PATH);
//        music(this.getClass().getResource(GAME_OVER_BGM_PATH).getPath());
        music(GAME_OVER_BGM_PATH);
        System.out.println("Final score: " + game.getCat().getFullness());
        timer.stop();
    }

    /**
     * Increment the speed according to level.
     */
    private void speedUp() {
        int delay = timer.getDelay();
        if (delay > 40) {
            Cat cat = game.getCat();
            int newDelay = INTERVAL - (cat.getLevel() * INTERVAL_INC) + cat.getDeceleration();
            timer.setDelay(newDelay < 40 ? 40 : newDelay);
            // System.out.println(delay + " -> " + timer.getDelay());
        }
        switchIntense(delay);
    }

    /**
     * Switch the game state between MAIN_STATE and INTENSE_STATE depending on speed.
     */
    private void switchIntense(int delay) {
        if (delay > 70) {
            if (game.getState() == GameState.INTENSE_STATE) {
                game.setState(GameState.MAIN_STATE);
                clip.stop();
                music(BGM_PATH);
            }
        } else {
            if (game.getState() != GameState.INTENSE_STATE) {
                game.setState(GameState.INTENSE_STATE);
                clip.stop();
                music(INTENSE_BGM_PATH);
            }
        }
    }

    /**
     * Centers the frame on the desktop.
     */
    private void centerOnScreen(JFrame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    /**
     * Represents a key handler than responds to keyboard events.
     */
    private class KeyHandler extends KeyAdapter {
        /**
         * Updates the game in response to a keyboard event.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    pressSpaceBar();
                    break;
                case KeyEvent.VK_LEFT:
                    rotateCat(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    rotateCat(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    rotateCat(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    rotateCat(Direction.DOWN);
                    break;
            }
        }
    }

    /**
     * Pause the game if the current state is MAIN or INTENSE.
     * Resume the game if the current state is PAUSE.
     * Start the game if the current state is TITLE.
     * Do nothing if the current state is GAME OVER.
     */
    private void pressSpaceBar() {
        switch (game.getState()) {
            case PAUSE_STATE:
                game.setState(unpauseState);
                clip.start();
                break;
            case TITLE_STATE:
                game.setState(GameState.MAIN_STATE);
                // sfx(this.getClass().getResource(POP_SFX_PATH).getPath());
                sfx(POP_SFX_PATH);
                break;
            case GAME_OVER_STATE:
                break;
            default:
                unpauseState = game.getState();
                game.setState(GameState.PAUSE_STATE);
                clip.stop();
        }
    }

    /**
     * Rotates the cat the given direction, unless it is the opposite direction.
     */
    private void rotateCat(Direction direction) {
        if (game.getState() != GameState.PAUSE_STATE
                && direction != game.getCat().getDirection().getOppositeDirection())
            game.rotateCat(direction);
    }

    /**
     * Plays a short sfx sound.
     *
     * @param pathName the path to the desired audio file.
     */
    private void sfx(String pathName) {
        InputStream in;
        try {
            in = new FileInputStream(this.getClass().getResource(pathName).getPath());
            // in = new FileInputStream(pathName);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a given audio file.
     *
     * @param pathName the path to the desired audio file.
     */
    private void music(String pathName) {
        if (clip != null && clip.isActive()) {
            return;
        }

        File file = new File(this.getClass().getResource(pathName).getPath());
        // File file = new File(pathName);

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            assert clip != null;
            clip.open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            throw new Error(e.getMessage());
        }

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    /**
     * Play the game.
     *
     * @param args the arguments entered via terminal; unused.
     */
    public static void main(String[] args) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Enter your name: ");
//        String name = br.readLine();
//        System.out.println("Welcome, " + name + "!");

        new HungryCatApp();
    }
}