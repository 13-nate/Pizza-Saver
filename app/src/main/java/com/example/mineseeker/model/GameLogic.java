package com.example.mineseeker.model;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.mineseeker.com.example.mineseeker.UI.GameMenu;
import com.example.mineseeker.com.example.mineseeker.UI.PlayGameActivity;
import com.example.mineseeker.com.example.mineseeker.UI.QueryPreferences;

import java.util.Arrays;

/**
 * Game Logic keeps track of teh differnt cells states, updating where the bombs are and how many
 * bombs are in hidden in the related row and collumn to each cell as the game is played
 */

public class GameLogic {
    private int scans;
    private int bombsFound;
    private String boardSettings;

    //arrays keep track of the different cell states
    //which cells have a bombs
    private boolean[][] isExplosive;
    // if that cells bomb has been found, so that a scan can be performed
    private boolean[][] isBombFound;
    private boolean[][] isCellScanned;
    // keeps track of the number of hidden bombs in the row and column related to each cell
    private int[][] hiddenBombs;
    private GameBoard gameBoard;

    //initialize arrays and other game board data
    public GameLogic() {
        gameBoard = GameBoard.getInstance();
        isExplosive = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        isBombFound = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        isCellScanned = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        hiddenBombs  = new int[gameBoard.getNumRows()][gameBoard.getNumCol()];

        for (int row = 0; row < gameBoard.getNumRows(); row++){
            for (int col = 0; col <  gameBoard.getNumCol(); col++) {
                isExplosive[row][col] = false;
                isBombFound[row][col] = false;
                isCellScanned[row][col] = false;
                hiddenBombs[row][col] = 0;
            }
        }

        boardSettings ="" + gameBoard.getNumCol() + gameBoard.getNumCol() + gameBoard.getNumMines();
    }

    public int getScans() {
        return scans;
    }

    public int getBombsFound() {
        return bombsFound;
    }

    public boolean getIsExplosive(int row, int col) {
        return isExplosive[row][col];
    }

    public boolean getCellScanned(int row, int col) {
        return isCellScanned[row][col];
    }

    public int getHiddenBombs(int row, int col) {
        return  hiddenBombs[row][col];
    }

    public boolean getIsBombFound(int row, int col) {
        return isBombFound[row][col];
    }

    // sets the places for random bombs with in the the isExplosive 2D array
    // sets the number of hidden bombs related to each cell's row and column
    public void makeRandomBombs() {
        gameBoard = GameBoard.getInstance();
        //bombs is used to hold all the bombs places and make sure they are unique
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
            // sets the number of hidden bombs related to each cell's row and column
            for(int row = 0; row < gameBoard.getNumRows(); row++) {
                for(int col = 0; col < gameBoard.getNumCol(); col++) {
                    if(row == tempRow || col == tempCol) {
                        hiddenBombs[row][col]++;
                    }
                }
            }
        }
        Log.i("Cheats","" + Arrays.deepToString(bombs));
        Log.i("hidden","" + Arrays.deepToString(hiddenBombs));

    }
    // two paths based on the cell's data, it has a bomb or else it doesn't, perform functions
    // accordingly
    public void btnClicked(int row, int col) {
        if(isExplosive[row][col]) {
            bombClicked(row,col);
        } else {
            scan(row, col);
        }
    }
    public void bombClicked(int row, int col) {
        gameBoard = GameBoard.getInstance();
        if(isBombFound[row][col]){
            scan(row,col);
        } else {
            bombsFound++;
            for (int i = 0; i < gameBoard.getNumRows(); i++) {
                for (int j = 0; j < gameBoard.getNumCol(); j++) {
                    // when a bomb is found decrement the hiddenBombs related to this row and column
                    if ((i == row || j == col)) {
                        hiddenBombs[i][j]--;
                    }
                }
            }
            isBombFound[row][col] = true;
        }
    }
    // keeps track of the number of scans and changes the isCellScanned to be true, so that
    // cells that have been scanned can be updated to display the correct number
    public void scan(int row, int col) {
        if(!isCellScanned[row][col]){
            scans++;
        }
        //keeps track of cells  scaned so thet can be changed once a bomb is revield
        isCellScanned[row][col]= true;
    }
    // change all hiddenBombs to be zero and return true
    public boolean winCondition(){
        gameBoard = GameBoard.getInstance();
        if(bombsFound == gameBoard.getNumMines()) {
            for (int i = 0; i < gameBoard.getNumRows(); i++) {
                for (int j = 0; j < gameBoard.getNumCol(); j++) {
                    hiddenBombs[i][j] = 0;
                }
            }
            String boardSettings = "" + gameBoard.getNumRows() + gameBoard.getNumCol()
                    + gameBoard.getNumMines();
            int highScore = QueryPreferences.getStoredQuery(GameMenu.getContextApp(), boardSettings);

            if(highScore == 0) {
                QueryPreferences.setStoredQuery(GameMenu.getContextApp(), boardSettings, scans);
            } else if(scans < highScore) {
                QueryPreferences.setStoredQuery(GameMenu.getContextApp(), boardSettings, scans);
            }
            return true;
        }
        return false;
    }


}


