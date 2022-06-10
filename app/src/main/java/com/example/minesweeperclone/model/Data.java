package com.example.minesweeperclone.model;

// Sets and gets data to interact between options and game activity
public class Data {

    private int row;
    private int column;
    private int mines;
    private int gamesPlayed;

    private static Data instance;

    private Data() {
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public void countGamesPlayed() {
        gamesPlayed++;
    }

    public void eraseGamesPlayed() {
        gamesPlayed = 0;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
