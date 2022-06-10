package com.example.minesweeperclone.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.minesweeperclone.R;
import com.example.minesweeperclone.model.Board;
import com.example.minesweeperclone.model.Data;

// Game play
public class GameActivity extends AppCompatActivity {

    // Default values
    private int numRows = 4;
    private int numCols = 6;
    private int numMines = 6;

    // State of the game
    private final static int OIL = -2;
    private final static int FOUND_OIL = -3;
    private static final int SCAN_COMPLETE = -4;

    Data store = Data.getInstance();

    TextView updateFound;
    TextView updateScan;
    TextView updateGamesPlayed;

    Board cell;

    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setUpBoard();
        populateButtons();
    }

    private void setUpBoard() {
        updateFound = (TextView) findViewById(R.id.Find);

        updateGamesPlayed = (TextView) findViewById(R.id.IdGames);

        if (store.getRow() != 0 && store.getColumn() != 0 || store.getMines() != 0) {

            numRows = store.getRow();

            numCols = store.getColumn();

            numMines = store.getMines();

            updateFound.setText("Found 0" + " of " + numMines + " barrels");
        } else if (store.getGamesPlayed() == 0) {

            updateGamesPlayed.setText("Times Played: 0");

        }

        cell = new Board(numRows, numCols, numMines);

        buttons = new Button[numRows][numCols];

        updateGamesPlayed.setText("Times Played: " + store.getGamesPlayed());
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.Tr);
        for (int row = 0; row < numRows; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);
            for (int col = 0; col < numCols; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callVibrate();
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int row, int col) {

        Button button = buttons[row][col];

        lockButtonSizes();

        updateScan = (TextView) findViewById(R.id.Scanner);

        int newWidth = button.getWidth();
        int newHeight = button.getHeight();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.golden_oil);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);

        Resources resources = getResources();

        if (cell.getLocation(row, col) == OIL) {

            cell.foundMine(); // Decrement mines

            // All mines found; show congratulations message
            if (cell.getMine() == 0) {
                FragmentManager manager = getSupportFragmentManager();
                MessageFragment dialog = new MessageFragment();
                dialog.show(manager, "Great");
            }

            buttons[row][col].setBackground(new BitmapDrawable(resources, scaledBitmap));

            updateFound.setText("Found " + cell.getOilCount() + " of " + numMines + " barrels");

            cell.setLocation(row, col); // To mark oil has been found

            reloadScanners(button, row, col); // To decrement surrounding scanners

        } else if (cell.getLocation(row, col) == FOUND_OIL) {

            cell.addScannersForMine(row, col);

            button.setText("" + cell.getLocation(row, col));
            button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
            button.setTextColor(Color.parseColor("#4E78A0"));

            updateScan.setText("# Scans used: " + cell.updateScanCount());

            cell.changeScanner(row, col);//To prevent overcounting

        } else if (cell.getLocation(row, col) == SCAN_COMPLETE ) {

        } else {

            cell.addScannersForMine(row, col); // updates hidden scanners for hidden barrels

            button.setText("" + cell.getLocation(row, col));
            button.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
            button.setTextColor(Color.parseColor("#4E78A0"));

            updateScan.setText("# Scans used: " + cell.updateScanCount());

            cell.changeScanner(row, col); //To prevent overcounting
        }
    }

    // Decrements scanners in their respective rows and columns
    private void reloadScanners(Button button, int row, int col) {
        setMineColLeft(button, row, col - 1);
        setMineColRight(button, row, col + 1);
        setMineRowUp(button, row - 1, col);
        setMineRowDown(button, row + 1, col);
    }

    // Changes scanners and alike down oil
    private void setMineRowDown(Button button, int i, int j) {
        if (i == numRows) {
            return;
        } else {
            while (i < numRows) {
                if (cell.getLocation(i, j) == SCAN_COMPLETE) {
                    cell.addScannersForMine(i, j);
                    button = buttons[i][j];
                    button.setText("" + cell.getLocation(i, j));

                    cell.changeScanner(i, j);
                }
                i++;
            }
        }
    }

    // Chamges scanners and alike above oil
    private void setMineRowUp(Button button, int i, int j) {

        if (i < 0) {
            return;
        } else {
            while (i >= 0) {
                if (cell.getLocation(i, j) == SCAN_COMPLETE) {
                    cell.addScannersForMine(i, j);
                    button = buttons[i][j];
                    button.setText("" + cell.getLocation(i, j));
                    cell.changeScanner(i, j);
                }
                i--;
            }
        }
    }

    // Chamges scanners and alike ahead of oil
    private void setMineColRight(Button button, int i, int j) {
        if (j == numCols) {
            return;
        } else {
            while (j < numCols) {
                if (cell.getLocation(i, j) == SCAN_COMPLETE) {
                    cell.addScannersForMine(i, j);
                    button = buttons[i][j];
                    button.setText("" + cell.getLocation(i, j));
                    cell.changeScanner(i, j);
                }
                j++;
            }
        }
    }

    // Chamges scanners and alike behind oil
    private void setMineColLeft(Button button, int i, int j) {
        if (j < 0) {
            return;
        } else {
            while (j >= 0) {
                if (cell.getLocation(i, j) == SCAN_COMPLETE) {
                    cell.addScannersForMine(i, j);
                    button = buttons[i][j];
                    button.setText("" + cell.getLocation(i, j));
                    cell.changeScanner(i, j);
                }
                j--;
            }
        }
    }

    // Code taken from https://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate#:~:text=You%20can%20achieve%20this%20by,Vibrate%20for%20400%20milliseconds%20v.
    public void callVibrate() {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

        } else {
            //deprecated in API 26
            vibe.vibrate(500);
        }
        if (vibe.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
        } else {
            Log.v("Can Vibrate", "NO");
        }
    }

    // Locks button dimensions appropriately to prevent resizing
    private void lockButtonSizes() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);
                button.setTextSize(30);
                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    public static Intent makeLaunchIntent(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        return intent;
    }

    @Override
    protected void onStop() {
        super.onStop();
        store.countGamesPlayed();
    }
}