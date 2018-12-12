package com.kodilla;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private GameSettings gameSettings;


    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();
        this.gameBoardPanel = new GameBoardPanel();
        this.statisticsPanel = new StatisticsPanel();
        this.historyPanel = new HistoryPanel();
        this.gameMenu = new GameMenu();
        processor = new GameProcessor();
        gameSettings = new GameSettings(7,Color.YELLOW,Color.GRAY);
        this.computerPlayer = new Computer(gameSettings.getComputerColor());
        this.humanPlayer = new Human("Unknown",gameSettings.getHumanColor());

        statisticsPanel.updatePlayerNick(humanPlayer.getNick());

        GridPane gamePane = gameBoardPanel.generateBoard();
        VBox histBox = this.historyPanel.generatePanel();
        GridPane leftBox2 = statisticsPanel.generatePanel2();
        VBox menuBox = gameMenu.initialize();

        mainPane.setLeft(leftBox2);
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



    private void setEventHandlersForMenuAndButtons() {

        gameMenu.getNewGameMenuItem().setOnAction(event -> {
                    newGameCleaning();
                    gameMenu.getGameSettings().setDisable(true);
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

        gameMenu.getGameSettings().setOnAction(event -> {
            Dialog<GameSettings> gSettingDialog = gameSettings.buildDialogForGameSettings();
            Optional<GameSettings> result = gSettingDialog.showAndWait();

            if(result.isPresent()) {
                GameSettings tempSettings = result.get();
                gameSettings.setComputerColor(tempSettings.getComputerColor());
                gameSettings.setHumanColor(tempSettings.getHumanColor());
                gameSettings.setNrOfPiecesToWin(tempSettings.getNrOfPiecesToWin());
            }

        });

        gameBoardPanel.getNewMoveButton().setOnAction( event -> {

            boolean addingNewPiece=false;
            boolean moveExisting = false;
            playerMove = true;
            Alert alert = null;

            if(humanPlayer.getPiecesReadyToEnterIntoBoard()>0 && humanPlayer.getPiecesReadyToEnterIntoBoard()<7) {
                alert = UITools.generateAlert("Add new piece Question","Do you want to add new piece ?",
                        "Chose OK to add , Cancel to move piece", Alert.AlertType.CONFIRMATION);

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
            statisticsPanel.updateHumanDiceRoll(diceRoll);

            if(addingNewPiece) {

                processor.insertNewPiece(humanPlayer,computerPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
                playerMove = false;

                diceRoll = processor.generateDiceRoll();
                statisticsPanel.updateComputerDiceRoll(diceRoll);

                boolean computerMove = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);

                if(!computerMove) {
                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
                }

            }

            if(moveExisting) {
                if(diceRoll==0) {
                    alert = UITools.generateAlert("No moves possible", null,
                            "0 in dice roll. No move possible", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                }
                else {
                    alert = UITools.generateAlert("Choosing piece to move", null,
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

                if(diceRoll!=0) {
                    processor.movePieceHuman(GridPane.getColumnIndex(current), GridPane.getRowIndex(current), humanPlayer, computerPlayer, gameBoardPanel, historyPanel, statisticsPanel, diceRoll);
                }

                diceRoll = processor.generateDiceRoll();
                statisticsPanel.updateComputerDiceRoll(diceRoll);

                boolean computerMoveExecuted = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);

                if(!computerMoveExecuted) {
                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
                }

                if(computerPlayer.checkWinCondition(gameSettings)) {
                    historyPanel.addEntry("Computer win this game !!");
                    gameBoardPanel.getNewMoveButton().setDisable(true);
                    gameMenu.getGameSettings().setDisable(false);
                }
                else if(humanPlayer.checkWinCondition(gameSettings)) {
                    historyPanel.addEntry("Computer win this game !!");
                    gameBoardPanel.getNewMoveButton().setDisable(true);
                    gameMenu.getGameSettings().setDisable(false);
                } else {

                    playerMove = false;
                    gameBoardPanel.getNewMoveButton().setDisable(false);

                }
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
