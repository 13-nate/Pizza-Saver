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


public class WarningFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.clear_warning_message,null );
        SharedPreferences clearData = PreferenceManager.getDefaultSharedPreferences(GameMenu.getContextApp());
        SharedPreferences.Editor editor = clearData.edit();
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    editor.remove("keyPLAYS");
                    editor.remove("466");
                    editor.remove("4610");
                    editor.remove("4615");
                    editor.remove("4620");
                    editor.remove("5106");
                    editor.remove("51010");
                    editor.remove("51015");
                    editor.remove("51020");
                    editor.remove("6156");
                    editor.remove("61510");
                    editor.remove("61515");
                    editor.remove("61520");
                    editor.apply();

                    Intent intent = GameMenu.makeIntentGameMenuActivity(getActivity());
                    startActivity(intent);
                    getActivity().finish();

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        return new AlertDialog.Builder(getActivity()).setTitle(R.string.warning)
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }

}
