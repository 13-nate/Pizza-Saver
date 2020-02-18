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

    //private ImageView logo;
    private TextView textLogo;
    private TextView authorLogo;
    private ImageButton skipButton;
    final Handler handler = new Handler();
    Animation logoAnim;
    Animation topAnim;
    Animation authorAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        textLogo = findViewById(R.id.welcomeLogo_textView);
        authorLogo = findViewById(R.id.author_textView);
        skipButton = findViewById(R.id.skip_imageButton);

        startAnimation();
        delayAnimation();
        skipButton.setOnClickListener(v -> {
            Intent intent = GameMenu.makeIntentGameMenuActivity(WelcomeScreenActivity.this);
            startActivity(intent);
            handler.removeCallbacksAndMessages(null);
            finish();
        });


    }
    private void delayAnimation() {

      final Runnable runnable = () -> {
          Intent intent = GameMenu.makeIntentGameMenuActivity(WelcomeScreenActivity.this);
          startActivity(intent);
          finish();

      };
      handler.postDelayed(runnable, 6000);
    }
    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        logoAnim.cancel();
        topAnim.cancel();
        authorAnim.cancel();
        super.onBackPressed();
        finish();
    }

    private void startAnimation() {
        logoAnim = AnimationUtils.loadAnimation(this,R.anim.welcomescreenanimation);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.topanimation);
        authorAnim = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        textLogo.startAnimation(topAnim);
        authorLogo.startAnimation(authorAnim);
    }
}
