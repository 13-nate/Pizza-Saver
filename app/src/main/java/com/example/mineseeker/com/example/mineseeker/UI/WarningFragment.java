package com.example.mineseeker.com.example.mineseeker.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mineseeker.R;

public class WarningFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.clear_warning_message,null );
        SharedPreferences clearData = PreferenceManager.getDefaultSharedPreferences(GameMenu.getContextApp());
        SharedPreferences.Editor editor = clearData.edit();

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        editor.remove("keyPLAYS");
                        editor.remove("4x6_6mines");
                        editor.remove("4x6_10mines");
                        editor.remove("4x6_15mines");
                        editor.remove("4x6_20mines");
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