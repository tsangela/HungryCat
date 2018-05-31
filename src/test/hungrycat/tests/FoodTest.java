package hungrycat.tests;

import hungrycat.model.Cell;
import hungrycat.model.FeederGame;
import hungrycat.model.Food;
import hungrycat.model.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FoodTest {
    private Food testFood;

    @BeforeEach
    public void runBefore() {
        Cell cell = new Cell(FeederGame.BOARD_ROWS /2, FeederGame.BOARD_COLS);
        testFood = new Food(cell);
    }

    @Test
    public void testConstructor() {
        assertEquals(new Cell(FeederGame.BOARD_ROWS /2, FeederGame.BOARD_COLS),
                testFood.getPosition());
    }

    @Test
    public void testFoodTypeValue() {
        FoodType type = testFood.getType();
        if (type == FoodType.C) {
            assertEquals(1, testFood.getValue());
        } else if (type == FoodType.B) {
            assertEquals(3, testFood.getValue());
        } else if (type == FoodType.A) {
            assertEquals(5, testFood.getValue());
        } else if (type == FoodType.S) {
            assertEquals(10, testFood.getValue());
        } else if (type == FoodType.SS) {
            assertEquals(50, testFood.getValue());
        } else {
            fail("Food type is none of SS, S, A, B, C!");
        }

        System.out.println("Food is of type " + testFood.getType()
                + " and has value " + testFood.getValue() + ".");
    }
}
