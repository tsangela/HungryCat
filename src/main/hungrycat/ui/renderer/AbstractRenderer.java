package hungrycat.ui.renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import hungrycat.model.Cell;
import hungrycat.model.Game;


public abstract class AbstractRenderer {
    protected Game game;

    AbstractRenderer(Game game) {
        this.game = game;
    }

    /**
     * Draws as appropriate onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    public abstract void draw(Graphics graphics);

    /**
     * Draws the given text onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    void drawText(Graphics graphics, Color color, String text, int x, int y) {
        graphics.setColor(color);
        graphics.drawString(text, x, y);
    }

    /**
     * Draws the given image onto the given graphics.
     *
     * @param graphics the graphics on which to be drawn.
     */
    void drawImage(Graphics graphics, String pathName, double fillPercent, int x, int y) throws IOException {
        File file = new File(pathName);
        final BufferedImage image = ImageIO.read(file);
        final int size = (int) (Cell.CELL_PIXELS * fillPercent);

        graphics.drawImage(image, x, y, size, size, null);
    }
}