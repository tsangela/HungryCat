package hungrycat.model;

/**
 * Represents the direction the cat faces.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public Direction getOppositeDirection() {
        switch (this) {
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return DOWN;
        }
    }
}
