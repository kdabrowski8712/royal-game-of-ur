package com.kodilla;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece {
    private int columnPositionOnBoard;
    private int rowPositionOnBoard;
    private boolean isOnBoard;
    private boolean isMovedThroughBoard;
    private Circle pieceRepresentation;
    private Color pieceColor;
    private int possibleNewRow;
    private int getPossibleNewColumn;

    public Piece(Color color) {
        pieceRepresentation =  new Circle(20 , color);
        this.pieceColor = color;
    }

    public void resetPosition(GenericPlayer player) {
        if(player instanceof Human) {
            columnPositionOnBoard = 0;

        } else if (player instanceof Computer) {
            columnPositionOnBoard =2;
        }

        rowPositionOnBoard = 4;
    }

    public int getColumnPositionOnBoard() {
        return columnPositionOnBoard;
    }

    public void setColumnPositionOnBoard(int columnPositionOnBoard) {
        this.columnPositionOnBoard = columnPositionOnBoard;
    }

    public int getRowPositionOnBoard() {
        return rowPositionOnBoard;
    }

    public void setRowPositionOnBoard(int rowPositionOnBoard) {
        this.rowPositionOnBoard = rowPositionOnBoard;
    }

    public boolean isOnBoard() {
        return isOnBoard;
    }

    public void setOnBoard(boolean onBoard) {
        isOnBoard = onBoard;
    }

    public Circle getPieceRepresentation() {
        return pieceRepresentation;
    }


    public boolean isMovedThroughBoard() {
        return isMovedThroughBoard;
    }

    public void setMovedThroughBoard(boolean movedThroughBoard) {
        isMovedThroughBoard = movedThroughBoard;
    }

    public int getPossibleNewRow() {
        return possibleNewRow;
    }

    public void setPossibleNewRow(int possibleNewRow) {
        this.possibleNewRow = possibleNewRow;
    }

    public int getPossibleNewColumn() {
        return getPossibleNewColumn;
    }

    public void setPossibleNewColumn(int getPossibleNewColumn) {
        this.getPossibleNewColumn = getPossibleNewColumn;
    }

    public void setPieceColor(Color pieceColor) {
        this.pieceColor = pieceColor;
        pieceRepresentation.setFill(pieceColor);
    }

    public Color getPieceColor() {
        return pieceColor;
    }
}
