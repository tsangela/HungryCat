package hungrycat.model;

import java.util.Random;

/**
 * Represents a game of Hungry Cat.
 */
public class Game {
    public static final int BOARD_ROWS = 20;
    public static final int BOARD_COLS = BOARD_ROWS;

    private Random    random;
    private Cat       cat;
    private Food      food;
    private GameState state;

    /**
     * Creates a game with the cat at the center of the board and a food item at
     * a random cell set to the title game state.
     */
    public Game() {
        random = new Random();
        cat = new Cat(new Cell(BOARD_ROWS/2, BOARD_COLS/2));
        food   = createFood();
        state  = GameState.TITLE_STATE;
    }

    /**
     * If game the is not over, move the cat.
     * If cat reached a food item, eat it and create a new food item at random location.
     */
    public void update() {
        if (!isGameOver()) {
            cat.move();
            if (canCatEat()) {
                eatFood();
                food = createFood();
            }
        }
    }

    /**
     * Returns the position of the cat.
     *
     * @return the current cell of the cat.
     */
    public Cell getCatPosition() {
        return cat.getPosition();
    }

    /**
     * Returns the direction in which the cat is facing.
     *
     * @return the current direction of the cat.
     */
    public Direction getCatDirection() {
        return cat.getDirection();
    }

    /**
     * Returns the fullness level of the cat.
     *
     * @return an integer indicating the cat's level of fullness.
     */
    public int getCatFullness() {
        return cat.getFullness();
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
     * @return true if the cat goes out of the game window.
     */
    public boolean isGameOver() {
        return !isInFrame(cat.getPosition());
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
    private boolean canCatEat() {
        return cat.getPosition().equals(food.getPosition());
    }

    /**
     * The cat eats the food.
     */
    private void eatFood() {
        assert canCatEat();
        cat.eat(food);
    }

    /**
     * Returns a random food at a random location.
     *
     * @return a food item of random type at a random cell other than the current
     *         location of the cat.
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
        return new Cell(random.nextInt(BOARD_ROWS), random.nextInt(BOARD_COLS));
    }
}
