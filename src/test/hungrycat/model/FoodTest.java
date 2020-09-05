package model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class FoodTest {
    private Food testFood;

    @Before
    public void setUp() {
        Cell cell = new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS);
        testFood = new Food(cell);
    }

    @Test
    public void testConstructor() {
        Assertions.assertThat(new Cell(Game.BOARD_ROWS / 2, Game.BOARD_COLS)).isEqualTo(testFood.getPosition());
    }

    @Test
    public void testFoodTypeValue() {
        FoodType type = testFood.getType();

        switch (type) {
            case BOMB:
                //
                break;
            case SLOW:
                //
                break;
            case SS:
                Assertions.assertThat(testFood.getValue()).isEqualTo(20);
                break;
            case S:
                Assertions.assertThat(testFood.getValue()).isEqualTo(10);
                break;
            case A:
                Assertions.assertThat(testFood.getValue()).isEqualTo(5);
                break;
            case B:
                Assertions.assertThat(testFood.getValue()).isEqualTo(3);
                break;
            case C:
                Assertions.assertThat(testFood.getValue()).isEqualTo(1);
                break;
            default:
                Assertions.fail("Food type is none of SS, S, A, B, C!");
                break;
        }

        System.out.println("Food has type " + testFood.getType() + " and value " + testFood.getValue());
    }
}
