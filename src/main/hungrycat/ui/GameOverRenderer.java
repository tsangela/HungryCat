package hungrycat.ui;

import hungrycat.model.Game;

import java.awt.*;

import static hungrycat.ui.HungryCatApp.HEIGHT;
import static hungrycat.ui.HungryCatApp.WIDTH;

public class GameOverRenderer extends AbstractRenderer {
    private static final Color GAME_OVER_COLOR = new Color(56, 56, 56);
    private static final Color TEXT_COLOR = new Color(82, 82, 82);
    private static final Color OMAE_COLOR = new Color(100, 0, 0);

    /**
     * Creates a game over screen renderer set to the given game.
     *
     * @param game  the game to render.
     */
    public GameOverRenderer(Game game) {
        super(game);
    }

    /**
     * Draws the game over screen onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    @Override
    public void draw(Graphics graphics) {
        MainRenderer r = new MainRenderer(game);
        r.draw(graphics);

//        graphics.setColor(GAME_OVER_COLOR);
//        graphics.fillRect(0,0, WIDTH, HEIGHT);

        drawGameOver(graphics);
        drawOmae(graphics);
        drawCredits(graphics);
    }

    /**
     * Draws the game over string onto the screen.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawGameOver(Graphics graphics) {
        Font font = new Font(Font.SERIF, Font.BOLD, 40);
        String text = "G A M E   O V E R";

        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (WIDTH - metrics.stringWidth(text)) / 2;
        int y = HEIGHT / 2;
        graphics.drawString(text, x, y);
    }

    /**
     * Draws "お前はもう死んでいる。" onto the screen.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawOmae(Graphics graphics) {
        Font font = new Font(Font.SERIF, Font.BOLD, 18);
        String text = "お前はもう死んでいる。";

        graphics.setFont(font);
        graphics.setColor(OMAE_COLOR);
        FontMetrics metrics = graphics.getFontMetrics();
        int x = (WIDTH - metrics.stringWidth(text)) / 2;
        int y = (HEIGHT / 2) + 30;
        graphics.drawString(text, x, y);
    }

    /**
     * Draws the credits strings onto the screen.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    private void drawCredits(Graphics graphics) {
        Font font = new Font(Font.MONOSPACED, Font.ITALIC, 8);
        graphics.setFont(font);
        graphics.setColor(TEXT_COLOR);
//        graphics.setColor(new Color(255, 255, 255));

        String text = "Credits:";
        int x = 8;
        int y = HEIGHT - 64;
        graphics.drawString(text, x, y);

        font = new Font(Font.MONOSPACED, Font.PLAIN, 8);
        graphics.setFont(font);

        text = "+ Character, \"[LPC] Cats and Dogs\" by bluecarrot16 of OpenGameArt.org";
        graphics.drawString(text, x, y+8);

        text = "+ Food, \"Food Icons\" by BizmasterStudios of OpenGameArt.org";
        graphics.drawString(text, x, y+16);

        text = "+ SFX, \"Cat, Screaming, A.wav\" by InspectorJ of Freesound.org";
        graphics.drawString(text, x, y+24);

        text = "       \"Pop sound\" by deraj of Freesound.org";
        graphics.drawString(text, x, y+32);

        text = "+ BGM, \"Level 0\" by Monplaisir";
        graphics.drawString(text, x, y+40);

        text = "       \"Level 3\" by Monplaisir";
        graphics.drawString(text, x, y+48);

        text = "       \"At Rest\" by Kevin MacLeod";
        graphics.drawString(text, x, y+56);
    }

}
