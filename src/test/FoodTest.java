import hungrycat.model.Cell;
import hungrycat.model.Food;
import hungrycat.model.FoodType;
import hungrycat.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class FoodTest {
    private Food testFood;

    @BeforeEach
    void setUp() {
        Cell cell = new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS);
        testFood = new Food(cell);
    }

    @Test
    void testConstructor() {
        assertEquals(new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS),
                testFood.getPosition());
    }

    @Test
    void testFoodTypeValue() {
        FoodType type = testFood.getType();

        switch (type) {
            case SS:
                assertEquals(20, testFood.getValue());
                break;
            case S:
                assertEquals(10, testFood.getValue());
                break;
            case A:
                assertEquals(5, testFood.getValue());
                break;
            case B:
                assertEquals(3, testFood.getValue());
                break;
            case C:
                assertEquals(1, testFood.getValue());
                break;
            default:
                fail("Food type is none of SS, S, A, B, C!");
                break;
        }

        System.out.println("Food is of type " + testFood.getType()
                + " and has value " + testFood.getValue() + ".");
    }
}
