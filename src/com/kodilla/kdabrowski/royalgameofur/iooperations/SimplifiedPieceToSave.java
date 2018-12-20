package com.kodilla.kdabrowski.royalgameofur.iooperations;

import java.io.Serializable;

public class SimplifiedPieceToSave implements Serializable {

    private boolean isOnBoard;
    private boolean isMovedThroughBoard;
    private  String pieceColor;
    private int pieceRow;
    private int pieceColumn;

    public boolean isOnBoard() {
        return isOnBoard;
    }

    public void setOnBoard(boolean onBoard) {
        isOnBoard = onBoard;
    }

    public boolean isMovedThroughBoard() {
        return isMovedThroughBoard;
    }

    public void setMovedThroughBoard(boolean movedThroughBoard) {
        isMovedThroughBoard = movedThroughBoard;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }

    public int getPieceRow() {
        return pieceRow;
    }

    public void setPieceRow(int pieceRow) {
        this.pieceRow = pieceRow;
    }

    public int getPieceColumn() {
        return pieceColumn;
    }

    public void setPieceColumn(int pieceColumn) {
        this.pieceColumn = pieceColumn;
    }
}
