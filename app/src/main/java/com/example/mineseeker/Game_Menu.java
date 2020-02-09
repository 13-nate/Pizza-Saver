package com.example.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Game_Menu extends AppCompatActivity {

    private Button helpButton;
    private Button playGameButton;
    private Button optionButton;
    private ImageView explosionLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__menu);
        startAnimation();

        playGameButton = findViewById(R.id.playGame_button);
        playGameButton.setOnClickListener(view -> {
            Intent intent = PlayGameActivity.makeIntentPlayGameActivity(Game_Menu.this);
            startActivity(intent);
            finish();

        });

        optionButton = findViewById(R.id.options_button);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_Menu.this, OptionsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_Menu.this, HelpMenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void startAnimation() {

        Animation menuAnimation = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        Animation explotionAnimation = AnimationUtils.loadAnimation(this,R.anim.explosion_logo_animation);

        explosionLogo = findViewById(R.id.explosionLogo_imageView);
        TextView menu = findViewById(R.id.mainMenu_textView);
        Button playButton = findViewById(R.id.playGame_button);
        Button optionButton = findViewById(R.id.options_button);
        Button helpButton = findViewById(R.id.help_button);

        menu.startAnimation(menuAnimation);
        playButton.startAnimation(menuAnimation);
        optionButton.startAnimation(menuAnimation);
        helpButton.startAnimation(menuAnimation);
        explosionLogo.startAnimation(explotionAnimation);
    }

    public static Intent makeIntentGame_Menue(Context context) {
        return new Intent(context, PlayGameActivity.class);
    }
}
