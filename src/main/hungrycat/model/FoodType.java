package hungrycat.model;

import java.util.*;

/**
 * Represents the types of food.
 */
public enum FoodType {
    // TODO: create "bomb" with negative nutritional value and time limit
    C(1), B(2), A(3), S(4), SS(10);
    private final int value;
    private static final Random RANDOM = new Random();


    /**
     * Creates a food type with an associated nutritional value.
     *
     * @param value the nutritional value to be associated with the specified food type.
     */
    FoodType(int value) {
        this.value = value;
    }

    /**
     * Returns the associated nutritional value of a food type.
     *
     * @return the associated nutritional value of the food type.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the probability distribution.
     *
     * @return a biased list of strings of types based on their rarities.
     */
    private static List<String> distribution() {
        List<String> values = new ArrayList<>();

        addTo(values, "C", 55);
        addTo(values, "B", 25);
        addTo(values, "A", 15);
        addTo(values, "S",  4);
        addTo(values, "SS", 1);

        return Collections.unmodifiableList(values);
    }

    /**
     * Adds the given string to the given list prob number of times to mimic probability distribution.
     *
     * @param type  The type of food as a String.
     * @param prob  The probability out of 100 of the appearance of the food type.
     */
    private static void addTo(List<String> list, String type, int prob) {
        for (int i = 0; i < prob; i++)
            list.add(type);
    }

    /**
     * Returns the size of the list.
     *
     * @param l the given list of which to compute the size.
     * @return the integer size of the list.
     */
    private static int getDistributionSize(List l) {
        return l.size();
    }

    /**
     * Returns a random type of food.
     *
     * @return a random FoodType item.
     */
    public static FoodType randomFoodType() {

        FoodType type = C;

        List<String> dist = distribution();
        int size = getDistributionSize(dist);

        String got = dist.get(RANDOM.nextInt(size));

        switch(got) {
            case "C":
                type = C;
                break;
            case "B":
                type = B;
                break;
            case "A":
                type = A;
                break;
            case "S":
                type = S;
                break;
            case "SS":
                type = SS;
                break;
        }

        return type;
    }

}
