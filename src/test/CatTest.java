import hungrycat.model.Cat;
import hungrycat.model.Cell;
import hungrycat.model.Direction;
import hungrycat.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hungrycat.model.Cell.CELL_PIXELS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CatTest {
    private Cat testCat;

    @BeforeEach
    void setUp() {
        Cell cell = new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS);
        testCat = new Cat(cell);
    }

    @Test
    void testConstructor() {
        Cell cell = new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS);
        assertEquals(cell, testCat.getPosition());
        assertEquals(Direction.UP, testCat.getDirection());
        assertEquals(0, testCat.getFullness());
    }

    @Test
    void testRotate() {
        assertEquals(Direction.UP, testCat.getDirection());
        testCat.move();

        testCat.rotate(Direction.UP);
        assertEquals(Direction.UP, testCat.getDirection());
        testCat.move();

        testCat.rotate(Direction.DOWN);
        assertEquals(Direction.DOWN, testCat.getDirection());
        testCat.move();

        testCat.rotate(Direction.LEFT);
        assertEquals(Direction.LEFT, testCat.getDirection());
        testCat.move();

        testCat.rotate(Direction.RIGHT);
        assertEquals(Direction.RIGHT, testCat.getDirection());
    }

    @Test
    void testMove() {
        Cell pos = testCat.getPosition();
        int x = pos.getTopLeftX();
        int y = pos.getTopLeftY();

        testCat.move();
        assertEquals(y - CELL_PIXELS, testCat.getPosition().getTopLeftY());
        assertEquals(x, testCat.getPosition().getTopLeftX());
        testCat.move();
        assertEquals(y - (2 * CELL_PIXELS), testCat.getPosition().getTopLeftY());
        assertEquals(x, testCat.getPosition().getTopLeftX());

        testCat.rotate(Direction.LEFT);
        testCat.move();
        assertEquals(y - (2 * CELL_PIXELS), testCat.getPosition().getTopLeftY());
        assertEquals(x - CELL_PIXELS, testCat.getPosition().getTopLeftX());
        testCat.move();
        assertEquals(y - (2 * CELL_PIXELS), testCat.getPosition().getTopLeftY());
        assertEquals(x - (2 * CELL_PIXELS), testCat.getPosition().getTopLeftX());

        testCat.rotate(Direction.DOWN);
        testCat.move();
        assertEquals(y - CELL_PIXELS, testCat.getPosition().getTopLeftY());
        assertEquals(x - (2 * CELL_PIXELS), testCat.getPosition().getTopLeftX());
        testCat.move();
        assertEquals(y, testCat.getPosition().getTopLeftY());
        assertEquals(x - (2 * CELL_PIXELS), testCat.getPosition().getTopLeftX());

        testCat.rotate(Direction.RIGHT);
        testCat.move();
        assertEquals(y, testCat.getPosition().getTopLeftY());
        assertEquals(x - CELL_PIXELS, testCat.getPosition().getTopLeftX());
        testCat.move();
        assertEquals(y, testCat.getPosition().getTopLeftY());
        assertEquals(x, testCat.getPosition().getTopLeftX());
    }
}
