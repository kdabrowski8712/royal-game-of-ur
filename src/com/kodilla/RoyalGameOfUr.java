package com.kodilla;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class RoyalGameOfUr extends Application {

    private GameBoardPanel gameBoardPanel;
    private StatisticsPanel statisticsPanel;
    private HistoryPanel historyPanel;
    private GameMenu gameMenu;
    private Human humanPlayer;
    private Computer computerPlayer;
    private boolean playerMove;
    private GameProcessor processor;
    private int diceRoll =0;


    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();
        this.gameBoardPanel = new GameBoardPanel();
        this.statisticsPanel = new StatisticsPanel();
        this.historyPanel = new HistoryPanel();
        this.gameMenu = new GameMenu();
        this.computerPlayer = new Computer(Color.GRAY);
        this.humanPlayer = new Human("Unknown",Color.YELLOW);
        processor = new GameProcessor();

        statisticsPanel.updatePlayerNick(humanPlayer.getNick());



        VBox leftBox = this.statisticsPanel.generatePanel();
        GridPane gamePane = gameBoardPanel.generateBoard();
        VBox histBox = this.historyPanel.generatePanel();
        VBox menuBox = gameMenu.initialize();

        mainPane.setLeft(leftBox);
        mainPane.setTop(menuBox);
        mainPane.setCenter(gamePane);
        mainPane.setRight(histBox);


        Scene scene2 = new Scene(mainPane,1600,1000);

        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(scene2);
        primaryStage.show();

        setEventHandlersForMenuAndButtons();
        setEventHandlerOnPieces();

    }

    private void newGameCleaning() {
        gameBoardPanel.clear();
        historyPanel.clear();
        statisticsPanel.clear();
        statisticsPanel.increaseGameCounter();
        humanPlayer.resetPlayer();
        computerPlayer.resetPlayer();
    }

    private Alert generateAlert(String title, String header, String content , Alert.AlertType type) {
        Alert res = new Alert(type);
        res.setTitle(title);
        res.setHeaderText(header);
        res.setContentText(content);

        return res;
    }

    private void setEventHandlersForMenuAndButtons() {

        gameMenu.getNewGameMenuItem().setOnAction(event -> {
                    newGameCleaning();
                    gameBoardPanel.getNewMoveButton().setDisable(false);

                     TextInputDialog dialog = new TextInputDialog("Unknown");
                     dialog.setTitle("Choose your nickname");
                     dialog.setHeaderText("Here you can define your nick");
                     dialog.setContentText("Please enter your nick:");

                    Optional<String> result = dialog.showAndWait();
                    result.ifPresent(name -> {
                        humanPlayer.setNick(result.get());
                        statisticsPanel.updatePlayerNick(result.get());
                    });

                }
        );

        gameBoardPanel.getNewMoveButton().setOnAction( event -> {

            boolean addingNewPiece=false;
            boolean moveExisting = false;
            playerMove = true;
            Alert alert = null;

            if(humanPlayer.getPiecesReadyToEnterIntoBoard()>0 && humanPlayer.getPiecesReadyToEnterIntoBoard()<7) {
                alert = generateAlert("Add new piece Question","Do you want to add new piece ?",
                        "Chose ok to add , Cancel to move piece", Alert.AlertType.CONFIRMATION);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    addingNewPiece=true;
                } else {
                   moveExisting=true;
                }
            }
            else if (humanPlayer.getPiecesReadyToEnterIntoBoard()==7) {
                addingNewPiece = true;
            }
            else if(humanPlayer.getPiecesReadyToEnterIntoBoard()==0) {
                moveExisting=true;
            }

            diceRoll = processor.generateDiceRoll();
           // System.out.println(" Dice roll: " + diceRoll);
            statisticsPanel.updateHumanDiceRoll(diceRoll);

            //alert = generateAlert("Dice roll in this move",null,
             //       "Your dice roll in this move is " + diceRoll, Alert.AlertType.INFORMATION);

            //alert.showAndWait();


            if(addingNewPiece) {

                processor.insertNewPiece(humanPlayer,computerPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);

                playerMove = false;

                diceRoll = processor.generateDiceRoll();
                statisticsPanel.updateComputerDiceRoll(diceRoll);
                boolean computerMove = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);

                if(!computerMove) {
                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
                }


                // tu logika komputera
            }

            if(moveExisting) {
                if(diceRoll==0) {
                    alert = generateAlert("No moves possible", null,
                            "0 in dice roll. No moves possible", Alert.AlertType.INFORMATION);
                }
                else {
                    alert = generateAlert("Choosing piece to move", null,
                            "Click on piece that should be moved", Alert.AlertType.INFORMATION);

                    alert.showAndWait();
                    gameBoardPanel.getNewMoveButton().setDisable(true);
                }
            }

        });



    }

    private void setEventHandlerOnPieces() {

        EventHandler<MouseEvent> circleClickEvent = (event) -> {

            if(playerMove) {
                Node current = (Node) event.getSource();
                System.out.println("Column : " + GridPane.getColumnIndex(current));
                System.out.println("Row : " + GridPane.getRowIndex(current));

                processor.movePieceHuman(GridPane.getColumnIndex(current),GridPane.getRowIndex(current),humanPlayer,computerPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);

                playerMove=false;

                diceRoll = processor.generateDiceRoll();
                statisticsPanel.updateComputerDiceRoll(diceRoll);
                boolean computerMoved = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);

                if(!computerMoved) {
                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
                }

                boolean computerWin = processor.checkWinCondition(computerPlayer);
                boolean humanWin = processor.checkWinCondition(humanPlayer);

                if(computerWin) {
                    historyPanel.addEntry("Computer win this game !!");
                    gameBoardPanel.getNewMoveButton().setDisable(true);
                }
                if(humanWin) {
                    historyPanel.addEntry("Computer win this game !!");
                    gameBoardPanel.getNewMoveButton().setDisable(true);
                }

            }

            gameBoardPanel.getNewMoveButton().setDisable(false);

            boolean computerWin = processor.checkWinCondition(computerPlayer);
            boolean humanWin = processor.checkWinCondition(humanPlayer);

            if(computerWin) {
                historyPanel.addEntry("Computer win this game !!");
                gameBoardPanel.getNewMoveButton().setDisable(true);
            }
            if(humanWin) {
                historyPanel.addEntry("Computer win this game !!");
                gameBoardPanel.getNewMoveButton().setDisable(true);
            }

        };

       for(Piece p : humanPlayer.getPlayerPieces()) {
           p.getPieceRepresentation().setOnMouseClicked(circleClickEvent);
       }

    }

    public static void main(String[] args) {
        launch(args);
    }


}
