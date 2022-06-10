package com.example.minesweeperclone.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.minesweeperclone.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// Welcome screen
public class MainActivity extends AppCompatActivity {

    private static int DELAY = 6500;
    Animation moveDown;
    Animation moveUpBubbles;
    TextView title;
    ImageView bubbleLeft;
    ImageView bubbleRight;
    Button skip;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAnimation();
        stopAnimation();
        skipButton();
    }

    private void skipButton() {
        skip = findViewById(R.id.SkipButton);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code taken from https://stackoverflow.com/questions/33780491/skipping-a-splash-screen
                if (handler != null) {
                    handler.removeCallbacksAndMessages(null);
                }
                startMainMenu();
            }
        });
    }

    // Code taken from https://www.youtube.com/watch?v=JLIFqqnSNmg
    private void stopAnimation() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainMenu();
            }
        }, DELAY);
    }

    private void startMainMenu() {
        Intent intent = MainMenu.makeLaunchIntent(MainActivity.this);
        startActivity(intent);
        finish();
    }

    // Code taken from https://www.youtube.com/watch?v=JLIFqqnSNmg
    private void createAnimation() {
        moveDown = AnimationUtils.loadAnimation(this, R.anim.move_down_anim);
        moveUpBubbles = AnimationUtils.loadAnimation(this, R.anim.move_top_for_bubbles);

        title = (TextView) findViewById(R.id.textView);
        bubbleLeft = (ImageView) findViewById(R.id.BubbleLeft);
        bubbleRight = (ImageView) findViewById(R.id.BubbleRight);

        title.setAnimation(moveDown);
        bubbleLeft.setAnimation(moveUpBubbles);
        bubbleRight.setAnimation(moveUpBubbles);
    }
}