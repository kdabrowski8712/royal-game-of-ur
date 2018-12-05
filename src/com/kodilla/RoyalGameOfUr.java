package com.kodilla;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

        Piece p = new Piece(Color.BLACK);
        this.gameBoardPanel.addPiece(p,0,0);
        this.historyPanel.addEntry("test");

        gameMenu.getNewMoveMenuItem().setOnAction(event -> {
          //  this.gameBoardPanel.movePiece(p,0,1);
          //  this.historyPanel.addEntry("Piece moved from " + p.getColumnPositionOnBoard());
            this.gameBoardPanel.addPiece(p,0,0);
                }
        );

        mainPane.setLeft(leftBox);
        mainPane.setTop(menuBox);
        mainPane.setCenter(gamePane);
        mainPane.setRight(histBox);


        Scene scene2 = new Scene(mainPane,1600,950);

        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(scene2);
        primaryStage.show();

        setEventHandlersForMenu();

        List<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");

        Iterator<String> tIt = test.iterator();

        while(tIt.hasNext()) {
            System.out.println(tIt.next());
        }
    }

    private void newGameCleaning() {
        gameBoardPanel.clear();
        historyPanel.clear();
        statisticsPanel.clear();
        statisticsPanel.increaseGameCounter();
        humanPlayer.resetPlayer();
        computerPlayer.resetPlayer();
    }

    private void setEventHandlersForMenu() {

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
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Add new piece Question");
                alert.setHeaderText("Do you want to add new piece ?");
                alert.setContentText("Chose ok to add , Cancel to abort");

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

            int diceRoll = processor.generateDiceRoll();
            System.out.println(" Dice roll: " + diceRoll);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dice roll in this move");
            alert.setHeaderText(null);
            alert.setContentText("Your dice roll in this move is " + diceRoll);
            alert.showAndWait();


            if(addingNewPiece) {

                processor.insertNewPiece(humanPlayer,computerPlayer,gameBoardPanel,historyPanel,diceRoll);

                playerMove = false;
            }

            if(moveExisting) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Choosing piece to move");
                alert.setHeaderText(null);
                alert.setContentText("Click on piece that should be moved");
                alert.showAndWait();
            }



        });



    }

    public static void main(String[] args) {
        launch(args);
    }


}
