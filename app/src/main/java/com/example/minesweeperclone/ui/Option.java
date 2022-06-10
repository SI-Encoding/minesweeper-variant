package com.example.minesweeperclone.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minesweeperclone.R;
import com.example.minesweeperclone.model.Data;

// Options activity
public class Option extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Data store = Data.getInstance();
    Spinner spinner1;
    Spinner spinner2;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        spinner1 = findViewById(R.id.IdChooseSize);
        spinner2 = findViewById(R.id.IdChooseBarrels);

        spinners();
        passSavedSpinners();
        resetGamesPlayed();
    }

    private void resetGamesPlayed() {
        reset = (Button) findViewById(R.id.IdReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.eraseGamesPlayed();
            }
        });
    }

    // Sets up the default values for the spinner
    private void passSavedSpinners() {
        int saveSpinner1 = getSavedSpinner1(this);
        int saveSpinner2 = getSavedSpinner2(this);

        setSpinner1(saveSpinner1);
        setSpinner2(saveSpinner2);

        transferSpinner1(saveSpinner1);
        transferSpinner2(saveSpinner2);
    }

    // Passes on data to store in data class; to retrieve from game activity
    private void transferSpinner1(int saveSpinner1) {

        switch (saveSpinner1) {
            case 0:
                store.setRow(4);
                store.setColumn(6);
                break;

            case 1:
                store.setRow(5);
                store.setColumn(10);
                break;

            case 2:
                store.setRow(6);
                store.setColumn(15);
                break;

            default:
                store.setRow(4);
                store.setColumn(6);
                break;
        }
    }

    private void transferSpinner2(int saveSpinner2) {
        switch (saveSpinner2) {
            case 0:
                store.setMines(6);
                break;

            case 1:
                store.setMines(10);
                break;

            case 2:
                store.setMines(15);
                break;


            case 3:
                store.setMines(20);
                break;

            default:
                store.setMines(6);
                break;
        }
    }


    public void setSpinner1(int pos) {
        spinner1.setSelection(pos);
        transferSpinner1(pos);
    }

    public void setSpinner2(int pos) {
        spinner2.setSelection(pos);
        transferSpinner2(pos);
    }

    // Code taken from: https://www.youtube.com/watch?v=on_OrrX7Nw4
    public void spinners() {
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sizes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
    }

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, Option.class);

        return intent;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.IdChooseSize) {
            saveSpinners();
        } else {
            saveSpinners();
        }
    }

    // Save to sharedpreferences
    private void saveSpinners() {
        int spinner1Position = spinner1.getSelectedItemPosition();
        int spinner2Position = spinner2.getSelectedItemPosition();

        SharedPreferences prefs = this.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("Spinner1 values", spinner1Position);
        editor.putInt("Spinner2 values", spinner2Position);
        editor.apply();
    }


    // Load from sharedpreferences
    static public int getSavedSpinner1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        return prefs.getInt("Spinner1 values", 0);
    }

    // Load from sharedpreferences
    static public int getSavedSpinner2(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("OptionPreferences", MODE_PRIVATE);
        return prefs.getInt("Spinner2 values", 0);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // When activity is closed; save the values
    @Override
    protected void onStop() {
        super.onStop();
        passSavedSpinners();
    }
}