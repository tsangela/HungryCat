package hungrycat.model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class CellTest {
    private Cell testCell;

    @Before
    public void setUp() {
        testCell = new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS);
    }

    @Test
    public void testConstructor() {
        Assertions.assertThat(testCell.getCol() * Cell.CELL_PIXELS).isEqualTo(testCell.getTopLeftX());
        Assertions.assertThat(testCell.getRow() * Cell.CELL_PIXELS).isEqualTo(testCell.getTopLeftY());
    }

    @Test
    public void testTopLeftX() {
        Cell c = new Cell(5, 3);
        Assertions.assertThat(3 * Cell.CELL_PIXELS).isEqualTo(c.getTopLeftX());
    }

    @Test
    public void testTopLeftY() {
        Cell c = new Cell(5, 3);
        Assertions.assertThat(5 * Cell.CELL_PIXELS).isEqualTo(c.getTopLeftY());
    }

    @Test
    public void testEquals() {
        Cell c1 = new Cell(2, 2);
        Cell c2 = new Cell(2, 2);
        Assertions.assertThat(c1).isEqualTo(c2);
    }
}
