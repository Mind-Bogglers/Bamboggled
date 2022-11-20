package com.bamboggled.model.path;

import java.util.Objects;

/**
 * Class to hold Position information associated with a BoggleGrid
 */
public class Position {
    /**
     * row
     */    
    private int row;
    /**
     * column
     */    
    private int col;

    /**
     * A grid Position.
     * Sets row and column to 0, by default
     */
    public Position() {
        this.row = 0;
        this.col = 0;
    }

    /**
     * A grid Position.
     * 
     * @param row row
     * @param col column
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public void setRow(int row) { this.row = row; }
    public void setCol(int col) { this.col = col; }
    public int getRow() { return this.row; }
    public int getCol() { return this.col; }

    @Override
    public String toString() {
        return "(" + this.row + ", " + this.col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
