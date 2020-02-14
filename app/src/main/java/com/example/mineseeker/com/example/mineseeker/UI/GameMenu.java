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

    private Button helpButton;
    private Button playGameButton;
    private Button optionButton;
    private ImageView explosionLogo;

    public static Context contextApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextApp = getApplicationContext();

        setContentView(R.layout.activity_game__menu);
        startAnimation();

        playGameButton = findViewById(R.id.playGame_button);
        playGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PlayGameActivity.makeIntentPlayGameActivity(GameMenu.this);
                startActivity(intent);
            }
        });


        optionButton = findViewById(R.id.options_button);
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = OptionsActivity.makeIntentOptionsActivity(GameMenu.this);
               startActivity(intent);
            }
        });

        helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = HelpMenuActivity.makeIntentHelpActivity(GameMenu.this);
                startActivity(intent);
            }
        });


    }

    public static Context getContextApp() {
        return contextApp;
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
    public static Intent makeIntentGameMenuActivity(Context context){
        return new Intent(context, GameMenu.class);
    }
}
