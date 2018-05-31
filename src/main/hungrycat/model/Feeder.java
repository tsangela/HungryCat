package hungrycat.model;

/**
 * Represents the feeder character.
 */
public class Feeder {
    private Cell      position;
    private Direction direction;
    private int       fullness;
    private boolean   canStep;

    /**
     * Creates a feeder at given position on screen, facing up, that has consumed no food.
     *
     * @param position  the position on the screen feeder is to be placed.
     */
    public Feeder(Cell position) {
        this.position = position;
        direction = Direction.UP;
        fullness = 0;
        canStep = true;
    }


    /**
     * Returns the position of the feeder.
     *
     * @return the current cell of the feeder.
     */
    public Cell getPosition() {
        return position;
    }


    /**
     * Returns the direction in which the feeder is facing.
     *
     * @return the current direction of the feeder.
     */
    public Direction getDirection() {
        return direction;
    }


    /**
     * Returns the fullness level of the feeder.
     *
     * @return an integer indicating the feeder's level of fullness.
     */
    public int getFullness() {
        return fullness;
    }

    /**
     * Rotates feeder to the given direction.
     */
    public void rotate(Direction d) {
        if (canStep) {
            direction = d;
            canStep = false;
        }
    }

    /**
     * Advance feeder one cell in the direction it is facing.
     */
    public void move() {
        switch(direction) {
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
     * Adds nutritional value of consumed food to fullness level of feeder.
     */
    public void eat(Food food) {
        fullness += food.getValue();
    }

}
