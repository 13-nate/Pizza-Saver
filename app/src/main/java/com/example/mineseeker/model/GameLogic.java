package com.example.mineseeker.model;

import android.util.Log;

import com.example.mineseeker.model.GameBoard;

import java.util.Arrays;

/**
 * Game Logic keeps track of teh differnt cells states, updating where the bombs are and how many
 * bombs are in hidden in the related row and collumn to each cell as the game is played
 */

public class GameLogic {

    //
    private int scans;
    private int bombsFound;

    //arrays keep track of the different cell states
    private boolean[][] isExplosive;
    private boolean[][] isBombShowing;
    private boolean[][] isCellScanned;
    private int[][] hiddenBombs;
    GameBoard gameBoard;


    public GameLogic() {
        gameBoard =GameBoard.getInstance();
        isExplosive = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        isBombShowing = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        isCellScanned = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        hiddenBombs  = new int[gameBoard.getNumRows()][gameBoard.getNumCol()];

        for (int row = 0; row < gameBoard.getNumRows(); row++){
            for (int col = 0; col <  gameBoard.getNumCol(); col++) {
                isExplosive[row][col] = false;
                isBombShowing[row][col] = false;
                isCellScanned[row][col] = false;
                hiddenBombs[row][col] = 0;
            }
        }


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
            //for each temp row and temp coll go thow and increment the number of hidden bombs
            // that relate to each temp row or coll
            for(int row = 0; row < gameBoard.getNumRows(); row++) {
                for(int col = 0; col < gameBoard.getNumCol(); col++) {
                    if(row == tempRow || col == tempCol) {
                        hiddenBombs[row][col]++;
                    }
                }
            }
        }
            Log.i("Cheats","" + Arrays.deepToString(bombs));
        //for each cell go through all  other cells and increment the cell within the
        // first loop for each bomb found related to it
        //maybe make simpler by incrementing all rows and cols related to the temp row and col
           /* for (int row = 0; row < gameBoard.getNumRows(); row++) {
                for (int col = 0; col < gameBoard.getNumCol(); col++) {
                    for (int r = 0; r < gameBoard.getNumRows(); r++) {
                        for (int c = 0; c < gameBoard.getNumCol(); c++) {
                            if (isExplosive[r][c] && (r == row || c == col)) {
                                hiddenBombs[row][col]++;
                            }
                        }
                    }
                }
            }*/
        Log.i("hidden","" + Arrays.deepToString(hiddenBombs));
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

        if(isBombShowing[row][col]){
            scan(row,col);
        }

        //if it is a bomb show the bomb
        if(isExplosive[row][col]) {
            //stops increasing the number of bombs found by clicking the found bomb
            if (!isBombShowing[row][col]) {
                bombsFound++;
                for (int i = 0; i < gameBoard.getNumRows(); i++) {
                    for (int j = 0; j < gameBoard.getNumCol(); j++) {
                        //decrement
                        if ((i == row || j == col)) {
                            hiddenBombs[i][j]--;
                        }
                    }
                }            }
            // if the bomb is showing and clicked do a scan
            //keeps track od showing bombs

            isBombShowing[row][col] = true;
        }
    }

    public void scan(int row, int col) {
        // stops multiple scans of the same cell
        if(!isCellScanned[row][col]){
            scans++;
        }
        for (int i = 0; i < gameBoard.getNumRows(); i++){
            for (int j = 0; j <  gameBoard.getNumCol(); j++) {
                //cells act like they know a related bomb is showing
                /*if(isBombShowing[i][j] && (i == row || j == col)){
                    hiddenBombs[row][col] --;
                }*/
            }
        }
        //keeps track of cells  scaned so thet can be changed once a bomb is revield
        isCellScanned[row][col]= true;
    }

    public boolean winCondition(){
        gameBoard = GameBoard.getInstance();
        if(bombsFound == gameBoard.getNumMines()){
            return true;
        }
        return false;
    }

}


