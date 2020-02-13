package com.example.mineseeker.model;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.mineseeker.com.example.mineseeker.UI.OptionsActivity;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Game board keeps track of the basic states of the game like its rows, collumns and number of
 * bombs, it also impolemtes a singleton to keep the data consistent
 */
public class GameBoard {
    // start up values for the first play of the game
    int MINES = 6;
    int COLS = 6;
    int ROWS = 4;
    private int numMines;
    private int numRows;
    private int numCol;
    private Context context;
    // singleton support
    private static GameBoard instance;

    // default values for start
    private GameBoard() {
        //default or saved values frim option screen
        /*SharedPreferences setRows = context.getSharedPreferences("ROWS",Context.MODE_PRIVATE);
        SharedPreferences setCols = context.getSharedPreferences("COLS",Context.MODE_PRIVATE);
        SharedPreferences setMines = context.getSharedPreferences("MINES",Context.MODE_PRIVATE);

        numRows = setRows.getInt("keyROWS", 6);
        numCol = setCols.getInt("keyCOLS", 4);
        numMines = setMines.getInt("keyMINES", 6);*/
        this.numRows = ROWS;
        this.numCol = COLS;
        this.numMines = MINES;
    }
    public static GameBoard getInstance() {
        if(instance == null){
            instance = new GameBoard();
        }
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
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

    private void updateDate() {
        Context context = OptionsActivity.getContextApp();
        SharedPreferences prefs = getDefaultSharedPreferences(context);
        numRows = prefs.getInt("ROWS", 0);
        numCol = prefs.getInt("COLS", 0);
        numMines = prefs.getInt("ROWS", 0);


    }
}
