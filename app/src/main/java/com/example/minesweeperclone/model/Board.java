package com.example.minesweeperclone.model;

import java.util.Random;

// Sets up the game board dimensions and inserts random mines
public class Board {

    // Initializes constructor
    private int rows;
    private int columns;
    private int mines;

    // Keeps state of the board
    private final static int EMPTY = -1;
    private final static int OIL = -2;
    private final static int FOUND_OIL = -3;
    private static final int SCAN_COMPLETE = -4;


    // Increments count of item found; used by setTexts
    private int scanCount = 0;
    private int oilCount = 0;

    // The board
    private int[][] location;

    // Create the board
    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        intializeBoard(rows, columns);
        randomnizeBoard();
    }

    // Create board that is entirely empty
    private void intializeBoard(int rows, int columns) {
        location = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                location[i][j] = EMPTY;
            }
        }
    }

    // Puts oil randomly in board
    private void randomnizeBoard() {
        Random generator = new Random();

        int untilMines = 0;
        while (untilMines != mines) {
            int thisRow = generator.nextInt(rows);
            int thisColumn = generator.nextInt(columns);

            if (location[thisRow][thisColumn] != OIL) {
                location[thisRow][thisColumn] = OIL;
                untilMines++;
            }
        }
        addScanners();
    }

    // Adds scanners to positions that do not have a mine
    private void addScanners() {
        int scan = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (location[i][j] != OIL) {

                    int columnLeft = findMinesColLeft(i, j - 1);
                    int columnRight = findMinesColRight(i, j + 1);
                    int rowUp = findMinesRowUp(i - 1, j);
                    int rowDown = findMinesRowDown(i + 1, j);

                    scan = columnLeft + columnRight + rowUp + rowDown;

                    location[i][j] = scan;
                }
            }
        }
    }

    //  Updates the scanner based on the hidden mines left when clicked
    public void addScannersForMine(int i, int j) {
        int scan = 0;
        int columnLeft = findMinesColLeft(i, j - 1);
        int columnRight = findMinesColRight(i, j + 1);
        int rowUp = findMinesRowUp(i - 1, j);
        int rowDown = findMinesRowDown(i + 1, j);
        scan = columnLeft + columnRight + rowUp + rowDown;
        location[i][j] = scan;
    }

    // Search for hidden mines and update scanner
    private int findMinesRowDown(int i, int j) {
        int oilFound = 0;
        if (i == rows) {
            return 0;
        } else {
            while (i < rows) {
                if (location[i][j] == OIL) {
                    oilFound++;
                }
                i++;
            }
        }
        return oilFound;
    }

    private int findMinesRowUp(int i, int j) {
        int oilFound = 0;
        if (i < 0) {
            return 0;
        } else {
            while (i >= 0) {
                if (location[i][j] == OIL) {
                    oilFound++;
                }
                i--;
            }
        }
        return oilFound;
    }

    private int findMinesColLeft(int i, int j) {
        int oilFound = 0;
        if (j < 0) {
            return 0;
        } else {
            while (j >= 0) {
                if (location[i][j] == OIL) {
                    oilFound++;
                }
                j--;
            }
        }
        return oilFound;
    }

    private int findMinesColRight(int i, int j) {
        int oilFound = 0;
        if (j == columns) {
            return 0;
        } else {
            while (j < columns) {
                if (location[i][j] == OIL) {
                    oilFound++;
                }
                j++;
            }
        }
        return oilFound;
    }

    // Changes the status of the location that has found the mine
    public void setLocation(int row, int column) {
        location[row][column] = FOUND_OIL;
    }

    // Changes the status of the location that has the scanner
    public void changeScanner(int row, int column) {
        location[row][column] = SCAN_COMPLETE;
    }

    public int getLocation(int rows, int columns) {
        return location[rows][columns];
    }

    // Used to set text
    public int updateScanCount() {
        return ++scanCount;
    }

    public int getOilCount() {
        return ++oilCount;
    }

    // Keeps track of mines
    public void foundMine() {
        mines--;
    }

    public int getMine() {
        return mines;
    }

}
