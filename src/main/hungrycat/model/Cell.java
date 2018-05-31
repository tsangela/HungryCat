package hungrycat.model;

/**
 * Represents a cell on the game board.
 */
public class Cell {
    public static final int CELL_PIXELS = 20;

    private int row;
    private int col;

    /**
     * Creates a cell at the given row and col on the board.
     *
     * @param row   The row of the cell.
     * @param col   The column of the cell.
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of the cell.
     *
     * @return the row number of the cell.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the cell.
     *
     * @return the column number of the cell.
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the x-coordinate of the top-left corner of the cell.
     *
     * @return the x-coordinate of the top-left corner of the cell.
     */
    public int getTopLeftX() {
        return col * CELL_PIXELS;
    }

    /**
     * Returns the y-coordinate of the top-left corner of the cell.
     *
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