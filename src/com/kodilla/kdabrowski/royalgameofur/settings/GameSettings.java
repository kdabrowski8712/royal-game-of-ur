package com.kodilla.kdabrowski.royalgameofur.settings;

import com.kodilla.kdabrowski.royalgameofur.gameui.GameSettingsDialog;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

public class GameSettings {

    private int nrOfPiecesToWin;
    private Color humanColor;
    private Color computerColor;
    private int timeOfPlaying;

    public GameSettings(int nrOfPiecesToWin, Color humanColor, Color computerColor, int time) {
        this.nrOfPiecesToWin = nrOfPiecesToWin;
        this.humanColor = humanColor;
        this.computerColor = computerColor;
        this.timeOfPlaying = time;

    }

    public void copy(GameSettings anotherGameSettingsObject) {
        humanColor = anotherGameSettingsObject.getHumanColor();
        computerColor = anotherGameSettingsObject.computerColor;
        nrOfPiecesToWin = anotherGameSettingsObject.getNrOfPiecesToWin();
        timeOfPlaying = anotherGameSettingsObject.getTimeOfPlaying();
    }

    public int getNrOfPiecesToWin() {
        return nrOfPiecesToWin;
    }

    public Color getHumanColor() {
        return humanColor;
    }


    public Color getComputerColor() {
        return computerColor;
    }

    public Dialog<GameSettings> buildDialogForGameSettings() {

        GameSettingsDialog dialog = new GameSettingsDialog();
        return dialog.returnDialog();
    }

    public int getTimeOfPlaying() {
        return timeOfPlaying;
    }
}
