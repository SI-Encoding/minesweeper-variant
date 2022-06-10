package com.example.minesweeperclone.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.minesweeperclone.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    Button gameOn;
    Button setOn;
    Button guideOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        playGame();
        configuration();
        guide();


    }

    private void playGame() {
        guideOn = (Button) findViewById(R.id.PlayGame);

        guideOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGameActivity();

            }
        });

    }

    private void configuration() {
        setOn = (Button) findViewById(R.id.Options);

        setOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchOptionsActivity();
            }
        });
    }

    private void guide() {
        gameOn= (Button) findViewById(R.id.Info);

        gameOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGuideActivity();

            }
        });
    }


    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, MainMenu.class);

        return intent;
    }

    private void launchGameActivity() {
        Intent intent = GameActivity.makeLaunchIntent(MainMenu.this);
        startActivity(intent);

    }

    private void launchOptionsActivity() {
        Intent intent = Option.makeLaunchIntent(MainMenu.this);
        startActivity(intent);
    }

    private void launchGuideActivity() {
        Intent intent = Help.makeLaunchIntent(MainMenu.this);
        startActivity(intent);
    }
}