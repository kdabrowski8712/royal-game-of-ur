package com.kodilla.kdabrowski.royalgameofur.gameui;

import com.kodilla.kdabrowski.royalgameofur.state.Player;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class StatisticsPanel {

    private GridPane panel;

    private Label nrOfGamesValue;
    private Label nrOfGamesTitle;
    private Label humaanPiecesCleared;
    private Label humanPiecesLeft;
    private Label computerPiecesLeft;
    private Label computerPiecesCleared;
    private Label titleLabel;
    private Label playerLabel;
    private Label computerLabel;
    private Label overallLabel;
    private Label hPiecesCLearedTitle;
    private Label hPiecesLeftTitle;
    private Label cPiecesCLearedTitle;
    private Label cPiecesLeftTitle;
    private Label diceRollhumanTitle;
    private Label diceRollHuman;
    private Label diceRollComputerTitle;
    private Label diceRollComputer;
    private Label nrOfMovesToWinGameTitle;
    private Label nrOfMovesToWinGame;


    private String cssLayout = "-fx-border-color: black;\n" +
            "-fx-border-insets: 5;\n" +
            "-fx-border-width: 1;\n" +
            "-fx-border-style: solid;\n";


    public StatisticsPanel() {
        nrOfGamesValue = new Label("0");
        humaanPiecesCleared = new Label("0");
        humanPiecesLeft = new Label("7");
        computerPiecesLeft = new Label("7");
        computerPiecesCleared = new Label("0");

        diceRollComputerTitle = new Label("Dice roll in current move - computer: ");
        diceRollhumanTitle = new Label("Dice roll in current move - human: ");

        diceRollComputer = new Label("0");
        diceRollHuman = new Label("0");

        titleLabel = new Label("STATISTICS");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 26");

        this.playerLabel = new Label("Player : ");
        this.playerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        this.playerLabel.setTextFill(Color.BROWN);

        this.computerLabel = new Label("Computer : ");
        this.computerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        this.computerLabel.setTextFill(Color.BROWN);

        this.overallLabel = new Label("Overall : ");
        this.overallLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15");
        this.overallLabel.setTextFill(Color.BROWN);

        this.hPiecesCLearedTitle = new Label("Pieces cleared : ");
        this.hPiecesLeftTitle = new Label("Pieces left : ");

        this.nrOfGamesTitle = new Label("Number of games : ");

        this.cPiecesCLearedTitle = new Label("Pieces cleared : ");
        this.cPiecesLeftTitle = new Label("Pieces left : ");

        nrOfMovesToWinGameTitle = new Label("Nr of pieces to move to win: ");
        nrOfMovesToWinGame = new Label("7");


    }

    public GridPane generatePanel2() {

        GridPane result = new GridPane();

        result.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        result.setHgap(20);
        result.setVgap(10);

        result.setStyle(cssLayout);

        result.add(titleLabel,0,0);
        GridPane.setColumnSpan(titleLabel,2);
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        result.add(overallLabel,0,1);
        result.add(nrOfGamesTitle,0,2);
        result.add(nrOfGamesValue,1,2);

        result.add(nrOfMovesToWinGameTitle,0,3);
        result.add(nrOfMovesToWinGame,1,3);

        result.add(playerLabel,0,4);
        result.add(hPiecesCLearedTitle,0,5);
        result.add(humaanPiecesCleared,1,5);
        result.add(hPiecesLeftTitle,0,6);
        result.add(humanPiecesLeft,1,6);
        result.add(diceRollhumanTitle,0,7);
        result.add(diceRollHuman,1,7);

        result.add(computerLabel,0,8);
        result.add(cPiecesCLearedTitle,0,9);
        result.add(computerPiecesCleared,1,9);
        result.add(cPiecesLeftTitle,0,10);
        result.add(computerPiecesLeft,1,10);

        result.add(diceRollComputerTitle,0,11);
        result.add(diceRollComputer,1,11);

        panel = result;
        return result;
    }

    public void updatePlayerNick(String nick) {

        playerLabel.setText("Player : ( " + nick + " )");
    }

    public void clear() {

        humaanPiecesCleared.setText("0");
        humanPiecesLeft.setText("7");
        computerPiecesLeft.setText("7");
        computerPiecesCleared.setText("0");
    }

    public void updateHumanDiceRoll(int val) {
        this.diceRollHuman.setText(String.valueOf(val));
    }

    public void updateComputerDiceRoll(int val) {
        this.diceRollComputer.setText(String.valueOf(val));
    }

    public void increaseGameCounter() {
        int tempVal = Integer.parseInt(nrOfGamesValue.getText());
        tempVal+=1;
        nrOfGamesValue.setText(String.valueOf(tempVal));
    }

    public void updateCurrentGameStatistics(Player player) {


        if(!player.getNick().contains("Computer")) {
            this.humaanPiecesCleared.setText(String.valueOf(player.getNrOfPiecesMoved()));
            this.humanPiecesLeft.setText(String.valueOf(player.getNrOfPiecesLeft()));
        }
        else {
            this.computerPiecesCleared.setText(String.valueOf(player.getNrOfPiecesMoved()));
            this.computerPiecesLeft.setText(String.valueOf(player.getNrOfPiecesLeft()));
        }
    }

    public void updateNrOfMovesToWin(int val) {

        nrOfMovesToWinGame.setText(String.valueOf(val));
    }

}
