package com.kodilla.kdabrowski.royalgameofur.state;

public class BoardCoordinates  {
    private int column;
    private int row;

    public BoardCoordinates(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
