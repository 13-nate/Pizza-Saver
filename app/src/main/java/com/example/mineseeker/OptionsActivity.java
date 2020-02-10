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

        RadioGroup groupMines =findViewById(R.id.radio_group_mines_number);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);
        for (int i = 0; i < numMines.length; i++){
            int numMine = numMines[i];

            gameBoard = GameBoard.getInstance();
            gameBoard.setNumMines(numMine);
            RadioButton button = new RadioButton(this);
            button.setText(numMine + " mines");
            groupMines.addView(button);
        }
    }

    private void createGridRadioButtons() {

        RadioGroup group = findViewById(R.id.radio_group_grid_size);

        //create radiobuttons
        String[] numGrids = getResources().getStringArray(R.array.grid_dimensions);

        for (int i = 0; i < numGrids.length; i++){
            String numGrid = numGrids[i];

            //get the row and col from text
            char rows = numGrid.charAt(0);
            char colls = numGrid.charAt(4);

            gameBoard = GameBoard.getInstance();
            gameBoard.setNumRows(rows);
            gameBoard.setNumCol(colls);

            //change txt to ints to be passed into singleton


            RadioButton button = new RadioButton(this);
            button.setText(numGrid);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(OptionsActivity.this, "Selected " + numGrid + " grid", Toast.LENGTH_SHORT).show();
                }
            });
            group.addView(button);

        }


    }
}
