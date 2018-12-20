package com.kodilla.kdabrowski.royalgameofur.main;

import com.kodilla.kdabrowski.royalgameofur.gameui.GameMenuItemsEnum;
import com.kodilla.kdabrowski.royalgameofur.gameui.GameUI;
import com.kodilla.kdabrowski.royalgameofur.gameui.UITools;
import com.kodilla.kdabrowski.royalgameofur.iooperations.GameObjectsLoader;
import com.kodilla.kdabrowski.royalgameofur.iooperations.GameObjectsWriter;
import com.kodilla.kdabrowski.royalgameofur.settings.BoardConfiguration;
import com.kodilla.kdabrowski.royalgameofur.state.GameMove;
import com.kodilla.kdabrowski.royalgameofur.state.GameState;
import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;

import com.kodilla.kdabrowski.royalgameofur.state.LoadedState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;


public class UrGame {

    private GameSettings gameSettings;
    private BoardConfiguration boardConfiguration;
    private GameUI gameUserInterface;
    private GameState gameState;
    private Stage promaryStage;

    public  UrGame(Stage primaryStage) {
        boardConfiguration = new BoardConfiguration();
        gameSettings = new GameSettings(7, Color.YELLOW.toString(),Color.GRAY.toString(),5);
        gameState = new GameState("Unknown",gameSettings);
        gameUserInterface = new GameUI(boardConfiguration);

        gameUserInterface.setMenuItemEventHandler(createHandlerForNewGameMenuItem(gameState), GameMenuItemsEnum.NewGame);
        gameUserInterface.setMenuItemEventHandler(createHandlerForGameSettingsMenuItem(),GameMenuItemsEnum.EditSettings);
        gameUserInterface.setMenuItemEventHandler(createEventForSaveState(),GameMenuItemsEnum.SaveState);
        gameUserInterface.setMenuItemEventHandler(createEventForSaveSettings2(),GameMenuItemsEnum.SaveSettings);
        gameUserInterface.setMenuItemEventHandler(createEventForLoadSettings2(),GameMenuItemsEnum.LoadSettings);
        gameUserInterface.setMenuItemEventHandler(createEventForEndGame(),GameMenuItemsEnum.EndGame);
        gameUserInterface.setMenuItemEventHandler(createEventForLoadState(),GameMenuItemsEnum.LoadState);


        gameUserInterface.setEventHandlerForNewMoveButton(createEventHandlerForNewMoveButton());
        gameUserInterface.setEventHandlerOnPlayerPieces(createEventForPieces(),gameState);
        this.promaryStage = primaryStage;

        gameUserInterface.disableMenuItem(GameMenuItemsEnum.EndGame);

    }

    public Scene getSceneToRender() {
        Scene scene2 = new Scene(gameUserInterface.getMainPane(),1600,1000);
        return  scene2;
    }

    private EventHandler<ActionEvent> createHandlerForNewGameMenuItem(GameState state) {

        EventHandler<ActionEvent> newGameMenuItemEvent = (event) -> {

            boolean nicknameSet=false;

                if(gameState.getNrOfGames()==0) {
                    nicknameSet = askForUserName(state);
                    System.out.println("tutaj " + nicknameSet);
                }

                 if(gameState.getNrOfGames()>0 || nicknameSet ) {
                    System.out.println("tu " + nicknameSet);

                    gameUserInterface.resetForNewGame();
                    gameUserInterface.increseGameCounter();
                    gameState.increaseGamesNumber();
                    gameState.getHuman().resetPlayer();
                    gameState.getComputer().resetPlayer();

                    gameUserInterface.updateNumberOfMovesToWin(gameSettings.getNrOfPiecesToWin());

                    setTimeThread();
                }



//                TextInputDialog dialog = new TextInputDialog();
//                dialog.setTitle("Choose your nickname");
//                dialog.setHeaderText("Here you can define your nick");
//                dialog.setContentText("Please enter your nick:");
//
//                Optional<String> result = dialog.showAndWait();
//
//                if (!result.isPresent()) {
//                    gameUserInterface.disableewMoveButton();
//                    gameUserInterface.updatePlayerNick("Unknown");
//                    gameUserInterface.enableEditSettingsMenuItem();
//                    Alert alert = UITools.generateAlert("No username choose", null, "Choose username to start game", Alert.AlertType.INFORMATION);
//                    alert.showAndWait();
//                } else {
//                    gameUserInterface.enableEndGameMenuItem();
//                    gameUserInterface.resetForNewGame();
//                    gameUserInterface.increseGameCounter();
//                    gameState.increaseGamesNumber();
//                    gameState.getHuman().resetPlayer();
//                    gameState.getComputer().resetPlayer();
//
//
//                    result.ifPresent(name -> {
//                        state.getHuman().setNick(result.get());
//                        gameUserInterface.updatePlayerNick(result.get());
//                    });
//                    System.out.println("w else");
//
//                    gameUserInterface.updateNumberOfMovesToWin(gameSettings.getNrOfPiecesToWin());
//                    gameUserInterface.disableEditSettingsMenuItem();
//                    gameUserInterface.disableLoadSettingsMenuItem();
//
//                    setTimeThread();
//                }



        };

        return  newGameMenuItemEvent;
    }

