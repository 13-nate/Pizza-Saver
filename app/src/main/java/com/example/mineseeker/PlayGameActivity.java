package com.example.mineseeker;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.util.Arrays;

public class PlayGameActivity extends AppCompatActivity {

    GameBoard gameBoard;
    int scans = 0;
    int bombsFound = 0;
    int count = 0;
    // save buttons when creating
    Button buttons[][];
    // keeps track of exlopsive cells
    boolean[][] isExplosive;
    boolean[][] bombIsShowing;
    boolean[][] cellScanned;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameBoard = GameBoard.getInstance();
        buttons = new Button[gameBoard.getNumRows()][gameBoard.getNumCol()];
        isExplosive = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        bombIsShowing  = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];
        cellScanned  = new boolean[gameBoard.getNumRows()][gameBoard.getNumCol()];


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setTitle("PLAY");



        TextView NbrOfBombsTxt = findViewById(R.id.txtBombsFound);
        NbrOfBombsTxt.setText("Bombs Found " + bombsFound +" of " + gameBoard.getNumMines());

        text =findViewById(R.id.timesPlayed);

        updateDate();
        populateButtons();

    }



    private void populateButtons() {
        //generate random sets of row/col pairs to be checked as buttons are generated
        // when a matched is found place a bomb on click
        gameBoard = GameBoard.getInstance();
        int[][]  bombs = new int [gameBoard.getNumMines()][2];
        //initilize to all false
        for (int row = 0; row < gameBoard.getNumRows(); row++){
            for (int col = 0; col <  gameBoard.getNumCol(); col++) {
                isExplosive[row][col] = false;
                bombIsShowing[row][col] = false;
                cellScanned[row][col] = false;

            }
        }

        for(int i = 0; i < gameBoard.getNumMines(); i++) {
            // minus one so that the random numbers max is the max index for the array
            int tempRow = (int)(Math.random()*((gameBoard.getNumRows()-1)+1));
            int tempCol = (int)(Math.random()*(( gameBoard.getNumCol()-1)+1));
            // check that the new random set of numbers is unique
            for (int k = 0; k < i; k++){
                if(tempRow == bombs[k][0] && tempCol == bombs[k][1]){
                    tempRow = (int)(Math.random()*((gameBoard.getNumRows()-1)+1));
                    tempCol = (int)(Math.random()*(( gameBoard.getNumCol()-1)+1));
                    // restart loop and look again with new numbers
                    k = -1;
                }
            }
            bombs[i][0] = tempRow;
            bombs[i][1]= tempCol;
        }

        Log.i("Cheats","" + Arrays.deepToString(bombs));

        TableLayout table = findViewById(R.id.tableForButtons);

        for (int row = 0; row < gameBoard.getNumRows(); row++){
            TableRow tableRow = new TableRow(this);
            //control how it layouts
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    //how to match the parent Width and height
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f           //scaleing weight of 1 so it knows how to scale
            ));
            table.addView(tableRow);

            for (int col = 0; col <  gameBoard.getNumCol(); col++){
                // to be used inside the anomnous class
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        //how to match the parent Width and height
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        //scaleing weight of 1 so it knows how to scale
                        1.0f
                ));

                // make text not clip on small buttons
                button.setPadding(0,0,0,0);
                //creates anominous class

                // check if one of the random number sets
                for(int i = 0; i < gameBoard.getNumMines(); i++) {
                    if (row == bombs[i][0] && col == bombs[i][1]) {
                        isExplosive[row][col] = true;
                    }
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // all buttons call same thing, make function
                        // cant use a varaible that is outside of this class if it is not final
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
        getData();
    }



    private void gridButtonClicked(int row, int col) {
        gameBoard = GameBoard.getInstance();
        /*Toast.makeText(this, "Button clicked: " + row + ", " + col,
                Toast.LENGTH_SHORT).show();*/


        //if it is a bomb show the bomb
        if(isExplosive[row][col]) {
            //stops increasing the number of bombs found by clicking the found bomb
            if(!bombIsShowing [row][col]){
                bombsFound++;
                TextView NbrOfBombsTxt = findViewById(R.id.txtBombsFound);
                NbrOfBombsTxt.setText("Bombs Found " + bombsFound +" of " + gameBoard.getNumMines());
            }


            // if the bomb is showing and clicked do a scan
            if(bombIsShowing[row][col]){
                scan(row,col);
            }

            //shows bomb
            Button button = buttons[row][col];
            //locks Button size
            lockButtonSizes();
            //scale image to button
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bomb);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
            //keeps track od showing bombs
            bombIsShowing[row][col] = true;


            // works for a bomb being found after a cell scanned
            //doesnt work for a new cell after a bomb is found

        //decrement  related cells when the button is reveild
            for(int i = 0; i < gameBoard.getNumRows(); i++){
                for(int j = 0; j <  gameBoard.getNumCol(); j++){
                    if(cellScanned[i][j] && (i == row || j == col)) {
                        Button btnScanned = buttons[i][j];
                        String btnText = btnScanned.getText().toString();
                        int btnNumber = Integer.parseInt(btnText);
                        // stops decrementing related cells once bomb is revield
                        if(!cellScanned[row][col]){
                            btnNumber--;
                        }

                        btnScanned.setText("" + btnNumber);
                    }
                }
            }
            // win condition met
            if(bombsFound == gameBoard.getNumMines()) {
                //all cells to show zero
                for (int i = 0; i < gameBoard.getNumRows(); i++) {
                    for (int j = 0; j <  gameBoard.getNumCol(); j++) {
                        Button btnScanned = buttons[i][j];
                        btnScanned.setText("" + 0);
                    }
                }
                //connect fragment
                displayWinMessage();
            }
            //not a bomb so scan row and col
        }
        else{
            scan(row, col);
        }
    }

    private void displayWinMessage() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager, "MessageDialog");
    }


    private void scan(int row, int col) {
        // stops multiple scans of the same cell
        if(!cellScanned[row][col]){
            scans++;
            TextView scansTxt = findViewById(R.id.txtScansUsed);
            scansTxt.setText("Scans Used: " + scans);
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
        Button button = buttons[row][col];
        button.setText(""+ countBombs);
    }

    private void lockButtonSizes() {
        gameBoard = GameBoard.getInstance();
        for (int row = 0; row < gameBoard.getNumRows(); row++) {
            for (int col = 0; col <  gameBoard.getNumCol(); col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }



    public static Intent makeIntentPlayGameActivity(Context context) {
        return new Intent(context, PlayGameActivity.class);
    }

    private void getData() {
        count ++;
        SharedPreferences preferences = getSharedPreferences("COUNT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("key", count);
        editor.commit();
    }
    private void updateDate() {
        SharedPreferences myScore = this.getSharedPreferences("COUNT", Context.MODE_PRIVATE);
        count = myScore.getInt("key", 0);
        text.setText("Times Played: " + count);
    }
}
