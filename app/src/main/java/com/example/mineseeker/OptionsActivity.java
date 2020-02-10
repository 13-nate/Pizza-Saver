package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    // singleton support
    private GameBoard gameBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("OPTIONS");

        createGridRadioButtons();

        createNumberOfMinesRadioButton();
        //getSingleton
    }

    private void createNumberOfMinesRadioButton() {
        gameBoard = GameBoard.getInstance();

        RadioGroup groupMines =findViewById(R.id.radio_group_mines_number);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);
        for (int i = 0; i < numMines.length; i++){
            int numMine = numMines[i];



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


                    int cols;
                    if(row == '5'){
                        cols = 10;
                    }else if (row == '6') {
                        cols = 15;
                    } else {
                        cols = 6;
                    }

                    int rows = Integer.parseInt(String.valueOf(row));

                    gameBoard = GameBoard.getInstance();
                    gameBoard.setNumRows(rows);
                    gameBoard.setNumCol(cols);

                    Toast.makeText(OptionsActivity.this, "Selected rows " + rows + " and columns " + cols, Toast.LENGTH_SHORT).show();

                }
            });
            group.addView(button);

        }


    }
}
