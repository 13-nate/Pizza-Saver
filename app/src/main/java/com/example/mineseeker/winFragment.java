package com.example.mineseeker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class winFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //create view to show
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.win_message_layout, null);

        //create a button listner
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), Game_Menu.class);
                startActivity(intent);
                getActivity().finish();
            }
        };
        //build the alert dialog
        return  new AlertDialog.Builder(getActivity())
                .setTitle("YOU WON!")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}
