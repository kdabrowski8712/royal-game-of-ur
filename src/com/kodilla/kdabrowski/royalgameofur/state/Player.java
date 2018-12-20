package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private String nick;
    private List<Piece> playerPieces;
    private int nrOfPiecesLeft;
    private int nrOfPiecesMoved;
    private int totalNumberOfPlayerPieces;

    public boolean isHuman() {
        boolean result = false;
        if(!nick.contains("Computer")) {
            result = true;
        }
        return result;
    }

    public Player(String nick , String pieceColor) {
        this.nick = nick;

        playerPieces = new ArrayList<>();
        totalNumberOfPlayerPieces = 7;

        for(int i = 0; i< totalNumberOfPlayerPieces; i++) {
            playerPieces.add(new Piece(pieceColor));
        }
        nrOfPiecesLeft =7;
        nrOfPiecesMoved = 0;

        populatePiecesStartingPositions();
    }

    private void populatePiecesStartingPositions() {
        for(Piece processedPiece : playerPieces )
        {
            processedPiece.resetPiecePosition(nick);
        }
    }

    public void resetPlayer() {
        nrOfPiecesMoved = 0;
        nrOfPiecesLeft = totalNumberOfPlayerPieces;

        for(Piece p : playerPieces) {
            p.resetPieceState();
            p.resetPiecePosition(nick);
        }
    }

    public void piecesStatisticRecalculation() {

        nrOfPiecesMoved = 0;
        for(Piece p : playerPieces) {
            if(p.isMovedThroughBoard()) {
                nrOfPiecesMoved+=1;
            }
        }
        nrOfPiecesLeft = totalNumberOfPlayerPieces - nrOfPiecesMoved;
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

    public Piece getPieceByBoardPosition(BoardCoordinates boardPosition) {
        Piece result = null;
        boolean found = false;

        Iterator<Piece> pieceIterator = playerPieces.iterator();

        while (!found && pieceIterator.hasNext()) {

            Piece piece = pieceIterator.next();

            if(piece.isOnBoard()) {
                if(piece.getPiecePositionOnBoard().getColumn()== boardPosition.getColumn() && piece.getPiecePositionOnBoard().getRow()==boardPosition.getRow()){
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

    public boolean checkWinCondition(GameSettings settings) {
        boolean win = false;

        if(this.getNrOfPiecesMoved()==settings.getNrOfPiecesToWin()) {
            win = true;
        }

        return  win;
    }

    public void setPiecesColor(String newColor) {
        for(Piece p : playerPieces) {
            p.setPieceColor(newColor);
        }
    }

    public  boolean checkCollisionWithPlayerPieces(BoardCoordinates coordinatesToCheck) {
      //  boolean found = false;
        Iterator<Piece> pieceIterator = playerPieces.iterator();
//        while (!found && pieceIterator.hasNext()) {
////
////            Piece p = pieceIterator.next();
////
////            if (p.isOnBoard()) {
////                if (p.getPiecePositionOnBoard().getColumn() == coordinatesToCheck.getColumn() && p.getPiecePositionOnBoard().getRow() == coordinatesToCheck.getRow()) {
////                    found = true; // teg dlugiego ifa zamienic w funkcje funkcja Extract w intellij i do metody prywatnej małej
////
////                }
////            }
////
////        }
        while (pieceIterator.hasNext()) {
            Piece p = pieceIterator.next();
            if (p.isOnBoard() && p.getPiecePositionOnBoard().equals(coordinatesToCheck)) {
                //if (p.getPiecePositionOnBoard().getColumn() == coordinatesToCheck.getColumn() && p.getPiecePositionOnBoard().getRow() == coordinatesToCheck.getRow()) {
                    return true; // teg dlugiego ifa zamienic w funkcje funkcja Extract w intellij i do metody prywatnej małej

               // }
            }
        }
        return false;
        //return found;
    }

    public boolean checkIfPlayerHaveAllPiecesOutOfBoard(){

        if(getPiecesReadyToEnterIntoBoard()==7) {
            return  true; // wrzucic returny
        }

        return false;
    }

    public SimpliFiedPlayerToSave returnPlayerToSave() {

        SimpliFiedPlayerToSave result = new SimpliFiedPlayerToSave();

        result.setNick(nick);
        result.setNrOfPiecesLeft(nrOfPiecesLeft);
        result.setNrOfPiecesMoved(nrOfPiecesMoved);
        result.setTotalNumberOfPlayerPieces(totalNumberOfPlayerPieces);

        for(Piece p : playerPieces) {
            result.getPlayerPieces().add(p.returnPieceToSave());
        }



        return result;

    }

}
