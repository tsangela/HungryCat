package hungrycat.model;

/**
 * Represents the cat character.
 */
public class Cat {
    private Cell      position;
    private Direction direction;
    private int       fullness;
    private boolean   canStep;

    /**
     * Creates a cat at given position on screen, facing up, that has consumed no food.
     *
     * @param position  the position on the screen cat is to be placed.
     */
    public Cat(Cell position) {
        this.position = position;
        direction = Direction.UP;
        fullness = 0;
        canStep = true;
    }


    /**
     * Returns the position of the cat.
     *
     * @return the current cell of the cat.
     */
    public Cell getPosition() {
        return position;
    }


    /**
     * Returns the direction in which the cat is facing.
     *
     * @return the current direction of the cat.
     */
    public Direction getDirection() {
        return direction;
    }


    /**
     * Returns the fullness level of the cat.
     *
     * @return an integer indicating the cat's level of fullness.
     */
    public int getFullness() {
        return fullness;
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
     * Adds nutritional value of consumed food to fullness level of cat.
     */
    public void eat(Food food) {
        fullness += food.getValue();
    }

}
