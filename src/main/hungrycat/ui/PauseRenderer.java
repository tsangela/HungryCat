package hungrycat.ui;

import hungrycat.model.Game;

import java.awt.*;

import static hungrycat.ui.HungryCatApp.HEIGHT;
import static hungrycat.ui.HungryCatApp.WIDTH;

public class PauseRenderer extends AbstractRenderer {
    private static final Color PAUSE_COLOR = new Color(255, 255, 255);
    private static final Color TEXT_COLOR = new Color(0, 0, 0);

    /**
     * Creates a pause screen renderer set to the given game.
     *
     * @param game  the game to render.
     */
    public PauseRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the pause screen onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {
//        graphics.setColor(PAUSE_COLOR);
//        graphics.fillRect(0,0, WIDTH, HEIGHT);


        MainRenderer r = new MainRenderer(game);
        r.draw(graphics);

        drawPause(graphics);
    }

    /**
     * Draws the pause string onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawPause(Graphics graphics) {
        Font font = new Font(Font.MONOSPACED, Font.ITALIC, 15);
        String text = "game paused";

        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (WIDTH - metrics.stringWidth(text)) / 2;
        int y = HEIGHT / 2;
        graphics.drawString(text, x, y);
    }
}
