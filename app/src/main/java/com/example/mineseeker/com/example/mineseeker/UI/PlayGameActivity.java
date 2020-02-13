package com.example.mineseeker.com.example.mineseeker.UI;


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
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.mineseeker.model.GameBoard;
import com.example.mineseeker.model.GameLogic;
import com.example.mineseeker.R;

public class PlayGameActivity extends AppCompatActivity {

    GameBoard gameBoard;
    int count = 0;
    // save buttons when creating
    Button buttons[][];
    // keeps track of exlopsive cells
    TextView text;
    GameLogic logic = new GameLogic();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameBoard = GameBoard.getInstance();
        buttons = new Button[gameBoard.getNumRows()][gameBoard.getNumCol()];


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setTitle("PLAY");



        TextView NbrOfBombsTxt = findViewById(R.id.txtBombsFound);
        NbrOfBombsTxt.setText("Bombs Found " + logic.getBombsFound() + " of " + gameBoard.getNumMines());

        text =findViewById(R.id.timesPlayed);

        updateDate();
        populateButtons();

    }



    private void populateButtons() {
        //generate random sets of row/col pairs to be checked as buttons are generated
        // when a matched is found place a bomb on click

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
        logic.makeRandomBombs();
        getData();
    }



    private void gridButtonClicked(int row, int col) {
        gameBoard = GameBoard.getInstance();
        /*Toast.makeText(this, "Button clicked: " + row + ", " + col,
                Toast.LENGTH_SHORT).show();*/
        logic.btnClicked(row,col);



        //update display txt on each click
        TextView scansTxt = findViewById(R.id.txtScansUsed);
        scansTxt.setText("Scans Used: " + logic.getScans());
        TextView NbrOfBombsTxt = findViewById(R.id.txtBombsFound);
        NbrOfBombsTxt.setText("Bombs Found " + logic.getBombsFound() +" of " + gameBoard.getNumMines());

        for(int i = 0; i < gameBoard.getNumRows();i++) {
            for(int j = 0; j < gameBoard.getNumCol();j++) {
                Button button = buttons[i][j];
                if(logic.getCellScanned(i, j)) {
                    button.setText("" + logic.getHiddenBombs(i, j));
                }
            }
        }





        if(logic.getIsExplosive(row, col)) {
            displayBomb(buttons[row][col]);

            if(logic.winCondition()) {
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
        }



    }

    private void displayBomb(Button button1) {
        Button button = button1;
        //locks Button size
        lockButtonSizes();
        //scale image to button
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bomb);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));
    }

    private void displayWinMessage() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager, "MessageDialog");
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
