package com.example.mineseeker;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class GameLogic {
    private int scans;
    private int bombsFound;
    private int count;
    private String btnTxt;
    // save buttons when creating
    // keeps track of exlopsive cells
    private boolean[][] isExplosive;
    private boolean[][] bombIsShowing;
    private boolean[][] cellScanned;
    private int[][] bombs;

    GameBoard gameBoard;


    public GameLogic() {
        gameBoard =GameBoard.getInstance();
        isExplosive = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        bombIsShowing  = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        cellScanned  = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
    }

    public GameLogic(int scans, int bombsFound, int count, boolean[][] isExplosive,
                     boolean[][] bombIsShowing, boolean[][] cellScanned) {
        this.scans = scans;
        this.bombsFound = bombsFound;
        this.count = count;
        this.isExplosive = isExplosive;
        this.bombIsShowing = bombIsShowing;
        this.cellScanned = cellScanned;
    }

    public int getScans() {
        return scans;
    }

    public void setScans(int scans) {
        this.scans = scans;
    }

    public int getBombsFound() {
        return bombsFound;
    }

    public void setBombsFound(int bombsFound) {
        this.bombsFound = bombsFound;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsExplosive(int row, int col) {
        return isExplosive[row][col];
    }

    public void setIsExplosive(boolean[][] isExplosive) {
        this.isExplosive = isExplosive;
    }

    public boolean[][] getBombIsShowing() {
        return bombIsShowing;
    }

    public void setBombIsShowing(boolean[][] bombIsShowing) {
        this.bombIsShowing = bombIsShowing;
    }

    public boolean getCellScanned(int row, int col) {
        return cellScanned[row][col];
    }

    public void setCellScanned(boolean[][] cellScanned) {
        this.cellScanned = cellScanned;
    }

    public String getBtnTxt() {
        return btnTxt;
    }

    // sets the places for random bombs with in the the isExplosive 2D array
    public void makeRandomBombs() {
        gameBoard = GameBoard.getInstance();
        int[][] bombs = new int[gameBoard.getNumMines()][2];

        for (int i = 0; i < gameBoard.getNumMines(); i++) {
            // minus one so that the random numbers max is the max index for the array
            int tempRow = (int) (Math.random() * ((gameBoard.getNumRows() - 1) + 1));
            int tempCol = (int) (Math.random() * ((gameBoard.getNumCol() - 1) + 1));
            // check that the new random set of numbers is unique
            for (int k = 0; k < i; k++) {
                if (tempRow == bombs[k][0] && tempCol == bombs[k][1]) {
                    tempRow = (int) (Math.random() * ((gameBoard.getNumRows() - 1) + 1));
                    tempCol = (int) (Math.random() * ((gameBoard.getNumCol() - 1) + 1));
                    // restart loop and look again with new numbers
                    k = -1;
                }
            }
            bombs[i][0] = tempRow;
            bombs[i][1] = tempCol;
            isExplosive[tempRow][tempCol] = true;
            Log.i("Cheats","" + Arrays.deepToString(bombs));

        }
        // check if one of the random number sets
    }

    public void btnClicked(int row, int col) {
        if(isExplosive[row][col]) {
            bombFound(row,col);
        } else {
            scan(row, col);
        }
    }

    //when a bomb is clicked
    public void bombFound(int row, int col) {
        gameBoard = GameBoard.getInstance();
        /*Toast.makeText(this, "Button clicked: " + row + ", " + col,
                Toast.LENGTH_SHORT).show();*/

        if(bombIsShowing[row][col]){
            scan(row,col);
        }

        //if it is a bomb show the bomb
        if(isExplosive[row][col]) {
            //stops increasing the number of bombs found by clicking the found bomb
            if (!bombIsShowing[row][col]) {
                bombsFound++;
                //nothing to show on the button because it was not a scan
                btnTxt = "";
            }
            // if the bomb is showing and clicked do a scan
            //keeps track od showing bombs
            bombIsShowing[row][col] = true;

            //not a bomb so scan row and col
        }

    }

    public void scan(int row, int col) {
        // stops multiple scans of the same cell
        if(!cellScanned[row][col]){
            scans++;
        }

        int countBombs= 0;
        for (int i = 0; i < gameBoard.getNumRows(); i++){
            for (int j = 0; j <  gameBoard.getNumCol(); j++) {
                if(isExplosive[i][j] &&(i == row || j == col)){
                    countBombs++;
                }
                //cells act like they know a related bomb is showing
                if(bombIsShowing[i][j] && (i == row || j == col)){
                    countBombs --;
                }
            }
        }
        //keeps track of cells  scaned so thet can be changed once a bomb is revield
        cellScanned[row][col]= true;
        btnTxt = "" + countBombs;
    }

    public boolean winCondition(){
        gameBoard = GameBoard.getInstance();
        if(bombsFound == gameBoard.getNumMines()){
            return true;
        }
        return false;
    }

}


