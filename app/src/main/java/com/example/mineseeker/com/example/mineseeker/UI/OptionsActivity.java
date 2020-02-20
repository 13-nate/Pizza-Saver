package com.example.mineseeker.com.example.mineseeker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mineseeker.model.GameBoard;
import com.example.mineseeker.R;

public class OptionsActivity extends AppCompatActivity {

    // singleton support
    private GameBoard gameBoard;
    private Button clear;
    int cols;
    int rows;
    int setMines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle(R.string.options);

        createGridRadioButtons();
        createNumberOfMinesRadioButton();
        clear = findViewById(R.id.clear_button);
        clear.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            WarningFragment dialog = new WarningFragment();
            dialog.show(manager, "Message");
        });

        //getSingleton
    }

    private void createNumberOfMinesRadioButton() {
        gameBoard = GameBoard.getInstance();

        RadioGroup groupMines =findViewById(R.id.radio_group_mines_number);
        int[] numMines = getResources().getIntArray(R.array.number_of_mines);
        for (int i = 0; i < numMines.length; i++){
            int numMine = numMines[i];



            RadioButton button = new RadioButton(this);
            String mines = getString(R.string.mines_chosen);
            button.setText("" + numMine + mines);
            button.setTextColor(Color.parseColor("#FFFFFF"));

            //taken from
            //https://stackoverflow.com/questions/17120199/change-circle-color-of-radio-button
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{

                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled} //enabled
                    },
                    new int[] {

                            Color.BLACK //disabled
                            ,Color.WHITE //enabled

                    }
            );

            button.setButtonTintList(colorStateList);//set the color tint list
            button.setOnClickListener(v -> {
                setMines = numMine;
                gameBoard.setNumMines(setMines);
                int index = groupMines.indexOfChild(findViewById(groupMines.getCheckedRadioButtonId()));
                QueryPreferences.setStoredQuery(this,"keyNUMMINESSELECTED", index);
                getData();
            });
            groupMines.addView(button);
        }
        int indexSelected  = QueryPreferences.getStoredQuery(this,"keyNUMMINESSELECTED");
        ((RadioButton)groupMines.getChildAt(indexSelected)).setChecked(true);
    }

    private void createGridRadioButtons() {
        gameBoard = GameBoard.getInstance();

        RadioGroup groupNumGrid = findViewById(R.id.radio_group_grid_size);

        //create radiobuttons
        String[] numGrids = getResources().getStringArray(R.array.grid_dimensions);

        for (int i = 0; i < numGrids.length; i++){
            String numGrid = numGrids[i];

            //change txt to ints to be passed into singleton
            RadioButton button = new RadioButton(this);
            button.setText(numGrid);
            button.setTextColor(Color.parseColor("#FFFFFF"));

            //taken from
            //https://stackoverflow.com/questions/17120199/change-circle-color-of-radio-button
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{

                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled} //enabled
                    },
                    new int[] {

                            Color.BLACK //disabled
                            ,Color.WHITE //enabled

                    }
            );

            button.setButtonTintList(colorStateList);//set the color tint list
            button.invalidate(); //could not be necessary
            button.setOnClickListener(v -> {

                //get the row and col from text
                char row = numGrid.charAt(0);

                if(row == '5'){
                    cols = 10;
                }else if (row == '6') {
                    cols = 15;
                } else {
                    cols = 6;
                }
                int index = groupNumGrid.indexOfChild(findViewById(groupNumGrid.getCheckedRadioButtonId()));
                QueryPreferences.setStoredQuery(this,"keyNUMGRIDSELECTED", index);
                rows = Integer.parseInt(String.valueOf(row));
                gameBoard = GameBoard.getInstance();
                gameBoard.setNumRows(rows);
                gameBoard.setNumCol(cols);
                getData();
            });
            groupNumGrid.addView(button);
        }
        int indexSelected  = QueryPreferences.getStoredQuery(this,"keyNUMGRIDSELECTED");
        ((RadioButton)groupNumGrid.getChildAt(indexSelected)).setChecked(true);
    }

    public static Intent makeIntentOptionsActivity(Context context){
        return new Intent(context, OptionsActivity.class);
    }
    private void getData() {
        gameBoard = GameBoard.getInstance();
        QueryPreferences.setStoredQuery(GameMenu.getContextApp(),"keyROWS", rows);
        QueryPreferences.setStoredQuery(GameMenu.getContextApp(), "keyCOLS", cols);
        QueryPreferences.setStoredQuery(GameMenu.getContextApp(),"keyMINES", setMines);
        gameBoard.setState(this);
    }
    public void onBackPressed() {
        Intent intent = GameMenu.makeIntentGameMenuActivity(OptionsActivity.this);
        startActivity(intent);
        super.onBackPressed();
        finish();
    }
}
