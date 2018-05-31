package hungrycat.model;

/**
 * Represents a food item.
 */
public class Food {
    private Cell     position;
    private FoodType type;
    private int      value;


    /**
     * Creates a food item of random type and places it at the given position.
     *
     * @param position  the cell on the board to place the food.
     */
    public Food(Cell position) {
        this.position = position;
        type = FoodType.randomFoodType();
        value = type.getValue();
    }

    /**
     * Returns the current position of the food.
     *
     * @return the current cell of the food.
     */
    public Cell getPosition() {
        return position;
    }

    /**
     * Returns the food type.
     *
     * @return the food type of the food.
     */
    public FoodType getType() {
        return type;
    }

    /**
     * Returns the nutritional value of the food.
     *
     * @return the nutritional value of the food.
     */
    public int getValue() {
        return value;
    }

}
