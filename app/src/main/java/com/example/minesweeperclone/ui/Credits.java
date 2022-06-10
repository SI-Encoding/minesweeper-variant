package com.example.minesweeperclone.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minesweeperclone.R;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

    }


    public static Intent makeLaunchIntent(Context context){

        Intent intent = new Intent(context,Credits.class);

        return intent;



    }





}