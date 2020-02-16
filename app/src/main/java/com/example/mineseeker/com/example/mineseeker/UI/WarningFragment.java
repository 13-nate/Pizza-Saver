package com.example.mineseeker.com.example.mineseeker.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mineseeker.R;
import com.example.mineseeker.model.GameBoard;

public class WarningFragment extends AppCompatDialogFragment {
    GameBoard gameBoard = GameBoard.getInstance();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.clear_warning_message,null );
        SharedPreferences clearData = PreferenceManager.getDefaultSharedPreferences(GameMenu.getContextApp());
        SharedPreferences.Editor editor = clearData.edit();
        String boardSettings = "" + gameBoard.getNumRows()+gameBoard.getNumCol()+gameBoard.getNumMines();
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        editor.remove("keyPLAYS");
                        editor.remove(boardSettings);

                        editor.apply();
                        Intent intent = GameMenu.makeIntentGameMenuActivity(getActivity());
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        return new AlertDialog.Builder(getActivity()).setTitle("WARNING!")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }

}