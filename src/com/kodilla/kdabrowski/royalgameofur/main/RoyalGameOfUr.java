package com.kodilla.kdabrowski.royalgameofur.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class RoyalGameOfUr extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        UrGame game = new UrGame(primaryStage);
        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(game.getSceneToRender());
        primaryStage.show();

    }

//    private EventHandler<ActionEvent> createHandlerForNewGameMenuItem(GameState state) {
//
//        EventHandler<ActionEvent> newGameMenuItemEvent = (event) -> {
//
//            gameUserInterface.resetForNewGame();
//            gameState.getHuman().resetPlayer();
//            gameState.getComputer().resetPlayer();
//
//            TextInputDialog dialog = new TextInputDialog("Unknown");
//            dialog.setTitle("Choose your nickname");
//            dialog.setHeaderText("Here you can define your nick");
//            dialog.setContentText("Please enter your nick:");
//
//            Optional<String> result = dialog.showAndWait();
//            result.ifPresent(name -> {
//                state.getHuman().setNick(result.get());
//                gameUserInterface.updatePlayerNick(result.get());
//            });
//            gameUserInterface.updateNumberOfMovesToWin(gameSettings.getNrOfPiecesToWin());
//            gameUserInterface.disableEditSettingsMenuItem();
//        };
//
//        return  newGameMenuItemEvent;
//    }
//
//    public  EventHandler<ActionEvent> createHandlerForGameSettingsMenuItem()  {
//
//        EventHandler<javafx.event.ActionEvent> gameSettingsMenuItemEvent = (event) -> {
//
//            Dialog<GameSettings> gSettingDialog = gameSettings.buildDialogForGameSettings();
//            Optional<GameSettings> result = gSettingDialog.showAndWait();
//
//            if(result.isPresent()) {
//                GameSettings gameSettingsToCopy = result.get();
//
//                gameSettings.copy(gameSettingsToCopy);
//                gameState.updateGameState(gameSettings);
//            }
//        };
//
//        return gameSettingsMenuItemEvent;
//    }
//
//    private EventHandler<ActionEvent> createEventHandlerForNewMoveButton() {
//        EventHandler<javafx.event.ActionEvent> newGameMoveButtonEvent = (event) ->{
//
//            boolean addingNewPiece=checkIfHumanPlayerWantToInsertNewPiece(gameState);
//
//            Alert alert = null;
//            gameState.setPlayerMove(true);
//
//            if(addingNewPiece) {
//               // System.out.println("czlowiek dodaje pionek");
//                GameMove humanMove = new GameMove(true,true,boardConfiguration,gameState);
//                gameUserInterface.updateMoveData(humanMove);
//                humanMove.preparePlayerPiecesForInsert();
//                gameUserInterface.handleInsertGameMove(humanMove);
//                gameState.setPlayerMove(false);
//
//              //  System.out.println("po czlowieku ktory dodaje");
//
//                performComputerMove(boardConfiguration,gameState);
//                gameUserInterface.updateStatistics(gameState);
//
//            }
//
//            if(!addingNewPiece) {
//                    alert = UITools.generateAlert("Choosing piece to move", null,
//                            "Click on piece that should be moved", Alert.AlertType.INFORMATION);
//
//                    alert.showAndWait();
//                    gameUserInterface.disableewMoveButton();
//                    gameState.setPlayerMove(true);
//
//                GameMove humanMove = new GameMove(true, false, boardConfiguration, gameState,
//                        0, 0);
//                gameUserInterface.updateMoveData(humanMove);
//                gameState.setHumanMoveToPassToOnClickHandler(humanMove);
//            }
//        };
//
//        return newGameMoveButtonEvent;
//    }
//
//    private EventHandler<MouseEvent> createEventForPieces() {
//        EventHandler<MouseEvent> result = (event -> {
//
//            if(gameState.isPlayerMove()) {
//
//                Node current = (Node) event.getSource();
//
//                GameMove humanMove = gameState.getHumanMoveToPassToOnClickHandler();
//                humanMove.getClickedCoordiantes().setColumn(GridPane.getColumnIndex(current));
//                humanMove.getClickedCoordiantes().setRow(GridPane.getRowIndex(current));
//
//
//                if (humanMove.getDiceRoll() != 0) {
//
//                    humanMove.preparePlayerPieceForMove();
//                    gameUserInterface.handleMoveGameMove(humanMove);
//                }
//                else {
//
//                    gameUserInterface.handleZeroRollWarning(humanMove);
//                }
//
//                performComputerMove(boardConfiguration, gameState);
//                gameUserInterface.updateStatistics(gameState);
//
//                if (gameState.getComputer().checkWinCondition(gameSettings)) {
//                    gameUserInterface.changeStateAfterWin(gameState.getComputer().getNick());
//
//                } else if (gameState.getHuman().checkWinCondition(gameSettings)) {
//                    gameUserInterface.changeStateAfterWin(gameState.getHuman().getNick());
//
//                } else {
//
//                    gameState.setPlayerMove(false);
//                    gameUserInterface.enableNewMoveButton();
//
//                }
//            }
//        });
//
//        return result;
//    }
//
//    private void performComputerMove(BoardConfiguration boardConfiguration , GameState gameState) {
//
//        GameMove computerMove = new GameMove(false,false,boardConfiguration,gameState);
//        gameUserInterface.updateMoveData(computerMove);
//        computerMove.preparePlayerPieceForMove();
//
//        if(computerMove.isMovePossible()) {
//            gameUserInterface.handleMoveGameMove(computerMove);
//        }
//        else {
//            computerMove.setInsertMove(true);
//            computerMove.preparePlayerPiecesForInsert();
//            gameUserInterface.handleInsertGameMove(computerMove);
//        }
//
//    }
//
//    private boolean checkIfHumanPlayerWantToInsertNewPiece(GameState state) {
//        boolean decision= false;
//        Alert alert;
//
//        if(!state.getHuman().checkIfPlayerHaveAllPiecesOutOfBoard()) {
//            alert = UITools.generateAlert("Add new piece Question","Do you want to add new piece ?",
//                    "Chose OK to add , Cancel to move piece", Alert.AlertType.CONFIRMATION);
//
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK){
//                decision=true;
//            }
//        }
//        else if (state.getHuman().getPiecesReadyToEnterIntoBoard()==7) {
//            decision = true;
//        }
//
//        return decision;
//    }

  //  private void setEventHandlersForMenuAndButtons() {
//



//        gameUserInterface.gameMenu.getNewGameMenuItem().setOnAction(event -> {
//                    newGameCleaning();
//
//                     TextInputDialog dialog = new TextInputDialog("Unknown");
//                     dialog.setTitle("Choose your nickname");
//                     dialog.setHeaderText("Here you can define your nick");
//                     dialog.setContentText("Please enter your nick:");
//
//                    Optional<String> result = dialog.showAndWait();
//                    result.ifPresent(name -> {
//                        gameUserInterface.updatePlayerNick(result.get());
//                    });
//                    gameUserInterface.updateNumberOfMovesToWin(gameSettings.getNrOfPiecesToWin());
//                }
//        );

//        gameMenu.getGameSettings().setOnAction(event -> {
//            Dialog<GameSettings> gSettingDialog = gameSettings.buildDialogForGameSettings();
//            Optional<GameSettings> result = gSettingDialog.showAndWait();
//
//            if(result.isPresent()) {
//                GameSettings tempSettings = result.get();
//                gameSettings.setComputerColor(tempSettings.getComputerColor());
//                gameSettings.setHumanColor(tempSettings.getHumanColor());
//                gameSettings.setNrOfPiecesToWin(tempSettings.getNrOfPiecesToWin());
//
//                humanPlayer.setPiecesColor(gameSettings.getHumanColor());
//                computerPlayer.setPiecesColor(gameSettings.getComputerColor());
//
//            }
//
//        });

        //gameBoardPanel.getNewMoveButton().setOnAction( event -> {

//            boolean addingNewPiece=false;
//            boolean moveExisting = false;
//            playerMove = true;
//            Alert alert = null;
//
//            if(humanPlayer.getPiecesReadyToEnterIntoBoard()>0 && humanPlayer.getPiecesReadyToEnterIntoBoard()<7) {
//                alert = UITools.generateAlert("Add new piece Question","Do you want to add new piece ?",
//                        "Chose OK to add , Cancel to move piece", Alert.AlertType.CONFIRMATION);
//
//                Optional<ButtonType> result = alert.showAndWait();
//                if (result.get() == ButtonType.OK){
//                    addingNewPiece=true;
//                } else {
//                   moveExisting=true;
//                }
//            }
//            else if (humanPlayer.getPiecesReadyToEnterIntoBoard()==7) {
//                addingNewPiece = true;
//            }
//            else if(humanPlayer.getPiecesReadyToEnterIntoBoard()==0) {
//                moveExisting=true;
//            }

//            diceRoll = processor.generateDiceRoll();
//            statisticsPanel.updateHumanDiceRoll(diceRoll);
//
//            if(addingNewPiece) {
//
//                processor.insertNewPiece(humanPlayer,computerPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
//                playerMove = false;
//
//                diceRoll = processor.generateDiceRoll();
//                statisticsPanel.updateComputerDiceRoll(diceRoll);
//
//                boolean computerMove = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);
//
//                if(!computerMove) {
//                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
//                }
//
//            }
//
//            if(moveExisting) {
//                if(diceRoll==0) {
//                    alert = UITools.generateAlert("No moves possible", null,
//                            "0 in dice roll. No move possible", Alert.AlertType.INFORMATION);
//                    alert.showAndWait();
//                }
//                else {
//                    alert = UITools.generateAlert("Choosing piece to move", null,
//                            "Click on piece that should be moved", Alert.AlertType.INFORMATION);
//
//                    alert.showAndWait();
//                    gameBoardPanel.getNewMoveButton().setDisable(true);
//                }
//            }
//
//        });
//
//    }
//
//    private void setEventHandlerOnPieces() {
//
//        EventHandler<MouseEvent> circleClickEvent = (event) -> {
//
//            if(playerMove) {
//                Node current = (Node) event.getSource();
//                System.out.println("Column : " + GridPane.getColumnIndex(current));
//                System.out.println("Row : " + GridPane.getRowIndex(current));
//
//                if(diceRoll!=0) {
//                    processor.movePieceHuman(GridPane.getColumnIndex(current), GridPane.getRowIndex(current), humanPlayer, computerPlayer, gameBoardPanel, historyPanel, statisticsPanel, diceRoll);
//                }
//
//                diceRoll = processor.generateDiceRoll();
//                statisticsPanel.updateComputerDiceRoll(diceRoll);
//
//                boolean computerMoveExecuted = processor.movePieceComputers(computerPlayer,humanPlayer,statisticsPanel,gameBoardPanel,historyPanel,diceRoll);
//
//                if(!computerMoveExecuted) {
//                    processor.insertNewPiece(computerPlayer,humanPlayer,gameBoardPanel,historyPanel,statisticsPanel,diceRoll);
//                }
//
//                if(computerPlayer.checkWinCondition(gameSettings)) {
//                    gameUserInterface.changeStateAfterWin(computerPlayer.getNick());
//
//                }
//                else if(humanPlayer.checkWinCondition(gameSettings)) {
//                    gameUserInterface.changeStateAfterWin(humanPlayer.getNick());
//
//                } else {
//
//                    playerMove = false;
//                    //gameBoardPanel.getNewMoveButton().setDisable(false);
//                    gameUserInterface.enableNewMoveButton();
//
//                }
//            }
//
//        };

//       for(Piece p : humanPlayer.getPlayerPieces()) {
//           p.getPieceRepresentation().setOnMouseClicked(circleClickEvent);
//       }
//
//    }

    public static void main(String[] args) {
        launch(args);
    }

}
