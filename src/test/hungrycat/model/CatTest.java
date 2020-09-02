package model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static model.Cell.CELL_PIXELS;

public class CatTest {
    private static final Cell CELL = new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS);

    private Cat cat;

    @Before
    public void setUp() {
        cat = new Cat(CELL);
    }

    @Test
    public void testConstructor() {
        Assertions.assertThat(cat.getPosition()).isEqualTo(CELL);
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.UP);
        Assertions.assertThat(cat.getFullness()).isEqualTo(0);
    }

    @Test
    public void testRotate() {
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.UP);
        cat.move();

        cat.rotate(Direction.UP);
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.UP);
        cat.move();

        cat.rotate(Direction.DOWN);
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.DOWN);
        cat.move();

        cat.rotate(Direction.LEFT);
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.LEFT);
        cat.move();

        cat.rotate(Direction.RIGHT);
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.RIGHT);
    }

    @Test
    public void testMove() {
        Cell pos = cat.getPosition();
        int x = pos.getTopLeftX();
        int y = pos.getTopLeftY();

        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y - CELL_PIXELS);
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x);
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y - (2 * CELL_PIXELS));
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x);

        cat.rotate(Direction.LEFT);
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y - (2 * CELL_PIXELS));
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x - CELL_PIXELS);
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y - (2 * CELL_PIXELS));
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x - (2 * CELL_PIXELS));

        cat.rotate(Direction.DOWN);
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y - CELL_PIXELS);
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x - (2 * CELL_PIXELS));
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y);
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x - (2 * CELL_PIXELS));

        cat.rotate(Direction.RIGHT);
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y);
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x - CELL_PIXELS);
        cat.move();
        Assertions.assertThat(cat.getPosition().getTopLeftY()).isEqualTo(y);
        Assertions.assertThat(cat.getPosition().getTopLeftX()).isEqualTo(x);
    }
}
