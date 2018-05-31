package hungrycat.tests;

import hungrycat.model.Cell;
import hungrycat.model.Direction;
import hungrycat.model.Feeder;
import hungrycat.model.FeederGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hungrycat.model.Cell.CELL_PIXELS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeederTest {
    private Feeder testFeeder;

    @BeforeEach
    public void runBefore() {
        Cell cell = new Cell(FeederGame.BOARD_ROWS /2, FeederGame.BOARD_COLS);
        testFeeder = new Feeder(cell);
    }

    @Test
    public void testConstructor() {
        Cell cell = new Cell(FeederGame.BOARD_ROWS /2, FeederGame.BOARD_COLS);
        assertEquals(cell, testFeeder.getPosition());
        assertEquals(Direction.UP, testFeeder.getDirection());
        assertEquals(0, testFeeder.getFullness());
    }

    @Test
    public void testRotate() {
        assertEquals(Direction.UP, testFeeder.getDirection());

        testFeeder.rotate(Direction.UP);
        assertEquals(Direction.UP, testFeeder.getDirection());

        testFeeder.rotate(Direction.DOWN);
        assertEquals(Direction.DOWN, testFeeder.getDirection());

        testFeeder.rotate(Direction.LEFT);
        assertEquals(Direction.LEFT, testFeeder.getDirection());

        testFeeder.rotate(Direction.RIGHT);
        assertEquals(Direction.RIGHT, testFeeder.getDirection());
    }

    @Test
    public void testMove() {
        Cell pos = testFeeder.getPosition();
        int x = pos.getTopLeftX();
        int y = pos.getTopLeftY();

        testFeeder.move();
        assertEquals(y-CELL_PIXELS, testFeeder.getPosition().getTopLeftY());
        assertEquals(x, testFeeder.getPosition().getTopLeftX());
        testFeeder.move();
        assertEquals(y-(2*CELL_PIXELS), testFeeder.getPosition().getTopLeftY());
        assertEquals(x, testFeeder.getPosition().getTopLeftX());

        testFeeder.rotate(Direction.LEFT);
        testFeeder.move();
        assertEquals(y-(2*CELL_PIXELS), testFeeder.getPosition().getTopLeftY());
        assertEquals(x-CELL_PIXELS, testFeeder.getPosition().getTopLeftX());
        testFeeder.move();
        assertEquals(y-(2*CELL_PIXELS), testFeeder.getPosition().getTopLeftY());
        assertEquals(x-(2*CELL_PIXELS), testFeeder.getPosition().getTopLeftX());

        testFeeder.rotate(Direction.DOWN);
        testFeeder.move();
        assertEquals(y-CELL_PIXELS, testFeeder.getPosition().getTopLeftY());
        assertEquals(x-(2*CELL_PIXELS), testFeeder.getPosition().getTopLeftX());
        testFeeder.move();
        assertEquals(y, testFeeder.getPosition().getTopLeftY());
        assertEquals(x-(2*CELL_PIXELS), testFeeder.getPosition().getTopLeftX());

        testFeeder.rotate(Direction.RIGHT);
        testFeeder.move();
        assertEquals(y, testFeeder.getPosition().getTopLeftY());
        assertEquals(x-CELL_PIXELS, testFeeder.getPosition().getTopLeftX());
        testFeeder.move();
        assertEquals(y, testFeeder.getPosition().getTopLeftY());
        assertEquals(x, testFeeder.getPosition().getTopLeftX());
    }
}
