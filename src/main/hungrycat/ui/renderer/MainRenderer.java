package ui.renderer;

import java.awt.*;
import java.io.IOException;

import model.Cat;
import model.Cell;
import model.Direction;
import model.Food;
import model.FoodType;
import model.Game;
import model.GameState;

import static ui.HungryCatApp.HEIGHT;
import static ui.HungryCatApp.WIDTH;
import static ui.HungryCatApp.unpauseState;

/**
 * Represents a Hungry Cat game renderer.
 */
public class MainRenderer extends AbstractRenderer {
    // TODO: upgrade background!
    private static final Color DEFAULT_COLOR = new Color(201, 255, 223);
    private static final Color INTENSE_COLOUR = new Color(211, 0, 250);
    private static final Color GAME_OVER_COLOR = new Color(56, 56, 56);

    // "[LPC] Cats and Dogs" by bluecarrot16 of OpenGameArt.org
    private static final String CAT_U_PATH = "src/main/resources/cat/catU.png";
    private static final String CAT_D_PATH = "src/main/resources/cat/catD.png";
    private static final String CAT_L_PATH = "src/main/resources/cat/catL.png";
    private static final String CAT_R_PATH = "src/main/resources/cat/catR.png";

    // "Food Icons" by BizmasterStudios of OpenGameArt.org
    private static final String SS_PATH = "src/main/resources/food/SS.png";
    private static final String S_PATH = "src/main/resources/food/S.png";
    private static final String A_PATH = "src/main/resources/food/A.png";
    private static final String B_PATH = "src/main/resources/food/B.png";
    private static final String C_PATH = "src/main/resources/food/C.png";
    private static final String BOMB_PATH = "src/main/resources/food/BOMB.png";
    private static final String SLOW_PATH = "src/main/resources/food/SLOW.png";

    /**
     * Creates a feeder game renderer set to the given game.
     *
     * @param game the game to render.
     */
    public MainRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the game onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(getBackgroundColor(game.getState()));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        drawFullness(graphics);
        drawLevel(graphics);

        try {
            drawFood(graphics);
            drawFeeder(graphics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the current fullness of the cat onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawFullness(Graphics graphics) {
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        Color color = new Color(0, 0, 0);
        int fullness = game.getCat().getFullness();

        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString("Fullness: " + fullness, 10, 20);
    }

    /**
     * Draws the level on the top right of the screen.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawLevel(Graphics graphics) {
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        Color color = new Color(0, 0, 0);

        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString("Level: " + game.getCat().getLevel(), 10, 35);
    }


    /**
     * Draws a food item onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawFood(Graphics graphics) throws IOException {
        Food food = game.getFood();
        Cell foodPosition = food.getPosition();
        drawImage(graphics, getFoodFilePath(food.getType()), 0.70, foodPosition.getTopLeftX(), foodPosition.getTopLeftY());
    }

    /**
     * Draws the cat onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawFeeder(Graphics graphics) throws IOException {
        Cat cat = game.getCat();
        Cell catPosition = cat.getPosition();
        drawImage(graphics, getCatFilePath(cat.getDirection()), 0.90, catPosition.getTopLeftX(), catPosition.getTopLeftY());
    }

    private Color getBackgroundColor(GameState state) {
        switch (state) {
            case MAIN_STATE:
                return DEFAULT_COLOR;
            case INTENSE_STATE:
                return INTENSE_COLOUR;
            case GAME_OVER_STATE:
                return GAME_OVER_COLOR;
            case PAUSE_STATE:
                return unpauseState == GameState.MAIN_STATE ? DEFAULT_COLOR : INTENSE_COLOUR;
            default:
                return DEFAULT_COLOR;
        }
    }

    private String getFoodFilePath(FoodType type) {
        switch (type) {
            case BOMB:
                return BOMB_PATH;
            case SLOW:
                return SLOW_PATH;
            case SS:
                return SS_PATH;
            case S:
                return S_PATH;
            case A:
                return A_PATH;
            case B:
                return B_PATH;
            default:
                return C_PATH;
        }
    }

    private String getCatFilePath(Direction direction) {
        switch (direction) {
            case DOWN:
                return CAT_D_PATH;
            case LEFT:
                return CAT_L_PATH;
            case RIGHT:
                return CAT_R_PATH;
            default:
                return CAT_U_PATH;
        }
    }
}
