package com.example.mineseeker.com.example.mineseeker.UI;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
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
    Button buttons[][]; // save buttons when creating
    TextView text;
    // keeps track of exlopsive cells
    TextView counterText;
    // create sound files
    MediaPlayer bombSound;
    MediaPlayer winSound;
    MediaPlayer laserSound;
    GameLogic logic = new GameLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gameBoard = GameBoard.getInstance();
        // change the state of the game board if it has been changed somewhere else
        gameBoard.getState(GameMenu.getContextApp());
        //create game logic after getting the state so that it can use the correct values
        // through out the app
        logic = new GameLogic();

        buttons = new Button[gameBoard.getNumRows()][gameBoard.getNumCol()];
        bombSound = MediaPlayer.create(this, R.raw.bomb_explosion);
        winSound = MediaPlayer.create(this,R.raw.wininng);
        laserSound = MediaPlayer.create(this, R.raw.laser);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setTitle("PLAY");

        TextView NbrOfBombsTxt = findViewById(R.id.txtBombsFound);
        NbrOfBombsTxt.setText("Bombs Found " + logic.getBombsFound() + " of " + gameBoard.getNumMines());

        counterText =findViewById(R.id.timesPlayed);

        updateDate();
        populateButtons();
    }


    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);
        for (int row = 0; row < gameBoard.getNumRows(); row++){
            TableRow tableRow = new TableRow(this);
            // control how it layouts
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    // how to match the parent Width and height
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f           // scaling weight of 1 so it knows how to scale
            ));
            table.addView(tableRow);

            for (int col = 0; col <  gameBoard.getNumCol(); col++){
                // to be used inside the anomnous class
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        // how to match the parent Width and height
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        // scaling weight of 1 so it knows how to scale
                        1.0f
                ));

                // make text not clip on small buttons
                button.setPadding(0,0,0,0);
                // creates anominous class
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // all buttons call same thing, make function
                        // cant use a variable that is outside of this class if it is not final
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
        //create random bombs with in the game board
        logic.makeRandomBombs();
        getData();
    }

    private void gridButtonClicked(int row, int col) {
        gameBoard = GameBoard.getInstance();
        logic.btnClicked(row,col);

        //update display txt on each click
        TextView scansTxt = findViewById(R.id.txtScansUsed);
        scansTxt.setText("Scans Used: " + logic.getScans());
        TextView NbrOfBombsTxt = findViewById(R.id.txtBombsFound);
        NbrOfBombsTxt.setText("Bombs Found " + logic.getBombsFound() +" of " + gameBoard.getNumMines());

        // sets the text for each button, the text should only be displayed if a scan has
        // been performed
        for(int i = 0; i < gameBoard.getNumRows();i++) {
            for(int j = 0; j < gameBoard.getNumCol();j++) {
                Button button = buttons[i][j];
                // the sound execution on a click checks if it should act as a scan first then moves
                // onto playing a bomb sound to stop both sounds from playing
                if(logic.getCellScanned(i, j)) {
                    laserSound.start();
                    button.setText("" + logic.getHiddenBombs(i, j));
                }
            }
        }
        // if the cell is a bomb display the image

        if(logic.getIsExplosive(row, col)) {
            //stops both sounds from playing
            if(!logic.getCellScanned(row,col)){
                bombSound.start();
            }
            displayBomb(buttons[row][col]);
            // check for win condition when a bomb is found
            if(logic.winCondition()) {
                for(int i = 0; i < gameBoard.getNumRows();i++) {
                    for(int j = 0; j < gameBoard.getNumCol();j++) {
                        Button button = buttons[i][j];
                        button.setText("" + logic.getHiddenBombs(i, j));
                    }
                }
                // connect fragment
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

    private void displayWinMessage() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager, "MessageDialog");
        winSound.start();
    }

    public static Intent makeIntentPlayGameActivity(Context context) {
        return new Intent(context, PlayGameActivity.class);
    }

    private void getData() {
        count ++;
        QueryPreferences.setStoredQuery(this,"keyPLAYS", count);
    }
    private void updateDate() {
        count = QueryPreferences.getStoredQuery(this, "keyPLAYS");
        counterText.setText("Times Played: " + count);
    }
}
