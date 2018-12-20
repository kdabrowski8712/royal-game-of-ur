package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.iooperations.SimplifiedGameStateToSave;
import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;

public class GameState {
    private Player human;
    private Player computer;
    private boolean playerMove;
    private GameMove humanMoveToPassToOnClickHandler;
    private int nrOfGames;

    public GameState(String playerNick , GameSettings settings ){
        playerMove = false;
        human = new Player(playerNick, settings.getHumanColorText());
        computer = new Player("Computer",settings.getComputerColorText());
    }

    public void updateGameState(GameSettings settings) {

        human.setPiecesColor(settings.getHumanColorText());
        computer.setPiecesColor(settings.getComputerColorText());
    }

    public Player getHuman() {
        return human;
    }

    public Player getComputer() {
        return computer;
    }

    public boolean isPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(boolean playerMove) {
        this.playerMove = playerMove;
    }

    public GameMove getHumanMoveToPassToOnClickHandler() {
        return humanMoveToPassToOnClickHandler;
    }

    public void setHumanMoveToPassToOnClickHandler(GameMove humanMoveToPassToOnClickHandler) {
        this.humanMoveToPassToOnClickHandler = humanMoveToPassToOnClickHandler;
    }

    public void increaseGamesNumber() {
        nrOfGames+=1;
    }

    public int getNrOfGames() {
        return nrOfGames;
    }

    public SimplifiedGameStateToSave returnStateToSave() {

    SimplifiedGameStateToSave result = new SimplifiedGameStateToSave();

    result.setComputer(computer.returnPlayerToSave());
    result.setHuman(human.returnPlayerToSave());
    result.setNrOfGames(nrOfGames);
    result.setPlayerMove(playerMove);

    return result;

    }
}
