package hungrycat.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Represents a food item.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Food {
    private Cell position; // Current position of the food
    private FoodType type;
    private int value; // Nutritional value of the food
    private int deceleration;

    /**
     * Creates a food of random type and places it at the given position.
     *
     * @param position the cell on the board to place the food.
     */
    public Food(Cell position) {
        this.position = position;
        type = FoodType.getRandomFoodType();
        value = type.getValue();
        deceleration = type.getDeceleration();
    }
}
