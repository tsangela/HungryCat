package hungrycat.ui;

import hungrycat.model.Cell;
import hungrycat.model.Game;
import hungrycat.model.FoodType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static hungrycat.ui.HungryCatApp.HEIGHT;
import static hungrycat.ui.HungryCatApp.WIDTH;
import static hungrycat.ui.HungryCatApp.isIntense;

/**
 * Represents a feeder game renderer.
 */
public class MainRenderer extends AbstractRenderer{
    // TODO: upgrade background!
    private static final Color DEFAULT_COLOR   = new Color(201, 255, 223);
    private static final Color INTENSE_COLOUR  = new Color(211, 0, 250);
    private static final Color GAME_OVER_COLOR = new Color(56, 56, 56);

    // "[LPC] Cats and Dogs" by bluecarrot16 of OpenGameArt.org
    private static final String CAT_U_PATH = "src/main/resources/cat/catU.png";
    private static final String CAT_D_PATH = "src/main/resources/cat/catD.png";
    private static final String CAT_L_PATH = "src/main/resources/cat/catL.png";
    private static final String CAT_R_PATH = "src/main/resources/cat/catR.png";

    // "Food Icons" by BizmasterStudios of OpenGameArt.org
    private static final String SS_PATH = "src/main/resources/food/SS.png";
    private static final String  S_PATH = "src/main/resources/food/S.png";
    private static final String  A_PATH = "src/main/resources/food/A.png";
    private static final String  B_PATH = "src/main/resources/food/B.png";
    private static final String  C_PATH = "src/main/resources/food/C.png";

    /**
     * Creates a feeder game renderer set to the given game.
     *
     * @param game  the game to render.
     */
    MainRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the feeder game onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {

        Color color = new Color(255, 255, 255);

        switch (game.getState()) {
            case MAIN_STATE:
                color = DEFAULT_COLOR;
                break;
            case INTENSE_STATE:
                color = INTENSE_COLOUR;
                break;
            case GAME_OVER_STATE:
                color = GAME_OVER_COLOR;
                break;
            case PAUSE_STATE:
                color = (isIntense ? INTENSE_COLOUR : DEFAULT_COLOR);
                break;
        }
//        graphics.setColor(game.isGameOver() ? GAME_OVER_COLOR : game.getState() == GameState.INTENSE_STATE ? INTENSE_COLOUR : DEFAULT_COLOR);
        graphics.setColor(color);
        graphics.fillRect(0,0, WIDTH, HEIGHT);

        drawFullness(graphics);

        try {
            drawFood(graphics);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            drawFeeder(graphics);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: draw level! (bottom left corner? right corner?)
    }

    /**
     * Draws the current fullness of the feeder onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawFullness(Graphics graphics) {
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        Color color = new Color(0,0,0);
        int fullness = game.getCatFullness();

        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString("Fullness: " + fullness, 10, 20);
    }

    /**
     * Draws a food item onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawFood(Graphics graphics) throws IOException {
        String pathName = "";
        FoodType type = game.getFoodType();

        switch (type) {
            case C:
                pathName = C_PATH;
                break;
            case B:
                pathName = B_PATH;
                break;
            case A:
                pathName = A_PATH;
                break;
            case S:
                pathName = S_PATH;
                break;
            case SS:
                pathName = SS_PATH;
                break;
        }

        File pathToFile = new File(pathName);
        final BufferedImage image = ImageIO.read(pathToFile);
        final double FILL_PERCENT = 0.70;
        final int SIZE = (int) (Cell.CELL_PIXELS * FILL_PERCENT);

        Cell foodPosition = game.getFoodPosition();
        graphics.drawImage(image, foodPosition.getTopLeftX(), foodPosition.getTopLeftY(), SIZE, SIZE,null);
    }

    /**
     * Draws the feeder onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawFeeder(Graphics graphics) throws IOException {
        String pathName = "";
        switch (game.getCatDirection()) {
            case UP:
                pathName = CAT_U_PATH;
                break;
            case DOWN:
                pathName = CAT_D_PATH;
                break;
            case LEFT:
                pathName = CAT_L_PATH;
                break;
            case RIGHT:
                pathName = CAT_R_PATH;
                break;
        }
        File file = new File(pathName);
        final BufferedImage image = ImageIO.read(file);
        final double FILL_PERCENT = 0.90;
        final int SIZE = (int) (Cell.CELL_PIXELS * FILL_PERCENT);

        Cell feederPosition = game.getCatPosition();
        graphics.drawImage(image, feederPosition.getTopLeftX(), feederPosition.getTopLeftY(), SIZE, SIZE, null);
    }

}
