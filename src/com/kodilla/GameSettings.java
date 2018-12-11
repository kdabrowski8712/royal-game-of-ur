package com.kodilla;

import javafx.scene.paint.Color;

public class GameSettings {
    public GameSettings(int nrOfPiecesToWin, Color humanColor, Color computerColor) {
        this.nrOfPiecesToWin = nrOfPiecesToWin;
        this.humanColor = humanColor;
        this.computerColor = computerColor;
    }

    private int nrOfPiecesToWin;
    private Color humanColor;
    private Color computerColor;

    public int getNrOfPiecesToWin() {
        return nrOfPiecesToWin;
    }

    public void setNrOfPiecesToWin(int nrOfPiecesToWin) {
        this.nrOfPiecesToWin = nrOfPiecesToWin;
    }

    public Color getHumanColor() {
        return humanColor;
    }

    public void setHumanColor(Color humanColor) {
        this.humanColor = humanColor;
    }

    public Color getComputerColor() {
        return computerColor;
    }

    public void setComputerColor(Color computerColor) {
        this.computerColor = computerColor;
    }
}
