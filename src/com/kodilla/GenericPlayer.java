package com.kodilla;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GenericPlayer {
    private String nick;
    private List<Piece> playerPieces;
    private int nrOfPiecesLeft;
    private int nrOfPiecesMoved;

    public GenericPlayer(String nick , Color pieceColor) {
        this.nick = nick;

        playerPieces = new ArrayList<>();

        for(int i=0; i<7;i++) {
            playerPieces.add(new Piece(pieceColor));
        }
        nrOfPiecesLeft =7;
        nrOfPiecesMoved = 0;
    }

    public void resetPlayer() {
        nrOfPiecesMoved = 0;
        nrOfPiecesLeft = 7;

        for(Piece p : playerPieces) {
            p.setOnBoard(false);
            p.setMovedThroughBoard(false);
        }
    }

    public void piecesStatisticRecalculation() {

        nrOfPiecesMoved = 0;
        for(Piece p : playerPieces) {
            if(p.isMovedThroughBoard()) {
                nrOfPiecesMoved+=1;
            }
        }
        nrOfPiecesLeft = 7 - nrOfPiecesMoved;
    }

    public int getPiecesReadyToEnterIntoBoard() {
        int result = 0;

        for(Piece p : playerPieces) {

            if(!p.isOnBoard() && !p.isMovedThroughBoard()){
                result+=1;
            }
        }

        return  result;
    }

    public int getPiecesMoved() {
        int result = 0;


        return result;
    }

    public Piece getPieceByCoordinates(int x , int y) {
        Piece result = null;
        boolean found = false;

        Iterator<Piece> pieceIterator = playerPieces.iterator();

        while (!found && pieceIterator.hasNext()) {

            Piece piece = pieceIterator.next();
            if(piece.isOnBoard()) {
                if(piece.getColumnPositionOnBoard()== x && piece.getRowPositionOnBoard()==y){
                    result = piece;
                    found=true;
                }
            }
        }

        return result;
    }

    public Piece getFreePiece() {
        Piece result = null;

        Iterator<Piece> pieceIterator = playerPieces.iterator();
        boolean found = false;

        while(!found && pieceIterator.hasNext()) {

            Piece p = pieceIterator.next();

            if(!p.isOnBoard() && !p.isMovedThroughBoard()) {
                result = p;
                found = true;
            }
        }

        return result;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public List<Piece> getPlayerPieces() {
        return playerPieces;
    }

    public int getNrOfPiecesLeft() {
        return nrOfPiecesLeft;
    }

    public int getNrOfPiecesMoved() {
        return nrOfPiecesMoved;
    }

    public void setNrOfPiecesLeft(int nrOfPiecesLeft) {
        this.nrOfPiecesLeft = nrOfPiecesLeft;
    }

    public void setNrOfPiecesMoved(int nrOfPiecesMoved) {
        this.nrOfPiecesMoved = nrOfPiecesMoved;
    }


}
