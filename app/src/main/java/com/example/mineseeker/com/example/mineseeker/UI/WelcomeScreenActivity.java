package com.example.mineseeker.com.example.mineseeker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mineseeker.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView textLogo;
    private TextView authorLogo;
    private ImageButton skipButton;
    final private static int ANIMATION_TIME_OUT = 6000;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        logo = findViewById(R.id.logo);
        textLogo = findViewById(R.id.welcomeLogo_textView);
        authorLogo = findViewById(R.id.author_textView);
        skipButton = findViewById(R.id.skip_imageButton);

        startAnimation();
        delayAnimation();
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameMenu.makeIntentGameMenuActivity(WelcomeScreenActivity.this);
                startActivity(intent);
                handler.removeCallbacksAndMessages(null);
                finish();
            }
        });
    }
    private void delayAnimation() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = GameMenu.makeIntentGameMenuActivity(WelcomeScreenActivity.this);
                startActivity(intent);
                handler.removeCallbacksAndMessages(null);
                finish();
            }
        }, ANIMATION_TIME_OUT);
    }

    private void startAnimation() {
        Animation logoAnim = AnimationUtils.loadAnimation(this,R.anim.welcomescreenanimation);
        Animation topAnim = AnimationUtils.loadAnimation(this,R.anim.topanimation);
        Animation authorAnim = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        textLogo.startAnimation(topAnim);
        logo.startAnimation(logoAnim);
        authorLogo.startAnimation(authorAnim);
    }
}
