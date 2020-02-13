package com.example.mineseeker.model;

/**
 * Game board keeps track of the basic states of the game like its rows, collumns and number of
 * bombs, it also impolemtes a singleton to keep the data consistent
 */
public class GameBoard {
    // start up values for the first play of the game
    public static final int NUM_MINES = 6;
    public static final int NUM_ROWS = 4;
    public static final int NUM_COL = 6;
    private int numMines;
    private int numRows;
    private int numCol;

    // singleton support
    private static GameBoard instance;

    // default values for start
    private GameBoard() {
        //default values
       this.numMines = NUM_MINES;
       this.numRows = NUM_ROWS;
       this.numCol = NUM_COL;
    }
    public static GameBoard getInstance() {
        if(instance == null){
            instance = new GameBoard();
        }
        return instance;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCol() {

        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }
}
