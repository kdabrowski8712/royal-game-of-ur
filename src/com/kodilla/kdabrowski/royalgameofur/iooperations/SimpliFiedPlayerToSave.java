package com.kodilla.kdabrowski.royalgameofur.iooperations;

import com.kodilla.kdabrowski.royalgameofur.iooperations.SimplifiedPieceToSave;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SimpliFiedPlayerToSave implements Serializable {
    private String nick;
    private List<SimplifiedPieceToSave> playerPieces;
    private int nrOfPiecesLeft;
    private int nrOfPiecesMoved;
    private int totalNumberOfPlayerPieces;

    public  SimpliFiedPlayerToSave() {
        playerPieces = new ArrayList<>();
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public List<SimplifiedPieceToSave> getPlayerPieces() {
        return playerPieces;
    }

    public void setPlayerPieces(List<SimplifiedPieceToSave> playerPieces) {
        this.playerPieces = playerPieces;
    }

    public int getNrOfPiecesLeft() {
        return nrOfPiecesLeft;
    }

    public void setNrOfPiecesLeft(int nrOfPiecesLeft) {
        this.nrOfPiecesLeft = nrOfPiecesLeft;
    }

    public int getNrOfPiecesMoved() {
        return nrOfPiecesMoved;
    }

    public void setNrOfPiecesMoved(int nrOfPiecesMoved) {
        this.nrOfPiecesMoved = nrOfPiecesMoved;
    }

    public int getTotalNumberOfPlayerPieces() {
        return totalNumberOfPlayerPieces;
    }

    public void setTotalNumberOfPlayerPieces(int totalNumberOfPlayerPieces) {
        this.totalNumberOfPlayerPieces = totalNumberOfPlayerPieces;
    }
}
