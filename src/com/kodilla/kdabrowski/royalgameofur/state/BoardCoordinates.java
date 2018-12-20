package com.kodilla.kdabrowski.royalgameofur.state;

import java.io.Serializable;
import java.util.Objects;

public class BoardCoordinates  {
    private int column;
    private int row;

    //public static final long serialVersionUID = -7347757483613843681L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardCoordinates that = (BoardCoordinates) o;
        return column == that.column &&
                row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