    public void setTimeThread() {

        Runnable timeThread = new Runnable() {
            @Override
            public void run() {
                try {
                    int milis = gameSettings.getTimeOfPlaying()*60*1000;
                    System.out.println(milis);
                    Thread.sleep(milis);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            gameUserInterface.resetForNewGameWhenTimeOut();
                            gameState.getHuman().resetPlayer();
                            gameState.getComputer().resetPlayer();

                            Alert alert = UITools.generateAlert("Out of time",null,"Time of game has passed !!!", Alert.AlertType.INFORMATION);
                            alert.showAndWait();

                        }
                    });

                }catch(Exception e) {

                }
            }
        };

        new Thread(timeThread).start();

    }

    public  EventHandler<ActionEvent> createHandlerForGameSettingsMenuItem()  {

        EventHandler<javafx.event.ActionEvent> gameSettingsMenuItemEvent = (event) -> {

            Dialog<GameSettings> gSettingDialog = gameSettings.buildDialogForGameSettings();
            Optional<GameSettings> result = gSettingDialog.showAndWait();

            if(result.isPresent()) {
                GameSettings gameSettingsToCopy = result.get();
                gameSettings.copy(gameSettingsToCopy);
                gameState.updateGameState(gameSettings);
            }
        };

        return gameSettingsMenuItemEvent;
    }

    private boolean askForUserName(GameState state) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose your nickname");
        dialog.setHeaderText("Here you can define your nick");
        dialog.setContentText("Please enter your nick:");

        Optional<String> result = dialog.showAndWait();

        if (!result.isPresent()) {
            gameUserInterface.disableewMoveButton();
            gameUserInterface.updatePlayerNick("Unknown");
            //gameUserInterface.enableEditSettingsMenuItem();
            gameUserInterface.enableMenuItem(GameMenuItemsEnum.EditSettings);
            Alert alert = UITools.generateAlert("No username choose", null, "Choose username to start game", Alert.AlertType.INFORMATION);
            alert.showAndWait();
            return false;
        }
        else {
            state.getHuman().setNick(result.get());
            gameUserInterface.updatePlayerNick(result.get());
            return true;
        }
    }

    private EventHandler<ActionEvent> createEventHandlerForNewMoveButton() {
        EventHandler<javafx.event.ActionEvent> newGameMoveButtonEvent = (event) ->{

            boolean addingNewPiece=checkIfHumanPlayerWantToInsertNewPiece(gameState);

            Alert alert = null;
            gameState.setPlayerMove(true);

            if(addingNewPiece) {
                GameMove humanMove = new GameMove(true,true,boardConfiguration,gameState);
                gameUserInterface.updateMoveData(humanMove);
                humanMove.preparePlayerPiecesForInsert();
                gameUserInterface.handleInsertGameMove(humanMove);
                gameState.setPlayerMove(false);

                performComputerMove(boardConfiguration,gameState);
                gameUserInterface.updateStatistics(gameState);

            }

            if(!addingNewPiece) {
                alert = UITools.generateAlert("Choosing piece to move", null,
                        "Click on piece that should be moved", Alert.AlertType.INFORMATION);

                alert.showAndWait();
                gameUserInterface.disableewMoveButton();

                gameState.setPlayerMove(true);

                GameMove humanMove = new GameMove(true, false, boardConfiguration, gameState,
                        0, 0);
                gameUserInterface.updateMoveData(humanMove);
                gameState.setHumanMoveToPassToOnClickHandler(humanMove);
            }
        };

        return newGameMoveButtonEvent;
    }

    private EventHandler<MouseEvent> createEventForPieces() {
        EventHandler<MouseEvent> result = (event -> {

            if(gameState.isPlayerMove()) {

                Node current = (Node) event.getSource();

                GameMove humanMove = gameState.getHumanMoveToPassToOnClickHandler();
                humanMove.getClickedCoordiantes().setColumn(GridPane.getColumnIndex(current));
                humanMove.getClickedCoordiantes().setRow(GridPane.getRowIndex(current));


                if (humanMove.getDiceRoll() != 0) {

                    humanMove.preparePlayerPieceForMove();
                    gameUserInterface.handleMoveGameMove(humanMove);
                }
                else {

                    gameUserInterface.handleZeroRollWarning(humanMove);
                }

                performComputerMove(boardConfiguration, gameState);
                gameUserInterface.updateStatistics(gameState);

                if (gameState.getComputer().checkWinCondition(gameSettings)) {
                    gameUserInterface.changeStateAfterWin(gameState.getComputer().getNick());

                } else if (gameState.getHuman().checkWinCondition(gameSettings)) {
                    gameUserInterface.changeStateAfterWin(gameState.getHuman().getNick());

                } else {

                    gameState.setPlayerMove(false);
                    gameUserInterface.enableNewMoveButton();
                }
            }
        });

        return result;
    }

    private EventHandler<ActionEvent> createEventForSaveState() {
        EventHandler<ActionEvent> result = (event -> {

            FileChooser saveStateFile = new FileChooser();
            saveStateFile.setTitle("Save to file");
            saveStateFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("State file (.st)","*.st"));

            File file = saveStateFile.showSaveDialog(this.promaryStage);

            try {
                GameObjectsWriter.saveGameState(gameState,gameUserInterface,file);

            }catch(IOException e) {
                e.printStackTrace();
            }

        });

        return result;
    }

    private EventHandler<ActionEvent> createEventForLoadState() {
        EventHandler<ActionEvent> result = (event -> {

            FileChooser loadStateFile = new FileChooser();
            loadStateFile.setTitle("Load file");
            loadStateFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("State file (.st)","*.st"));

            File file = loadStateFile.showOpenDialog(this.promaryStage);

            System.out.println("load state handler");
            try {

                LoadedState lState = GameObjectsLoader.loadState(file);

                System.out.println(lState.getHistory().getHistryEntries().get(0));



            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    private EventHandler<ActionEvent> createEventForSaveSettings2() {
        EventHandler<ActionEvent> result = (event -> {

            System.out.println("save settings handler");
            FileChooser saveSettingsFile = new FileChooser();
            saveSettingsFile.setTitle("Save to file");
            saveSettingsFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Settings file (.set)","*.set"));

            File file = saveSettingsFile.showSaveDialog(this.promaryStage);

            if(file!=null ) {

                try{
                    GameObjectsWriter.saveGameSettingsObject(gameSettings,file);

                }catch(IOException exc) {
                    Alert alert = UITools.generateAlert("File Operation Error",null,"Error in file operation. Settings not saved", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                    exc.printStackTrace();
                }
            }
            else {
                Alert alert = UITools.generateAlert("No file choosen",null,"No file was choosen. Settings won't be saved", Alert.AlertType.INFORMATION);
                alert.showAndWait();
            }

        });

        return result;
    }

    private EventHandler<ActionEvent> createEventForLoadSettings2() {

        EventHandler<ActionEvent> result = (event -> {

            FileChooser loadSettingsFile = new FileChooser();
            loadSettingsFile.setTitle("Load  file");
            loadSettingsFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Settings file (.set)","*.set"));

           File file = loadSettingsFile.showOpenDialog(this.promaryStage);

            if(file!=null) {

                try {
                        gameSettings = GameObjectsLoader.loadGameSettings2(file);
                        gameSettings.setSettingsLoadedFromFile(true);
                } catch(Exception e) {
                    Alert alert = UITools.generateAlert("File Operation Error",null,"Error in file operation. Settings won't be loaded ", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                }

            }
            else {

                Alert alert = UITools.generateAlert("No file choosen",null,"No file was choosen. Settings won't be loaded", Alert.AlertType.INFORMATION);
                alert.showAndWait();

            }

        });

        return result;
    }

    private EventHandler<ActionEvent> createEventForEndGame() {
        EventHandler<ActionEvent> result = (event -> {

            gameUserInterface.resetForNewGame();
            gameState.getHuman().resetPlayer();
            gameState.getComputer().resetPlayer();

            //gameUserInterface.enableEditSettingsMenuItem();
            gameUserInterface.enableMenuItem(GameMenuItemsEnum.EditSettings);

            //gameUserInterface.enableLoadSettingsMenuItem();
            gameUserInterface.enableMenuItem(GameMenuItemsEnum.LoadSettings);
            gameUserInterface.enableMenuItem(GameMenuItemsEnum.SaveSettings);
            gameUserInterface.enableMenuItem(GameMenuItemsEnum.LoadState);

        });

        return result;
    }

    private void performComputerMove(BoardConfiguration boardConfiguration , GameState gameState) {

        GameMove computerMove = new GameMove(false,false,boardConfiguration,gameState);
        gameUserInterface.updateMoveData(computerMove);
        computerMove.preparePlayerPieceForMove();

        if(computerMove.isMovePossible()) {
            gameUserInterface.handleMoveGameMove(computerMove);
        }
        else {
            computerMove.setInsertMove(true);
            computerMove.preparePlayerPiecesForInsert();
            gameUserInterface.handleInsertGameMove(computerMove);
        }

    }

    private boolean checkIfHumanPlayerWantToInsertNewPiece(GameState state) {
        boolean decision= false;
        Alert alert;

        if(!state.getHuman().checkIfPlayerHaveAllPiecesOutOfBoard()) {
            alert = UITools.generateAlert("Add new piece Question","Do you want to add new piece ?",
                    "Chose OK to add , Cancel to move piece", Alert.AlertType.CONFIRMATION);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                decision=true;
            }
        }
        else if (state.getHuman().getPiecesReadyToEnterIntoBoard()==7) {
            decision = true;
        }

        return decision;
    }

}
