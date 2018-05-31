package hungrycat.ui;


import hungrycat.model.Game;

import java.awt.*;

import static hungrycat.ui.HungryCatApp.HEIGHT;
import static hungrycat.ui.HungryCatApp.WIDTH;

/**
 * Represents a title renderer.
 */
public class TitleRenderer extends AbstractRenderer {
    private static final Color TITLE_COLOR = new Color(255, 204, 210);
    private static final Color TEXT_COLOR  = new Color(255, 255, 255);
    private static final String VERSION = "version 1.0.0";

    /**
     * Creates a title screen renderer set to the given game.
     *
     * @param game  the game to render.
     */
    TitleRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the title screen onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(TITLE_COLOR);
        graphics.fillRect(0,0, WIDTH, HEIGHT);

        drawTitle(graphics);
        drawVersion(graphics);
        drawInstructions(graphics);
        drawContinue(graphics);
    }

    /**
     * Draws the title onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawTitle(Graphics graphics) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
        String text = "HUNGRY CAT";

        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (WIDTH - metrics.stringWidth(text)) / 2;
        int y = HEIGHT / 2;
        graphics.drawString(text, x, y);
    }

    /**
     * Draws the version string onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawVersion(Graphics graphics) {
        Font font = new Font(Font.SERIF, Font.ITALIC, 10);
        String text = VERSION;

        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (WIDTH - metrics.stringWidth(text)) / 2;
        int y = (HEIGHT / 2) + 20;
        graphics.drawString(text, x, y);
    }

    /**
     * Draws the instruction strings onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawInstructions(Graphics graphics) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);

        String text = "Instructions: Use the arrow keys to move around. Different foods have";
        int x = 12;
        int y = HEIGHT - 52;
        graphics.drawString(text, x, y);

        text = "different nutritional values. The more food you eat, the faster you run!";
        graphics.drawString(text, x, y+20);

        text = "Avoid hitting the walls. Press the space bar to pause. Good luck!";
        graphics.drawString(text, x, y+40);
    }

    /**
     * Draws the instruction on how to continue onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawContinue(Graphics graphics) {
        Font font = new Font(Font.MONOSPACED, Font.ITALIC, 15);
        String text = "- Press space bar to start -";

        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (WIDTH - metrics.stringWidth(text)) / 2;
        int y = (HEIGHT / 2) + 70;
        graphics.drawString(text, x, y);
    }

}
