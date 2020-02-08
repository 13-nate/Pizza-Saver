package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("OPTIONS");

        createGridRadioButtons();

        createNumberOfMinesRadioButton();
    }

    private void createNumberOfMinesRadioButton() {

        RadioGroup groupMines =findViewById(R.id.radio_group_mines_number);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);
        for (int i = 0; i < numMines.length; i++){
            int numMine = numMines[i];

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
