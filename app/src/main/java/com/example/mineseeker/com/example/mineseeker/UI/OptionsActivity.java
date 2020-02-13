package com.example.mineseeker.com.example.mineseeker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mineseeker.model.GameBoard;
import com.example.mineseeker.R;

public class OptionsActivity extends AppCompatActivity {

   public static Context contextApp;


    // singleton support
    private GameBoard gameBoard;
    private Button clear;
    int cols;
    int rows;
    int numMine;

    public static Context getContextApp() {
        return contextApp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("OPTIONS");

        createGridRadioButtons();

        createNumberOfMinesRadioButton();

        contextApp = getApplicationContext();

        clear = findViewById(R.id.clear_button);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                WarningFragment dialog = new WarningFragment();
                dialog.show(manager, "Message");
            }
        });

        //getSingleton
    }

    private void createNumberOfMinesRadioButton() {
        gameBoard = GameBoard.getInstance();

        RadioGroup groupMines =findViewById(R.id.radio_group_mines_number);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);
        for (int i = 0; i < numMines.length; i++){
            numMine = numMines[i];



            RadioButton button = new RadioButton(this);
            button.setText(numMine + " mines");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameBoard.setNumMines(numMine);
                    Toast.makeText(OptionsActivity.this, "Selected number of mines is: " + numMine, Toast.LENGTH_SHORT).show();

                }
            });
            groupMines.addView(button);
        }
    }

    private void createGridRadioButtons() {

//        getData();
        gameBoard = GameBoard.getInstance();

        RadioGroup group = findViewById(R.id.radio_group_grid_size);

        //create radiobuttons
        String[] numGrids = getResources().getStringArray(R.array.grid_dimensions);

        for (int i = 0; i < numGrids.length; i++){
            String numGrid = numGrids[i];

            //change txt to ints to be passed into singleton
            RadioButton button = new RadioButton(this);
            button.setText(numGrid);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(OptionsActivity.this, "Selected " + numGrid + " grid", Toast.LENGTH_SHORT).show();

                    //get the row and col from text
                    gameBoard = GameBoard.getInstance();
                    char row = numGrid.charAt(0);

                    if(row == '5'){
                        cols = 10;
                    }else if (row == '6') {
                        cols = 15;
                    } else {
                        cols = 6;
                    }

                    rows = Integer.parseInt(String.valueOf(row));
                    gameBoard = GameBoard.getInstance();
                    gameBoard.setNumRows(rows);
                    gameBoard.setNumCol(cols);

                    Toast.makeText(OptionsActivity.this, "Selected rows " + rows + " and columns " + cols, Toast.LENGTH_SHORT).show();
                    getData();
                }
            });
            group.addView(button);
        }
    }

    public static Intent makeIntentOptionsActivity(Context context){
        return new Intent(context, OptionsActivity.class);
    }
    private void getData() {
        SharedPreferences preferencesRows = getSharedPreferences("ROWS", Context.MODE_PRIVATE);
        SharedPreferences preferencesCols = getSharedPreferences("COLS", Context.MODE_PRIVATE);
        SharedPreferences preferencesMines = getSharedPreferences("MINES", Context.MODE_PRIVATE);

        SharedPreferences.Editor editorRow = preferencesRows.edit();
        SharedPreferences.Editor editorCols = preferencesCols.edit();
        SharedPreferences.Editor editorMines = preferencesMines.edit();

        editorRow.putInt("keyROWS", rows);
        editorCols.putInt("keyCOLS", cols);
        editorCols.putInt("keyMINES", numMine);

        editorRow.commit();
        editorCols.commit();
        editorMines.commit();
//        gameBoard.setContext(getApplicationContext());

    }
}
