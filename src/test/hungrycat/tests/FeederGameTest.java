package hungrycat.tests;

import hungrycat.model.Cell;
import hungrycat.model.Direction;
import hungrycat.model.FeederGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hungrycat.model.FeederGame.BOARD_COLS;
import static hungrycat.model.FeederGame.BOARD_ROWS;
import static org.junit.jupiter.api.Assertions.*;

public class FeederGameTest {
    private FeederGame testGame;

    @BeforeEach
    public void runBefore() {
        testGame = new FeederGame();
    }

    @Test
    public void testConstructor() {
        assertEquals(Direction.UP, testGame.getFeederDirection());
        assertNotEquals(testGame.getFeederPosition(), testGame.getFoodPosition());
    }

    @Test
    public void testIsOverMoveUp() {
        Cell pos = testGame.getFeederPosition();
        int distance = BOARD_ROWS - pos.getRow();

        for (int i = 0; i < distance; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsOverMoveDown() {
        testGame.rotateFeeder(Direction.DOWN);
        Cell pos = testGame.getFeederPosition();
        int distance = BOARD_COLS - pos.getRow();

        for (int i = 0; i < distance - 1; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsOverMoveLeft() {
        testGame.rotateFeeder(Direction.LEFT);
        Cell pos = testGame.getFeederPosition();
        int distance = BOARD_COLS - pos.getCol();

        for (int i = 0; i < distance; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsOverMoveRight() {
        testGame.rotateFeeder(Direction.RIGHT);
        Cell pos = testGame.getFeederPosition();
        int distance = BOARD_COLS - pos.getCol();

        for (int i = 0; i < distance - 1; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testRotateFeeder() {
        testGame.rotateFeeder(Direction.UP);
        assertEquals(Direction.UP, testGame.getFeederDirection());
        testGame.rotateFeeder(Direction.LEFT);
        assertEquals(Direction.LEFT, testGame.getFeederDirection());
        testGame.rotateFeeder(Direction.DOWN);
        assertEquals(Direction.DOWN, testGame.getFeederDirection());
        testGame.rotateFeeder(Direction.RIGHT);
        assertEquals(Direction.RIGHT, testGame.getFeederDirection());
    }

    @Test
    public void testAutomaticFoodCreation() {
        eatFood();
        assertNotEquals(testGame.getFoodPosition(), testGame.getFeederPosition());
    }

    // MODIFIES: this
    // EFFECTS:  move feeder to cell containing food so that food is eaten
    private void eatFood() {
        Cell foodPosition = testGame.getFoodPosition();
        Cell feederPosition = testGame.getFeederPosition();
        int rowDiff = foodPosition.getRow() - feederPosition.getRow();
        int colDiff = foodPosition.getCol() - feederPosition.getCol();

        if (rowDiff > 0) {
            testGame.rotateFeeder(Direction.DOWN);
            for (int i = 0; i < rowDiff; i++)
                testGame.update();
        }
        else if (rowDiff < 0) {
            testGame.rotateFeeder(Direction.UP);
            for (int i = 0; i < -rowDiff; i++)
                testGame.update();
        }

        if (colDiff > 0) {
            testGame.rotateFeeder(Direction.RIGHT);
            for (int i = 0; i < colDiff; i++)
                testGame.update();
        }
        else if (colDiff < 0) {
            testGame.rotateFeeder(Direction.LEFT);
            for (int i = 0; i < -colDiff; i++)
                testGame.update();
        }
    }

}