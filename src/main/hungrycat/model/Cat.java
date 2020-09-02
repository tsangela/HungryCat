package model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Represents the cat character.
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cat {
    Cell position;  // Position of the cat
    Direction direction; // Direction in which the cat is facing
    int fullness;  // Fullness level of the cat
    boolean canStep;
    int level;
    int deceleration;

    /**
     * Creates a cat at given position on screen, facing up, that has consumed no food.
     *
     * @param position the position on the screen cat is to be placed.
     */
    public Cat(Cell position) {
        this.position = position;
        direction = Direction.UP;
        fullness = 0;
        canStep = true;
        level = 0;
        deceleration = 0;
    }

    /**
     * Rotates cat to the given direction.
     */
    public void rotate(Direction d) {
        if (canStep) {
            direction = d;
            canStep = false;
        }
    }

    /**
     * Advance cat one cell in the direction it is facing.
     */
    public void move() {
        switch (direction) {
            case UP:
                position = new Cell(position.getRow() - 1, position.getCol());
                break;
            case DOWN:
                position = new Cell(position.getRow() + 1, position.getCol());
                break;
            case LEFT:
                position = new Cell(position.getRow(), position.getCol() - 1);
                break;
            case RIGHT:
                position = new Cell(position.getRow(), position.getCol() + 1);
                break;
        }
        canStep = true;
    }

    /**
     * Adds nutritional value of consumed food to fullness level of cat.
     */
    void eat(Food food) {
        fullness += food.getValue();
        deceleration += food.getDeceleration();
        // ++level;
    }

    void levelUp() {
        ++level;
    }

//    public void decelerate() {
//        deceleration += food.
//    }

}
