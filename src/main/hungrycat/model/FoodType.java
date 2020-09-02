package hungrycat.model;

import java.util.Arrays;
import java.util.Random;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Represents the types of food.
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FoodType {
    // Special items
    BOMB(-10, 0, 0),
    SLOW(0, 30, 2),
    // Foods
    SS(20, 0, 5),
    S(10, 0, 10),
    A(5, 0, 20),
    B(2, 0, 40),
    C(1, 0, 100);

    private static final Random RANDOM = new Random();

    int value;        // Associated nutritional value of a food type
    int deceleration; // Slow-down factor
    double upper;     // Upper bound of distribution spectrum

    /**
     * Returns a type of food based on its probability of appearance.
     *
     * @return a type of food.
     */
    public static FoodType getRandomFoodType() {
        double random = RANDOM.nextDouble() * 100;
        FoodType type = Arrays.stream(FoodType.values())
                .filter(t -> t.getUpper() >= random)
                .findFirst()
                .orElse(C);
        return type;
//        return Arrays.stream(FoodType.values())
//                .filter(type -> type.getUpper() >= random)
//                .collect(Collectors.toList())
//                .get(0);
    }
}
