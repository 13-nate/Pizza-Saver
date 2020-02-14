package com.example.mineseeker.com.example.mineseeker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.mineseeker.R;

import java.util.regex.Pattern;

import static android.text.Html.fromHtml;

public class HelpMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
        getSupportActionBar().setTitle("HELP");

        TextView txtBomb = findViewById(R.id.txtBombImage);
        txtBomb.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtWinSound = findViewById(R.id.txtWinSound);
        txtWinSound.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtBombFoundSound = findViewById(R.id.txtBombFoundSound);
        txtBombFoundSound.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtScanSound = findViewById(R.id.txtScanSound);
        txtScanSound.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtAuthors = findViewById(R.id.txtAuthor);
        txtAuthors.setMovementMethod(LinkMovementMethod.getInstance());

    }
    public static Intent makeIntentHelpActivity(Context context){
        return new Intent(context, HelpMenuActivity.class);
    }
}
