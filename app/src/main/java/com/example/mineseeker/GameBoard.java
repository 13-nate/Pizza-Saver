package com.example.mineseeker;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private int numMines;
    private int numRows;
    private int numCol;

    // singleton support

    private static GameBoard instance;

    private GameBoard() {
        //private to prevent anyone else from extentianting
    }
    public static GameBoard getInstance() {
        if(instance == null){
            instance = new GameBoard();
        }
        return instance;
    }

    public int getNumMines() {
        //default values for start
        if(numMines == 0 ){
            numMines = 6;
        }
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public int getNumRows() {
        //default values for start
        if(numRows == 0 ){
            numRows = 4;
        }
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCol() {
        //default values for start
        if(numCol == 0 ){
            numCol = 6;
        }
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }
}
