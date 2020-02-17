package com.example.mineseeker.com.example.mineseeker.UI;

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

import com.example.mineseeker.R;

public class GameMenu extends AppCompatActivity {

    // ask him about the QueryPreference? model?
    //back button on Welcome Screen, and option activity after reset, stop the timer and/or animation.
    //stop from producing effects dynamic generated string for each instance.\


    private ImageView helpButton;
    private ImageView playGameButton;
    private ImageView optionButton;
    private ImageView explosionLogo;
    Animation menuAnimation;
    Animation explotionAnimation;


    // used the link below to help use shared preferences with material from the
    // Android Programming Big Nerd Ranch guide page: 533
    // https://stackoverflow.com/questions/7491287/android-how-to-use-sharedpreferences-in-non-activity-class
    public static Context contextApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextApp = getApplicationContext();
        setContentView(R.layout.activity_game__menu);
        startAnimation();
        playGameButton = findViewById(R.id.playGame_button);
        playGameButton.setOnClickListener(v -> {
            Intent intent = PlayGameActivity.makeIntentPlayGameActivity(GameMenu.this);
            startActivity(intent);
           finish();


        });


        optionButton = findViewById(R.id.options_button);
        optionButton.setOnClickListener(v -> {
           Intent intent = OptionsActivity.makeIntentOptionsActivity(GameMenu.this);
           startActivity(intent);
            finish();


        });

        helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(v -> {
            Intent intent = HelpMenuActivity.makeIntentHelpActivity(GameMenu.this);
            startActivity(intent);
            finish();


        });

    }

    public static Context getContextApp() {
        return contextApp;
    }

    private void startAnimation() {

        menuAnimation = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        explotionAnimation = AnimationUtils.loadAnimation(this,R.anim.explosion_logo_animation);

        explosionLogo = findViewById(R.id.explosionLogo_imageView);
        TextView menu = findViewById(R.id.mainMenu_textView);
        ImageView playButton = findViewById(R.id.playGame_button);
        ImageView optionButton = findViewById(R.id.options_button);
        ImageView helpButton = findViewById(R.id.help_button);
        menu.startAnimation(menuAnimation);
        playButton.startAnimation(menuAnimation);
        optionButton.startAnimation(menuAnimation);
        helpButton.startAnimation(menuAnimation);
        explosionLogo.startAnimation(explotionAnimation);

    }
    public static Intent makeIntentGameMenuActivity(Context context){
        return new Intent(context, GameMenu.class);
    }
}
