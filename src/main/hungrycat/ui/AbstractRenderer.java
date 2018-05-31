package hungrycat.ui;

import hungrycat.model.FeederGame;

import java.awt.*;

public abstract class AbstractRenderer {
    protected FeederGame game;

    public AbstractRenderer(FeederGame game) {
        this.game = game;
    }

    /**
     * Draws as appropriate onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    public abstract void draw(Graphics graphics);

    /**
     * Helper: Draws the given text onto the given graphics.
     *
     * @param graphics  the graphics on which to be drawn.
     */
    protected void drawText(Graphics graphics, Font font, Color color, String text, int x, int y) {
        graphics.setFont(font);
        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }
}