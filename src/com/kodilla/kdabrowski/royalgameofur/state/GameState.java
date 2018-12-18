package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;
import javafx.scene.paint.Color;

public class GameState {
    private Player human;
    private Player computer;
    private boolean playerMove;
    private GameMove humanMoveToPassToOnClickHandler;

    public GameState(String playerNick , GameSettings settings ){
        playerMove = false;
//        human = new Player(playerNick,settings.getHumanColor());
//        computer = new Player("Computer",settings.getComputerColor());
        human = new Player(playerNick, Color.web(settings.getHumanColorText()));
        computer = new Player("Computer",Color.web(settings.getComputerColorText()));
    }

    public void updateGameState(GameSettings settings) {
//        human.setPiecesColor(settings.getHumanColor());
//        computer.setPiecesColor(settings.getComputerColor());
        human.setPiecesColor(Color.web(settings.getComputerColorText()));
        computer.setPiecesColor(Color.web(settings.getComputerColorText()));
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
}
