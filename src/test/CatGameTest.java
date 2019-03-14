import hungrycat.model.Cell;
import hungrycat.model.Direction;
import hungrycat.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static hungrycat.model.Game.BOARD_ROWS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CatGameTest {
    private Game testGame;

    @BeforeEach
    public void setUp() {
        testGame = new Game();
    }

    @Test
    public void testConstructor() {
        assertEquals(Direction.UP, testGame.getCat().getDirection());
        assertNotEquals(testGame.getCat().getPosition(), testGame.getFood().getPosition());
    }

    @Test
    public void testHitUpperWall() {
        assertGameOver(Direction.UP);
    }

    @Test
    public void testHitLowerWall() {
        assertGameOver(Direction.DOWN);
    }

    @Test
    public void testHitLeftWall() {
        assertGameOver(Direction.LEFT);
    }

    @Test
    public void testHitRightWall() {
        assertGameOver(Direction.RIGHT);
    }

    @Test
    public void testRotateCat() {
        testGame.rotateCat(Direction.UP);
        assertEquals(Direction.UP, testGame.getCat().getDirection());
        testGame.update();

        testGame.rotateCat(Direction.LEFT);
        assertEquals(Direction.LEFT, testGame.getCat().getDirection());
        testGame.update();

        testGame.rotateCat(Direction.DOWN);
        assertEquals(Direction.DOWN, testGame.getCat().getDirection());
        testGame.update();

        testGame.rotateCat(Direction.RIGHT);
        assertEquals(Direction.RIGHT, testGame.getCat().getDirection());
    }

    @Test
    public void testFoodCreationPosition() {
        eatFood();
        assertNotEquals(testGame.getFood().getPosition(), testGame.getCat().getPosition());
    }

    private void assertGameOver(Direction direction) {
        testGame.rotateCat(direction);
        Cell pos = testGame.getCat().getPosition();
        int distance = BOARD_ROWS - pos.getRow();
        if (direction == Direction.RIGHT || direction == Direction.DOWN) {
            distance--;
        }

        for (int i = 0; i < distance; i++)
            testGame.update();

        assertFalse(testGame.isGameOver());

        testGame.update();
        assertTrue(testGame.isGameOver());
    }

    private void eatFood() {
        Cell foodPosition = testGame.getFood().getPosition();
        Cell catPosition = testGame.getCat().getPosition();
        int rowDiff = foodPosition.getRow() - catPosition.getRow();
        int colDiff = foodPosition.getCol() - catPosition.getCol();

        if (rowDiff > 0) {
            testGame.rotateCat(Direction.DOWN);
            for (int i = 0; i < rowDiff; i++)
                testGame.update();
        } else if (rowDiff < 0) {
            testGame.rotateCat(Direction.UP);
            for (int i = 0; i < -rowDiff; i++)
                testGame.update();
        }

        if (colDiff > 0) {
            testGame.rotateCat(Direction.RIGHT);
            for (int i = 0; i < colDiff; i++)
                testGame.update();
        } else if (colDiff < 0) {
            testGame.rotateCat(Direction.LEFT);
            for (int i = 0; i < -colDiff; i++)
                testGame.update();
        }
    }
}