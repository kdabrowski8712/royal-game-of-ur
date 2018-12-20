package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.iooperations.SimplifiedPieceToSave;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece {

    private boolean isOnBoard;
    private boolean isMovedThroughBoard;
    private Circle pieceRepresentation;
    //private Color pieceColor;
    private  String pieceColor;
    private BoardCoordinates piecePositionOnBoard;

    //public static final long serialVersionUID = -2276823960220649728L;

    public Piece(String color) {
        pieceRepresentation =  new Circle(20 , Color.web(color));
        this.pieceColor = color;

        piecePositionOnBoard = new BoardCoordinates(0,0);
    }

    public BoardCoordinates getPiecePositionOnBoard() {
        return piecePositionOnBoard;
    }

    public void resetPieceState() {
        isOnBoard=false;
        isMovedThroughBoard = false;
    }

    public void resetPiecePosition(String playerNick) {
        if(playerNick.contains("Computer")) {
            piecePositionOnBoard.setColumn(2);
        }
        else {
            piecePositionOnBoard.setColumn(0);
        }
        piecePositionOnBoard.setRow(4);
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

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
        pieceRepresentation.setFill(Color.web(pieceColor));
    }

    public void removeFromBoard(Player player) {
        isOnBoard = false;
        resetPiecePosition(player.getNick());
        player.piecesStatisticRecalculation();
    }

    public void setPieceCoordinates(BoardCoordinates newCoordiantes) {
        this.piecePositionOnBoard.setRow(newCoordiantes.getRow());
        this.piecePositionOnBoard.setColumn(newCoordiantes.getColumn());
    }

    public SimplifiedPieceToSave returnPieceToSave() {
        SimplifiedPieceToSave result = new SimplifiedPieceToSave();

        result.setMovedThroughBoard(isMovedThroughBoard);
        result.setOnBoard(isOnBoard);
        result.setPieceColor(pieceColor);
        result.setPieceColumn(piecePositionOnBoard.getColumn());
        result.setPieceRow(piecePositionOnBoard.getRow());

        return result;
    }
}
