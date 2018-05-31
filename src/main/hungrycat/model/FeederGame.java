package hungrycat.model;

import java.util.Random;

/**
 * Represents a game of feeder.
 */
public class FeederGame {
    public static final int BOARD_ROWS = 20;
    public static final int BOARD_COLS = BOARD_ROWS;

    private Random    random;
    private Feeder    feeder;
    private Food      food;
    private GameState state;

    /**
     * Creates a game with the feeder at the center of the board and a food item at
     * a random cell set to the title game state.
     */
    public FeederGame() {
        random = new Random();
        feeder = new Feeder(new Cell(BOARD_ROWS/2, BOARD_COLS/2));
        food   = createFood();
        state  = GameState.TITLE_STATE;
    }

    /**
     * If game the is not over, move the feeder.
     * If feeder reached a food item, eat it and create a new food item at random location.
     */
    public void update() {
        if (!isGameOver()) {
            feeder.move();
            if (canFeederEat()) {
                eatFood();
                food = createFood();
            }
        }
    }

    /**
     * Returns the position of the feeder.
     *
     * @return the current cell of the feeder.
     */
    public Cell getFeederPosition() {
        return feeder.getPosition();
    }

    /**
     * Returns the direction in which the feeder is facing.
     *
     * @return the current direction of the feeder.
     */
    public Direction getFeederDirection() {
        return feeder.getDirection();
    }

    /**
     * Returns the fullness level of the feeder.
     *
     * @return an integer indicating the feeder's level of fullness.
     */
    public int getFeederFullness() {
        return feeder.getFullness();
    }

    /**
     * Returns the position of the food.
     *
     * @return the current cell of the food.
     */
    public Cell getFoodPosition() {
        return food.getPosition();
    }

    /**
     * Returns the type of the food.
     *
     * @return the FoodType of the food.
     */
    public FoodType getFoodType() {
        return food.getType();
    }

    /**
     * Returns the nutritional value of the food.
     *
     * @return an integer representing the nutritional value of the food.
     */
    public int getFoodNutritionalValue() {
        return food.getValue();
    }

    /**
     * Returns the current state of the game.
     *
     * @return the current state of the game.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the current game state to the given state.
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Returns true if the game is over, false otherwise.
     *
     * @return true if the feeder goes out of the game window.
     */
    public boolean isGameOver() {
        return !isInFrame(feeder.getPosition());
    }

    /**
     * Returns true if the feeder is within the game window, false otherwise.
     *
     * @return true if the feeder's position is within the frame, false otherwise.
     */
    private boolean isInFrame(Cell cell) {
        return cell.getCol() >= 0 && cell.getCol() < BOARD_COLS
                && cell.getRow() >= 0 && cell.getRow() < BOARD_ROWS;
    }

    /**
     * Returns true if the feeder can eat a food item.
     *
     * @return true if the feeder has reached a food cell.
     */
    private boolean canFeederEat() {
        return feeder.getPosition().equals(food.getPosition());
    }

    /**
     * The feeder eats the food.
     */
    private void eatFood() {
        assert canFeederEat();
        feeder.eat(food);
    }

    /**
     * Returns a random food at a random location.
     *
     * @return a food item of random type at a random cell other than the current
     *         location of the feeder.
     */
    private Food createFood() {
        Cell position = randomCell();

        while (position.equals(feeder.getPosition()))
            position = randomCell();

        return new Food(position);
    }

    /**
     * Rotates the feeder to the given direction.
     *
     * @param d The direction to rotate the feeder.
     */
    public void rotateFeeder(Direction d) {
        feeder.rotate(d);
    }

    /**
     * Returns a random cell on the board.
     *
     * @return a random cell on the board.
     */
    private Cell randomCell() {
        return new Cell(random.nextInt(BOARD_ROWS), random.nextInt(BOARD_COLS));
    }
}
