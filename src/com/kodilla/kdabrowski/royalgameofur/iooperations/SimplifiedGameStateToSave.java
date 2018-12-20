package com.kodilla.kdabrowski.royalgameofur.iooperations;

import java.io.Serializable;

public class SimplifiedGameStateToSave implements Serializable {
    private SimpliFiedPlayerToSave human;
    private SimpliFiedPlayerToSave computer;
    private boolean playerMove;
    private int nrOfGames;

    public SimpliFiedPlayerToSave getHuman() {
        return human;
    }

    public void setHuman(SimpliFiedPlayerToSave human) {
        this.human = human;
    }

    public SimpliFiedPlayerToSave getComputer() {
        return computer;
    }

    public void setComputer(SimpliFiedPlayerToSave computer) {
        this.computer = computer;
    }

    public boolean isPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(boolean playerMove) {
        this.playerMove = playerMove;
    }

    public int getNrOfGames() {
        return nrOfGames;
    }

    public void setNrOfGames(int nrOfGames) {
        this.nrOfGames = nrOfGames;
    }
}
