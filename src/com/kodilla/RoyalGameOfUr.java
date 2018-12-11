package com.kodilla;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

    private Alert generateAlert(String title, String header, String content , Alert.AlertType type) {
        Alert res = new Alert(type);
        res.setTitle(title);
        res.setHeaderText(header);
        res.setContentText(content);

        return res;
    }

    private Dialog<GameSettings> buildDialogForGameSettings()  {
        Dialog<GameSettings> result = new Dialog<>();
        result.setTitle("Game Settings");

        GridPane content = new GridPane();
        content.setHgap(20);
        content.setVgap(10);
        content.setPadding(new Insets(10,10,0,10));

        Label testL = new Label("Nr of cleared pieces to win:");
        Label humanPieceColor = new Label("Human piece color: ");
        Label computerPieceColor = new Label("Computer piece color:");
        TextField input = new TextField();
        input.setText("7");
        input.setDisable(true);
        CheckBox defaultCheckbox = new CheckBox("Default (7)");
        defaultCheckbox.setSelected(true);

        ColorPicker humanColorPicker = new ColorPicker(Color.YELLOW);
        ColorPicker computerColorPicker = new ColorPicker(Color.GRAY);

        content.add(testL,0,3);
        content.add(input,1,3);
        content.add(defaultCheckbox,2,3);
        content.add(humanPieceColor,0,4);
        content.add(humanColorPicker,1,4);
        content.add(computerPieceColor,0,5);
        content.add(computerColorPicker,1,5);

        result.getDialogPane().setContent(content);

        ButtonType okButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType ocancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        result.getDialogPane().getButtonTypes().add(okButton);
        result.getDialogPane().getButtonTypes().add(ocancelButton);

        defaultCheckbox.setOnAction(event -> {
            CheckBox chk = (CheckBox)event.getSource();

            if(chk.isSelected()) {
                input.setDisable(true);
                input.setText("7");
            }
            if(!chk.isSelected()) {
                input.setDisable(false);
            }

        });

        result.setResultConverter((button )-> {
            GameSettings value;
            if(button==okButton){
                try {
                     int nrOfMovesToWin = Integer.parseInt(input.getText());
                      value = new GameSettings(nrOfMovesToWin,humanColorPicker.getValue(),computerColorPicker.getValue());
                }
                catch (NumberFormatException ex) {
                    Alert alert = generateAlert("Parsing error",null,"Wrong data in text filed - default(7) will be used", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                    value = new GameSettings(7,humanColorPicker.getValue(),computerColorPicker.getValue());
                }
                return value;
            }  else {
                return null;
            }
        });

        return result;
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

        gameMenu.getGameSettings().setOnAction(event -> {
            Dialog<GameSettings> gSettingDialog = buildDialogForGameSettings();
            Optional<GameSettings> result = gSettingDialog.showAndWait();
        });

        gameBoardPanel.getNewMoveButton().setOnAction( event -> {

            boolean addingNewPiece=false;
            boolean moveExisting = false;
            playerMove = true;
            Alert alert = null;

            if(humanPlayer.getPiecesReadyToEnterIntoBoard()>0 && humanPlayer.getPiecesReadyToEnterIntoBoard()<7) {
                alert = generateAlert("Add new piece Question","Do you want to add new piece ?",
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
                    alert = generateAlert("No moves possible", null,
                            "0 in dice roll. No move possible", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
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

                if(diceRoll!=0) {
                    processor.movePieceHuman(GridPane.getColumnIndex(current), GridPane.getRowIndex(current), humanPlayer, computerPlayer, gameBoardPanel, historyPanel, statisticsPanel, diceRoll);
                }

                diceRoll = processor.generateDiceRoll();
                statisticsPanel.updateComputerDiceRoll(diceRoll);

                boolean computerMoveExecuted = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);

                if(!computerMoveExecuted) {
                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
                }

                if(computerPlayer.checkWinCondition()) {
                    historyPanel.addEntry("Computer win this game !!");
                    gameBoardPanel.getNewMoveButton().setDisable(true);
                }
                else if(humanPlayer.checkWinCondition()) {
                    historyPanel.addEntry("Computer win this game !!");
                    gameBoardPanel.getNewMoveButton().setDisable(true);
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
