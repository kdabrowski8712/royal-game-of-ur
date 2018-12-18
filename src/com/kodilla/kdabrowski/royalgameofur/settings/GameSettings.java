package com.kodilla.kdabrowski.royalgameofur.settings;

import com.kodilla.kdabrowski.royalgameofur.gameui.GameSettingsDialog;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class GameSettings implements Serializable{


    private int nrOfPiecesToWin;
    //private Color humanColor;
   //private Color computerColor;
    private String humanColorText;
    private String computerColorText;


    private int timeOfPlaying;
    private boolean settingsLoadedFromFile;

    public static final long serialVersionUID = 7965599591866909073L;


//    public GameSettings(int nrOfPiecesToWin, Color humanColor, Color computerColor, int time) {
//        this.nrOfPiecesToWin = nrOfPiecesToWin;
//        this.humanColor = humanColor;
//        this.computerColor = computerColor;
//        this.timeOfPlaying = time;
//        settingsLoadedFromFile = false;
//
//    }


    public GameSettings(int nrOfPiecesToWin, String humanColorText, String computerColorText, int timeOfPlaying) {
        this.nrOfPiecesToWin = nrOfPiecesToWin;
        this.humanColorText = humanColorText;
        this.computerColorText = computerColorText;
        this.timeOfPlaying = timeOfPlaying;
    }

//    public void copy(GameSettings anotherGameSettingsObject) {
//        humanColor = anotherGameSettingsObject.getHumanColor();
//        computerColor = anotherGameSettingsObject.computerColor;
//        nrOfPiecesToWin = anotherGameSettingsObject.getNrOfPiecesToWin();
//        timeOfPlaying = anotherGameSettingsObject.getTimeOfPlaying();
//        this.settingsLoadedFromFile = anotherGameSettingsObject.isSettingsLoadedFromFile();
//    }

    public void copy(GameSettings anotherGameSettingsObject) {
        humanColorText = anotherGameSettingsObject.getHumanColorText();
        computerColorText = anotherGameSettingsObject.getComputerColorText();
        nrOfPiecesToWin = anotherGameSettingsObject.getNrOfPiecesToWin();
        timeOfPlaying = anotherGameSettingsObject.getTimeOfPlaying();
        this.settingsLoadedFromFile = anotherGameSettingsObject.isSettingsLoadedFromFile();
    }

    public int getNrOfPiecesToWin() {
        return nrOfPiecesToWin;
    }

//    public Color getHumanColor() {
//        return humanColor;
//    }
//
//
//    public Color getComputerColor() {
//        return computerColor;
//    }


    public String getHumanColorText() {
        return humanColorText;
    }

    public String getComputerColorText() {
        return computerColorText;
    }

    public Dialog<GameSettings> buildDialogForGameSettings() {

        GameSettingsDialog dialog = new GameSettingsDialog(this);
        return dialog.returnDialog();
    }

    public int getTimeOfPlaying() {
        return timeOfPlaying;
    }

    public boolean isSettingsLoadedFromFile() {
        return settingsLoadedFromFile;
    }

    public void setSettingsLoadedFromFile(boolean settingsLoadedFromFile) {
        this.settingsLoadedFromFile = settingsLoadedFromFile;
    }


    @Override
    public String toString() {
        return  nrOfPiecesToWin +
                "," + humanColorText +
                "," + computerColorText +
                "," + timeOfPlaying +
                '\n';
    }
}
