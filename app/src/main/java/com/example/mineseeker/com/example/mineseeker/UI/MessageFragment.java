package com.example.mineseeker.com.example.mineseeker.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mineseeker.R;

public class MessageFragment extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_message,null );

        DialogInterface.OnClickListener listener = (dialog, which) -> {
            Intent intent = GameMenu.makeIntentGameMenuActivity(getActivity());
            startActivity(intent);
            getActivity().finish();

        };

        return new AlertDialog.Builder(getActivity()).setTitle("GAME OVER")
                .setMessage("Congratulations You won!")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}