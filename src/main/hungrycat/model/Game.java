package model;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a game of Hungry Cat.
 */
@Getter
public class Game {
    public static final int BOARD_ROWS = 20;
    public static final int BOARD_COLS = BOARD_ROWS;
    private static final Random RANDOM = new Random();

    private Cat cat;
    private Food food;
    @Setter
    private GameState state; // Current state of the game

    /**
     * Creates a game with the cat at the center of the board and a food item at
     * a random cell set to the title game state.
     */
    public Game() {
        cat = new Cat(new Cell(BOARD_ROWS / 2, BOARD_COLS / 2));
        food = createFood();
        state = GameState.TITLE_STATE;
    }

    /**
     * If game the is not over, move the cat.
     * If cat reached a food item, eat it and create a new food item at random location.
     */
    public void update() {
        if (!isGameOver()) {
            cat.move();
            if (canEat()) {
                eatFood();
                food = createFood();
            }
        }
    }

    /**
     * Returns true if the game is over, false otherwise.
     *
     * @return true if the cat goes out of the game window.
     */
    public boolean isGameOver() {
        return !isInFrame(cat.getPosition());
    }

    /**
     * Restarts the game.
     */
    public void restart() {
        cat = new Cat(new Cell(BOARD_ROWS / 2, BOARD_COLS / 2));
        food = createFood();
        state = GameState.TITLE_STATE;
    }

    /**
     * Returns true if the cat is within the game window, false otherwise.
     *
     * @return true if the cat's position is within the frame, false otherwise.
     */
    private boolean isInFrame(Cell cell) {
        return cell.getCol() >= 0 && cell.getCol() < BOARD_COLS
                && cell.getRow() >= 0 && cell.getRow() < BOARD_ROWS;
    }

    /**
     * Returns true if the cat can eat a food item.
     *
     * @return true if the cat has reached a food cell.
     */
    private boolean canEat() {
        return cat.getPosition().equals(food.getPosition());
    }

    /**
     * The cat eats the food.
     */
    private void eatFood() {
        assert canEat();
        cat.eat(food);
        cat.levelUp();
    }

    /**
     * Returns a random food at a random location.
     *
     * @return a food item of random type at a random cell other than the current
     * location of the cat.
     */
    private Food createFood() {
        Cell position = randomCell();

        while (position.equals(cat.getPosition()))
            position = randomCell();

        return new Food(position);
    }

    /**
     * Rotates the cat to the given direction.
     *
     * @param d The direction to rotate the cat.
     */
    public void rotateCat(Direction d) {
        cat.rotate(d);
    }

    /**
     * Returns a random cell on the board.
     *
     * @return a random cell on the board.
     */
    private Cell randomCell() {
        return new Cell(RANDOM.nextInt(BOARD_ROWS), RANDOM.nextInt(BOARD_COLS));
    }
}
