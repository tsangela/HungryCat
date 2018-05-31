package hungrycat.tests;

import hungrycat.model.Cell;
import hungrycat.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    private Cell testCell;

    @BeforeEach
    public void runBefore() {
        testCell = new Cell(Game.BOARD_ROWS /2, Game.BOARD_COLS);
    }

    @Test
    public void testConstructor() {
        assertEquals(testCell.getCol() * Cell.CELL_PIXELS, testCell.getTopLeftX());
        assertEquals(testCell.getRow() * Cell.CELL_PIXELS, testCell.getTopLeftY());
    }

    @Test
    public void testTopLeftX() {
        Cell c = new Cell(5, 3);
        assertEquals(3 * Cell.CELL_PIXELS, c.getTopLeftX());
    }

    @Test
    public void testTopLeftY() {
        Cell c = new Cell(5, 3);
        assertEquals(5 * Cell.CELL_PIXELS, c.getTopLeftY());
    }

    @Test
    public void testEquals() {
        Cell c1 = new Cell(2, 2);
        Cell c2 = new Cell(2, 2);
        assertEquals(c1, c2);
    }
}
