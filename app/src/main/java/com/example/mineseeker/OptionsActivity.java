package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("OPTIONS");

        createGridRadioButtons();

    }

    private void createGridRadioButtons() {

        RadioGroup group = findViewById(R.id.radio_group_grid_size);

        //create radiobuttons
        String[] numGrids = getResources().getStringArray(R.array.grid_dimensions);

        for (int i = 0; i < numGrids.length; i++){
            String numGrid = numGrids[i];

            RadioButton button = new RadioButton(this);
            button.setText(numGrid);
            group.addView(button);

        }


    }
}
