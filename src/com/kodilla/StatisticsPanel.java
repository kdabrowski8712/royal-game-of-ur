package com.kodilla;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class StatisticsPanel {

    private VBox panel;

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
    private HBox titleBox;
    private HBox hPiecesClearedBox;
    private HBox hPiecesLeftBox;
    private HBox nrOfGamesBox;
    private HBox cPiecesClearedBox;
    private HBox cPiecesLeftBox;
    private HBox diceRollHumanBox;
    private HBox diceRollComputerBox;


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



        titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);

        this.hPiecesClearedBox = new HBox();
        this.hPiecesLeftBox = new HBox();
        this.hPiecesLeftBox.setSpacing(30);

        //diceRollComputerBox = new HBox();
        //diceRollHumanBox = new HBox();

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


    }

    private HBox generateTwoLabelPair(Label label1 , Label label2) {
        HBox result = new HBox();
        result.setSpacing(10);
       // result.setAlignment(Pos.L);
        result.getChildren().add(label1);
        result.getChildren().add(label2);

        return result;
    }

    public VBox generatePanel() {
        VBox result = new VBox();
        this.panel = result;

        result.setPadding(new Insets(5 , 10 ,10 ,10 ));
        result.setSpacing(8);
        result.setStyle(cssLayout);
        result.setPrefWidth(300);

        this.titleBox.getChildren().add(this.titleLabel);

        result.getChildren().add(this.titleBox);
        result.getChildren().add(this.overallLabel);
        this.nrOfGamesBox = generateTwoLabelPair(this.nrOfGamesTitle,this.nrOfGamesValue);
       // HBox.setMargin(nrOfGamesValue,new Insets(0,0,0,0));
        result.getChildren().add(this.nrOfGamesBox);

        result.getChildren().add(this.playerLabel);
        this.hPiecesClearedBox = generateTwoLabelPair(this.hPiecesCLearedTitle,humaanPiecesCleared);
        this.hPiecesLeftBox = generateTwoLabelPair(this.hPiecesLeftTitle, humanPiecesLeft);
        HBox.setMargin(humanPiecesLeft,new Insets(0,0,0,20));

        result.getChildren().add(this.hPiecesClearedBox);
        result.getChildren().add(this.hPiecesLeftBox);

        diceRollHumanBox = generateTwoLabelPair(diceRollhumanTitle,diceRollHuman);

        result.getChildren().add(diceRollHumanBox);

        result.getChildren().add(computerLabel);
        this.cPiecesClearedBox = generateTwoLabelPair(this.cPiecesCLearedTitle,this.computerPiecesCleared);
        this.cPiecesLeftBox = generateTwoLabelPair(this.cPiecesLeftTitle, this.computerPiecesLeft);
        HBox.setMargin(computerPiecesLeft,new Insets(0,0,0,20));

        result.getChildren().add(this.cPiecesClearedBox);
        result.getChildren().add(this.cPiecesLeftBox);

        diceRollComputerBox = generateTwoLabelPair(diceRollComputerTitle,diceRollComputer);

        result.getChildren().add(diceRollComputerBox);
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

    public void updateCurrentGameStatistics(GenericPlayer player) {


        if(player instanceof Human) {
            this.humaanPiecesCleared.setText(String.valueOf(player.getNrOfPiecesMoved()));
            this.humanPiecesLeft.setText(String.valueOf(player.getNrOfPiecesLeft()));
        }
        else {
            this.computerPiecesCleared.setText(String.valueOf(player.getNrOfPiecesMoved()));
            this.computerPiecesLeft.setText(String.valueOf(player.getNrOfPiecesLeft()));
        }
    }




    public Label getNrOfGamesValue() {
        return nrOfGamesValue;
    }

    public void setNrOfGamesValue(Label nrOfGamesValue) {
        this.nrOfGamesValue = nrOfGamesValue;
    }

    public Label getHumaanPiecesCleared() {
        return humaanPiecesCleared;
    }

    public void setHumaanPiecesCleared(Label humaanPiecesCleared) {
        this.humaanPiecesCleared = humaanPiecesCleared;
    }

    public Label getHumanPiecesLeft() {
        return humanPiecesLeft;
    }

    public void setHumanPiecesLeft(Label humanPiecesLeft) {
        this.humanPiecesLeft = humanPiecesLeft;
    }

    public Label getComputerPiecesLeft() {
        return computerPiecesLeft;
    }

    public void setComputerPiecesLeft(Label computerPiecesLeft) {
        this.computerPiecesLeft = computerPiecesLeft;
    }

    public Label getComputerPiecesCleared() {
        return computerPiecesCleared;
    }

    public void setComputerPiecesCleared(Label computerPiecesCleared) {
        this.computerPiecesCleared = computerPiecesCleared;
    }


}
