package com.example.mineseeker.com.example.mineseeker.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        SharedPreferences clearData = this.getActivity().getSharedPreferences("COUNT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = clearData.edit();


        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.clear().commit();
                Intent intent = GameMenu.makeIntentGameMenuActivity(getActivity());
                startActivity(intent);
                getActivity().finish();
            }
        };

        return new AlertDialog.Builder(getActivity()).setTitle("WARNING!")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}