package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Game_Menu extends AppCompatActivity {

    private Button helpButton;

    private Button playGameButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__menu);

        startAnimation();
        helpButton = findViewById(R.id.help_button);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_Menu.this, HelpMenuActivity.class);
                startActivity(intent);
            }
        });

        playGameButton = findViewById(R.id.playGame_button);

        playGameButton.setOnClickListener(view -> {
            Intent intent = PlayGameActivity.makeIntentPlayGameActivity(Game_Menu.this);
            startActivity(intent);
        });


    }

    private void startAnimation() {

        Animation menuAnimation = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        TextView menu = findViewById(R.id.mainMenu_textView);
        Button playButton = findViewById(R.id.playGame_button);
        Button optionButton = findViewById(R.id.options_button);
        Button helpButton = findViewById(R.id.help_button);
        menu.startAnimation(menuAnimation);
        playButton.startAnimation(menuAnimation);
        optionButton.startAnimation(menuAnimation);
        helpButton.startAnimation(menuAnimation);
    }
}
