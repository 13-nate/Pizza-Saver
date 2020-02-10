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
