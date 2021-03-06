package ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import model.Cat;
import model.Direction;
import model.Game;
import model.GameState;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import ui.renderer.GameOverRenderer;
import ui.renderer.MainRenderer;
import ui.renderer.PauseRenderer;
import ui.renderer.TitleRenderer;

import static model.Cell.CELL_PIXELS;
import static model.Game.BOARD_COLS;
import static model.Game.BOARD_ROWS;

/**
 * Represents the Hungry Cat game application.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HungryCatApp extends JPanel {
    public static final int WIDTH = BOARD_COLS * CELL_PIXELS;
    public static final int HEIGHT = BOARD_ROWS * CELL_PIXELS;

    private static final int INTERVAL_1 = 200;
    private static final int INTERVAL_2 = 150;
    private static final int INTERVAL_3 = 100;
    private static final int INTERVAL_INC = 5;

    private static final String BGM_PATH = "../music/lv0.wav";            // Main BGM:         "Level 0" by Monplaisir
    private static final String INTENSE_BGM_PATH = "../music/lv3.wav";    // Intense Mode BGM: "Level 3" by Monplaisir
    private static final String GAME_OVER_BGM_PATH = "../music/over.wav"; // Game Over BGM:    "At Rest" by Kevin MacLeod
    private static final String BANG_SFX_PATH = "../sfx/bang.au";
    private static final String MEOW_SFX_PATH = "../sfx/meow.au";         // "Cat, Screaming, A.wav" by InspectorJ of Freesound.org
    private static final String POP_SFX_PATH = "../sfx/pop.au";           // "Pop sound"             by deraj of Freesound.org

    private static final Map<String, Integer> INTERVALS = ImmutableMap.of(
            "1", INTERVAL_1,
            "2", INTERVAL_2,
            "3", INTERVAL_3
    );

    Game game;
    TitleRenderer title;
    MainRenderer main;
    PauseRenderer pause;
    GameOverRenderer gameOver;
    Timer timer;
    Clip clip;
    int initialInterval;

    public static GameState unpauseState;

    /**
     * Creates and sets up the game window.
     */
    private HungryCatApp(int interval) {
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
        timer = new Timer(interval, null);
        initialInterval = interval;
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
                    music(BGM_PATH);
                    break;
                case PAUSE_STATE:
                    if (!timer.isRunning())
                        timer.stop();
                    break;
                case MAIN_STATE:
                case INTENSE_STATE:
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
        sfx(BANG_SFX_PATH);
        sfx(MEOW_SFX_PATH);
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
            int newDelay = initialInterval - (cat.getLevel() * INTERVAL_INC) + cat.getDeceleration();
            timer.setDelay(newDelay < 40 ? 40 : newDelay);
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
                loopStartMusic();
                break;
            case TITLE_STATE:
                game.setState(GameState.MAIN_STATE);
                sfx(POP_SFX_PATH);
                break;
            case GAME_OVER_STATE:
                game.restart();
                timer.restart();
                clip.stop();
                music(BGM_PATH);
                loopStartMusic();
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
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loopStartMusic() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
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

        loopStartMusic();
    }

    /**
     * Play the game.
     *
     * @param args the arguments entered via terminal; unused.
     */
    public static void main(String[] args) {
        System.out.print("\n" +
                "*--------------------------*\n" +
                "*         Welcome!         *\n" +
                "*--------------------------*\n" +
                "1 - My eyes are closed [default]\n" +
                "2 - I am a regular human being\n" +
                "3 - Mom, pick me up, I'm scared\n\n" +
                "Please enter a difficulty mode: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int interval = INTERVAL_1;
        try {
            String difficulty = br.readLine().trim();
            interval = INTERVALS.get(difficulty);
        } catch (IOException e) {
            // skip and go straight to game
        } finally {
            new HungryCatApp(interval);
        }
    }
}