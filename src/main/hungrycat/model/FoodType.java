package hungrycat.model;

import java.util.Arrays;
import java.util.List;
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
    // Special items
    BOMB(-10, 0, 0),
    SLOW(0, 10, 2),
    // Foods
    SS(20, 0, 3),
    S(10, 0, 6),
    A(5, 0, 12),
    B(2, 0, 35),
    C(1, 0, 100);

    private static final Random RANDOM = new Random();

    private final int value; // Associated nutritional value of a food type
    private final int deceleration; // Slow-down factor
    private final double upper; // Upper bound of distribution spectrum

    /**
     * Returns a type of food based on its probability of appearance.
     *
     * @return a type of food.
     */
    public static FoodType getRandomFoodType() {
        double random = RANDOM.nextDouble() * 100;
        List<FoodType> types = Arrays.stream(FoodType.values())
                .filter(type -> type.getUpper() >= random)
                .collect(Collectors.toList());
        System.out.println(random + " -> " + types);
        return types.get(0);
//        return Arrays.stream(FoodType.values())
//                .filter(type -> type.getUpper() >= random)
//                .collect(Collectors.toList())
//                .get(0);
    }
}
