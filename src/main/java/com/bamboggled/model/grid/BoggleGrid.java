package com.bamboggled.model.grid;

/**
 * The BoggleGrid represents the grid on which Boggle is played.
 */
public class BoggleGrid {

    /**
     * Size of grid
     */  
    private int size;
    /**
     * Characters assigned to grid
     */      
    private char[][] board;

    /**
     * BoggleGrid constructor
     * @param size  The size of the Boggle grid to initialize
     */
    public BoggleGrid(int size) {
        this.size = size;
        this.board = new char[size][size];
    }

    /**
     * Assigns a letter in the string of letters provided to each grid position
     * Letters are assigned left to right, top to bottom
     * @param letters a string of letters, one for each grid position.
     */
    public void initalizeBoard(String letters) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = letters.charAt(i * this.size + j);
            }
        }
    }

    /**
     * Provide a string representation of the board.
     * @return String to print
     */
    @Override
    public String toString() {
        String boardString = "";
        for(int row = 0; row < this.size; row++){
            for(int col = 0; col < this.size; col++){
                boardString += this.board[row][col] + " ";
            }
            boardString += "\n";
        }
        return boardString;
    }

    /**
     * @return int the number of rows on the board
     */
    public int numRows() {
        return this.size;
    }

    /**
     * @return int the number of columns on the board (assumes square grid)
     */
    public int numCols() {
        return this.size;
    }

    /**
     * @return char the character at a given grid position
     */
    public char getCharAt(int row, int col) {
        return this.board[row][col];
    }

}
