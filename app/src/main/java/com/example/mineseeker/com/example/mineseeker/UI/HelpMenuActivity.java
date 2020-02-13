package com.example.mineseeker.com.example.mineseeker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mineseeker.R;

public class HelpMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
        getSupportActionBar().setTitle("HELP");
    }
    public static Intent makeIntentHelpActivity(Context context){
        return new Intent(context, HelpMenuActivity.class);
    }
}
