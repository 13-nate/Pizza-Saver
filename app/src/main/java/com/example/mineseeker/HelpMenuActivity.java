package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HelpMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);
        getSupportActionBar().setTitle("HELP");
    }
}
