package hungrycat.model;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the types of food.
 */
@AllArgsConstructor
@Getter
public enum FoodType {
    // TODO: create "bomb" with negative nutritional value and time limit
    SS(20, 0),
    S(10, 2),
    A(5, 9),
    B(2, 29),
    C(1, 99);

    private static final Random RANDOM = new Random();

    private final int value; // Associated nutritional value of a food type
    private final int upper; // Upper bound of distribution spectrum

    /**
     * Returns a type of food based on its probability of appearance.
     *
     * @return a type of food.
     */
    public static FoodType getRandomFoodType() {
        int random = RANDOM.nextInt(99);
        // System.out.println(random + " -> " + foodType.name());
        return Arrays.stream(FoodType.values())
                .filter(type -> type.getUpper() >= random)
                .collect(Collectors.toList())
                .get(0);
    }
}
