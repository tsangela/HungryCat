package ui.renderer;

import java.awt.*;

import model.Game;

import static ui.HungryCatApp.HEIGHT;
import static ui.HungryCatApp.WIDTH;

/**
 * Represents a title renderer.
 */
public class TitleRenderer extends AbstractRenderer {
    private static final Color TITLE_COLOR = new Color(255, 204, 210);
    private static final Color TEXT_COLOR = new Color(255, 255, 255);
    private static final String VERSION = "version 1.0.0";

    /**
     * Creates a title screen renderer set to the given game.
     *
     * @param game the game to render.
     */
    public TitleRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the title screen onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(TITLE_COLOR);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        drawTitle(graphics);
        drawVersion(graphics);
        drawInstructions(graphics);
        drawContinue(graphics);
        drawMadeBy(graphics);
    }

    /**
     * Draws the title onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawTitle(Graphics graphics) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
        graphics.setFont(font);
        String text = "HUNGRY CAT";

        int x = (WIDTH - graphics.getFontMetrics().stringWidth(text)) / 2;
        int y = HEIGHT / 2;

        drawText(graphics, TEXT_COLOR, text, x, y);
    }

    /**
     * Draws the version string onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawVersion(Graphics graphics) {
        Font font = new Font(Font.SERIF, Font.ITALIC, 11);
        graphics.setFont(font);

        int x = (WIDTH - graphics.getFontMetrics().stringWidth(VERSION)) / 2;
        int y = (HEIGHT / 2) + 20;

        drawText(graphics, TEXT_COLOR, VERSION, x, y);
    }

    /**
     * Draws the instruction strings onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawInstructions(Graphics graphics) {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);

        int x = 12;
        int y = HEIGHT - 52;

        graphics.drawString("Instructions: Use the arrow keys to move around. Different foods have", x, y);
        graphics.drawString("different nutritional values. The more food you eat, the faster you run!", x, y + 20);
        graphics.drawString("Avoid hitting the walls. Press the space bar to pause. Good luck!", x, y + 40);
    }

    /**
     * Draws the instruction on how to continue onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawContinue(Graphics graphics) {
        Font font = new Font(Font.MONOSPACED, Font.ITALIC, 15);
        graphics.setFont(font);
        String text = "- Press space bar to start -";

        int x = (WIDTH - graphics.getFontMetrics().stringWidth(text)) / 2;
        int y = (HEIGHT / 2) + 70;

        drawText(graphics, TEXT_COLOR, text, x, y);
    }

    private void drawMadeBy(Graphics graphics) {
        Font font = new Font(Font.MONOSPACED, Font.ITALIC, 11);
        graphics.setFont(font);
        String text = "by tsangela";

        int x = WIDTH - graphics.getFontMetrics().stringWidth(text) - 4;
        int y = 12;

        drawText(graphics, TEXT_COLOR, text, x, y);
    }

//    private void setGraphics(Graphics graphics, Font font, String text, int x, int y) {
    // graphics.setFont(font);
//        graphics.setColor(TitleRenderer.TEXT_COLOR);
//        graphics.drawString(text, x, y);
//    }

}
