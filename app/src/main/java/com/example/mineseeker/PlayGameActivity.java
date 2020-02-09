package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Arrays;

public class PlayGameActivity extends AppCompatActivity {

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 5;
    private static final int NUM_BOMBS = 5;


    // save buttons when creating
    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];
    // keeps track of exlopsive cells
    boolean[][] isExplosive = new boolean[NUM_ROWS][NUM_COLS];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setTitle("PLAY");

        populateButtons();

    }
    private void populateButtons() {
        //generate random sets of row/col pairs to be checked as buttons are generated
        // when a matched is found place a bomb on click
        int[][]  bombs = new int [NUM_BOMBS][2];
        //initilize to all false
        for (int row = 0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++) {
                isExplosive[row][col] = false;

            }
        }

        for(int i = 0; i < NUM_BOMBS; i++) {
            // minus one so that the random numbers max is the max index for the array
            int tempRow = (int)(Math.random()*((NUM_ROWS-1)+1));
            int tempCol = (int)(Math.random()*((NUM_COLS-1)+1));
            // check that the new random set of numbers is unique
            for (int k = 0; k < i; k++){
                if(tempRow == bombs[k][0] && tempCol == bombs[k][1]){
                    tempRow = (int)(Math.random()*((NUM_ROWS-1)+1));
                    tempCol = (int)(Math.random()*((NUM_COLS-1)+1));
                    // restart loop and look again with new numbers
                    k = -1;
                }
            }
            bombs[i][0] = tempRow;
            bombs[i][1]= tempCol;
        }

        // makes sure there is no duplicates
        boolean noDuplicates = false;

        for (int i = 0; i < NUM_BOMBS; i++) {
            int tempRow = bombs[i][0];
            int tempCol = bombs[i][1];
            // find a duplicate radomize again
            int k = i + 1;
            if (k == NUM_BOMBS) {
                k--;
            } boolean isSameCol = true;
            while(isSameCol) {
                if (tempRow == bombs[k][0] && tempCol == bombs[k][1]) {
                    bombs[i][1] = (int) (Math.random() * ((NUM_COLS - 1) + 1));
                }
                isSameCol = false;
            }
        }


        Log.i("Cheats","" + Arrays.deepToString(bombs));

        TableLayout table = findViewById(R.id.tableForButtons);

        for (int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            //control how it layouts
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    //how to match the parent Width and height
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f           //scaleing weight of 1 so it knows how to scale
            ));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++){
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
                for(int i = 0; i < NUM_BOMBS; i++) {
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
    }

    private void gridButtonClicked(int row, int col) {
        Toast.makeText(this, "Button clicked: " + row + ", " + col,
                Toast.LENGTH_SHORT).show();

        if(isExplosive[row][col]) {
            Button button = buttons[row][col];
            //does not scale button
            //button.setBackgroundResource(R.drawable.baby_yoda);
            //lock Button size
            lockButtonSizes();
            //scale image to button
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bomb);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
            
        }else{
            scan(row, col);
        }
    }

    private void scan(int row, int col) {
        int countBombs= 0;
        for (int i = 0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++) {
                // isExplosize is a bool already,
                if(isExplosive[i][j] &&(i == row || j == col)){
                    countBombs++;
                }
            }
        }
        Button button = buttons[row][col];
        button.setText(""+ countBombs);
    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
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
}
