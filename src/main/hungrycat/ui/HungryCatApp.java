package hungrycat.ui;

import hungrycat.model.Direction;
import hungrycat.model.Game;
import hungrycat.model.GameState;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static hungrycat.model.Cell.CELL_PIXELS;
import static hungrycat.model.Game.BOARD_COLS;
import static hungrycat.model.Game.BOARD_ROWS;

/**
 * Represents the Hungry Cat game application.
 */
public class HungryCatApp extends JPanel {
    protected static final int WIDTH  = BOARD_COLS * CELL_PIXELS;
    protected static final int HEIGHT = BOARD_ROWS * CELL_PIXELS;
    private static final int INTERVAL = 200;
    private static final int INTERVAL_INC = 10;
    private static final int LEVEL_INC = 10;

    // Main BGM: "Level 0" by Monplaisir
    private static final String BGM_PATH           = "src/main/resources/music/lv0.wav";
    // Intense Mode BGM: "Level 3" by Monplaisir
    private static final String INTENSE_BGM_PATH   = "src/main/resources/music/lv3.wav";
    // Game Over BGM: "At Rest" by Kevin MacLeod
    private static final String GAME_OVER_BGM_PATH = "src/main/resources/music/over.wav";

    private static final String BANG_SFX_PATH      = "src/main/resources/sfx/bang.au";
    // "Cat, Screaming, A.wav" by InspectorJ of Freesound.org
    private static final String MEOW_SFX_PATH      = "src/main/resources/sfx/meow.au";
    // "Pop sound" by deraj of Freesound.org
    private static final String POP_SFX_PATH       = "src/main/resources/sfx/pop.au";

    private Game game;
    private TitleRenderer tRenderer;
    private MainRenderer renderer;
    private PauseRenderer pRenderer;
    private GameOverRenderer oRenderer;
    private Clip clip;

    // TODO: Implement "levels" and music corresponding to interval decrements
    private int factor;
    private boolean isStart;
    protected static boolean isIntense;

    /**
     * Creates and sets up the feeder game window.
     */
    private HungryCatApp() {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);

        setDoubleBuffered(true);
        setSize(WIDTH, HEIGHT);

        game = new Game();
        tRenderer = new TitleRenderer(game);
        renderer  = new MainRenderer(game);
        pRenderer = new PauseRenderer(game);
        oRenderer = new GameOverRenderer(game);

        frame.addKeyListener(new KeyHandler());
        centerOnScreen(frame);
        addTimer();

        setBorder(new LineBorder(Color.RED, 10));

        frame.add(this);
        frame.setVisible(true);

        factor = 1;
        isStart = true;
        isIntense = false;
    }

    /**
     * Clears the screen and paints the game onto the given graphics.
     */
    @Override
    public void paint(Graphics graphics) {
        switch (game.getState()) {
            case TITLE_STATE:
                tRenderer.draw(graphics);
                if (isStart) {
                    isStart = false;
                    music(BGM_PATH);
                }
                break;
            case MAIN_STATE:
                renderer.draw(graphics);
                break;
            case INTENSE_STATE:
                renderer.draw(graphics);
                break;
            case PAUSE_STATE:
                pRenderer.draw(graphics);
                break;
            case GAME_OVER_STATE:
                oRenderer.draw(graphics);
                break;
        }
    }

    /**
     * Initializes a timer that updates the game every INTERVAL milliseconds.
     */
    private void addTimer() {
        final Timer t = new Timer(INTERVAL, null);

        t.addActionListener(e -> {
            switch (game.getState()) {
                case TITLE_STATE:
                    break;
                case MAIN_STATE:

                    game.update();

                    if (game.isGameOver())
                        game.setState(GameState.GAME_OVER_STATE);
                    else if (!t.isRunning())
                        t.start();

                    if (game.getCatFullness() >= factor * LEVEL_INC) {
                        if (t.getDelay() <= 70) {
                            isIntense = true;
                            game.setState(GameState.INTENSE_STATE);
                            clip.stop();
                            music(INTENSE_BGM_PATH);
                        } else if (t.getDelay() > 40) {
                            int newDelay = INTERVAL - (factor * INTERVAL_INC);
                            t.setDelay(newDelay < 40 ? 40 : newDelay);
                            factor++;
                        }
                    }

                    break;
                case INTENSE_STATE:

                    game.update();

                    if (game.isGameOver())
                        game.setState(GameState.GAME_OVER_STATE);
                    else if (!t.isRunning())
                        t.start();

                    if (game.getCatFullness() >= factor * LEVEL_INC && t.getDelay() > 40) {
                        int newDelay = INTERVAL - (factor * INTERVAL_INC);
                        t.setDelay(newDelay < 40 ? 40 : newDelay);
                        factor++;
                    }

                    break;
                case PAUSE_STATE:
                    if (!t.isRunning())
                        t.stop();
                    break;
            }

            if (game.isGameOver()) {
                t.stop();
                clip.stop();
                sfx(BANG_SFX_PATH);
                sfx(MEOW_SFX_PATH);
                music(GAME_OVER_BGM_PATH);
                System.out.println("Final score: " + game.getCatFullness());
            }

            repaint();

        });

        t.start();

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
            switch(e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    switch(game.getState()) {
                        case TITLE_STATE:
                            game.setState(GameState.MAIN_STATE);
                            sfx(POP_SFX_PATH);
                            break;
                        case MAIN_STATE:
                            game.setState(GameState.PAUSE_STATE);
                            clip.stop();
                            break;
                        case INTENSE_STATE:
                            game.setState(GameState.PAUSE_STATE);
                            clip.stop();
                            break;
                        case PAUSE_STATE:
                            game.setState(isIntense ? GameState.INTENSE_STATE : GameState.MAIN_STATE);
                            clip.start();
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                            break;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (game.getCatDirection() != Direction.RIGHT && game.getState() != GameState.PAUSE_STATE)
                        game.rotateCat(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (game.getCatDirection() != Direction.LEFT && game.getState() != GameState.PAUSE_STATE)
                        game.rotateCat(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    if (game.getCatDirection() != Direction.DOWN && game.getState() != GameState.PAUSE_STATE)
                        game.rotateCat(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    if (game.getCatDirection() != Direction.UP && game.getState() != GameState.PAUSE_STATE)
                        game.rotateCat(Direction.DOWN);
                    break;
            }
        }
    }

    /**
     * Plays a short sfx sound.
     *
     * @param pathName  the path to the desired audio file.
     */
    private void sfx(String pathName) {
        InputStream in;
        try {
            in = new FileInputStream(pathName);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a given audio file.
     *
     * @param pathName  the path to the desired audio file.
     */
    private void music(String pathName) {
        File file = new File(pathName);
        AudioInputStream stream = null;

        try {
            stream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            assert clip != null;
            clip.open(stream);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    /**
     * Play the game.
     *
     * @param args  the arguments entered via terminal; unused.
     */
    public static void main(String[] args) throws IOException {

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

//        System.out.println("Enter your name: ");
//        String nm = br.readLine();
//        System.out.println("Welcome, " + nm + "!");

        new HungryCatApp();
    }
}