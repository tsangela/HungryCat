package hungrycat.ui.renderer;

import java.awt.*;

import hungrycat.model.Game;
import hungrycat.ui.HungryCatApp;

public class PauseRenderer extends AbstractRenderer {
    private static final Color TEXT_COLOR = new Color(0, 0, 0);

    /**
     * Creates a pause screen renderer set to the given game.
     *
     * @param game the game to render.
     */
    public PauseRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the pause screen onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {
        MainRenderer renderer = new MainRenderer(game);
        renderer.draw(graphics);
        drawPause(graphics);
    }

    /**
     * Draws the pause string onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    private void drawPause(Graphics graphics) {
        Font font = new Font(Font.MONOSPACED, Font.ITALIC, 15);
        String text = "game paused";

        graphics.setFont(font);
        int x = (HungryCatApp.WIDTH - graphics.getFontMetrics().stringWidth(text)) / 2;
        int y = HungryCatApp.HEIGHT / 2;
        drawText(graphics, TEXT_COLOR, text, x, y);
    }
}
