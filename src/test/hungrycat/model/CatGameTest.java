package hungrycat.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static hungrycat.model.Game.BOARD_ROWS;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatGameTest {
    Game game;
    Cat cat;

    @Before
    public void setUp() {
        game = new Game();
        cat = game.getCat();
    }

    @Test
    public void testConstructor() {
        Assertions.assertThat(cat.getDirection()).isEqualTo(Direction.UP);
        Assertions.assertThat(cat.getPosition()).isNotEqualTo(game.getFood().getPosition());
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
        assertCatRotatedAndUpdateGame(Direction.UP);
        assertCatRotatedAndUpdateGame(Direction.LEFT);
        assertCatRotatedAndUpdateGame(Direction.DOWN);
        assertCatRotatedAndUpdateGame(Direction.RIGHT);
    }

    @Test
    public void testFoodCreationPosition() {
        eatFood();
        Assertions.assertThat(game.getFood().getPosition()).isNotEqualTo(cat.getPosition());
    }

    private void assertGameOver(Direction direction) {
        game.rotateCat(direction);
        Cell position = cat.getPosition();

        // Distance to wall
        int distance = BOARD_ROWS - position.getRow();
        if (direction == Direction.RIGHT || direction == Direction.DOWN) {
            distance--;
        }

        // Move cat right up to wall
        for (int i = 0; i < distance; i++)
            game.update();
        Assertions.assertThat(game.isGameOver()).isFalse();

        // Hit wall
        game.update();
        Assertions.assertThat(game.isGameOver()).isTrue();
    }

    private void eatFood() {
        Cell foodPosition = game.getFood().getPosition();
        Cell catPosition = cat.getPosition();
        int rowDiff = foodPosition.getRow() - catPosition.getRow();
        int colDiff = foodPosition.getCol() - catPosition.getCol();

        if (rowDiff > 0) {
            game.rotateCat(Direction.DOWN);
            for (int i = 0; i < rowDiff; i++)
                game.update();
        } else if (rowDiff < 0) {
            game.rotateCat(Direction.UP);
            for (int i = 0; i < -rowDiff; i++)
                game.update();
        }

        if (colDiff > 0) {
            game.rotateCat(Direction.RIGHT);
            for (int i = 0; i < colDiff; i++)
                game.update();
        } else if (colDiff < 0) {
            game.rotateCat(Direction.LEFT);
            for (int i = 0; i < -colDiff; i++)
                game.update();
        }
    }

    private void assertCatRotatedAndUpdateGame(Direction direction) {
        game.rotateCat(direction);
        Assertions.assertThat(cat.getDirection()).isEqualTo(direction);
        game.update();
    }
}