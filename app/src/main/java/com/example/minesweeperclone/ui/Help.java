package com.example.minesweeperclone.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minesweeperclone.R;

public class Help extends AppCompatActivity {

    Button creditButton;
    TextView textInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setUpButton();
    }



    private void setUpButton() {
        creditButton = (Button) findViewById(R.id.IdCredits);

        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCredits();

            }
        });
    }



    private void startCredits() {
        Intent intent = Credits.makeLaunchIntent(Help.this);
        startActivity(intent);
    }


    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, Help.class);

        return intent;
    }
}