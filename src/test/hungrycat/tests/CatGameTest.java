package hungrycat.tests;

import hungrycat.model.Cell;
import hungrycat.model.Direction;
import hungrycat.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hungrycat.model.Game.BOARD_COLS;
import static hungrycat.model.Game.BOARD_ROWS;
import static org.junit.jupiter.api.Assertions.*;

public class CatGameTest {
    private Game testGame;

    @BeforeEach
    public void runBefore() {
        testGame = new Game();
    }

    @Test
    public void testConstructor() {
        assertEquals(Direction.UP, testGame.getCatDirection());
        assertNotEquals(testGame.getCatPosition(), testGame.getFoodPosition());
    }

    @Test
    public void testIsOverMoveUp() {
        Cell pos = testGame.getCatPosition();
        int distance = BOARD_ROWS - pos.getRow();

        for (int i = 0; i < distance; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsOverMoveDown() {
        testGame.rotateCat(Direction.DOWN);
        Cell pos = testGame.getCatPosition();
        int distance = BOARD_COLS - pos.getRow();

        for (int i = 0; i < distance - 1; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsOverMoveLeft() {
        testGame.rotateCat(Direction.LEFT);
        Cell pos = testGame.getCatPosition();
        int distance = BOARD_COLS - pos.getCol();

        for (int i = 0; i < distance; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());
        
        testGame.update();
        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testIsOverMoveRight() {
        testGame.rotateCat(Direction.RIGHT);
        Cell pos = testGame.getCatPosition();
        int distance = BOARD_COLS - pos.getCol();

        for (int i = 0; i < distance - 1; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();

        assertTrue(testGame.isGameOver());
    }

    @Test
    public void testRotateFeeder() {
        testGame.rotateCat(Direction.UP);
        assertEquals(Direction.UP, testGame.getCatDirection());
        testGame.rotateCat(Direction.LEFT);
        assertEquals(Direction.LEFT, testGame.getCatDirection());
        testGame.rotateCat(Direction.DOWN);
        assertEquals(Direction.DOWN, testGame.getCatDirection());
        testGame.rotateCat(Direction.RIGHT);
        assertEquals(Direction.RIGHT, testGame.getCatDirection());
    }

    @Test
    public void testAutomaticFoodCreation() {
        eatFood();
        assertNotEquals(testGame.getFoodPosition(), testGame.getCatPosition());
    }

    // MODIFIES: this
    // EFFECTS:  move feeder to cell containing food so that food is eaten
    private void eatFood() {
        Cell foodPosition = testGame.getFoodPosition();
        Cell feederPosition = testGame.getCatPosition();
        int rowDiff = foodPosition.getRow() - feederPosition.getRow();
        int colDiff = foodPosition.getCol() - feederPosition.getCol();

        if (rowDiff > 0) {
            testGame.rotateCat(Direction.DOWN);
            for (int i = 0; i < rowDiff; i++)
                testGame.update();
        }
        else if (rowDiff < 0) {
            testGame.rotateCat(Direction.UP);
            for (int i = 0; i < -rowDiff; i++)
                testGame.update();
        }

        if (colDiff > 0) {
            testGame.rotateCat(Direction.RIGHT);
            for (int i = 0; i < colDiff; i++)
                testGame.update();
        }
        else if (colDiff < 0) {
            testGame.rotateCat(Direction.LEFT);
            for (int i = 0; i < -colDiff; i++)
                testGame.update();
        }
    }

}