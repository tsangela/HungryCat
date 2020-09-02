package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * Represents a cell on the game board.
 */
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cell {
    public static final int CELL_PIXELS = 20;

    int row;
    int col;

    /**
     * @return the x-coordinate of the top-left corner of the cell.
     */
    public int getTopLeftX() {
        return col * CELL_PIXELS;
    }

    /**
     * @return the y-coordinate of the top-left corner of the cell.
     */
    public int getTopLeftY() {
        return row * CELL_PIXELS;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Cell otherCell = (Cell) other;

        if (col != otherCell.col) return false;
        return row == otherCell.row;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

}