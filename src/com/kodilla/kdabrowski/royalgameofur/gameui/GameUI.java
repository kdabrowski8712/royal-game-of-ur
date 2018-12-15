package com.kodilla.kdabrowski.royalgameofur.gameui;

import com.kodilla.kdabrowski.royalgameofur.state.GameMove;
import com.kodilla.kdabrowski.royalgameofur.state.Piece;
import com.kodilla.kdabrowski.royalgameofur.settings.BoardConfiguration;
import com.kodilla.kdabrowski.royalgameofur.state.BoardCoordinates;
import com.kodilla.kdabrowski.royalgameofur.state.GameState;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.event.EventHandler;

public class GameUI {
    private GameBoardPanel gameBoardPanel;
    private StatisticsPanel statisticsPanel;
    private HistoryPanel historyPanel;
    private GameMenu gameMenu;
    private BorderPane mainPane;

    public GameUI(BoardConfiguration boardConfiguration) {
        mainPane = new BorderPane();
        this.gameBoardPanel = new GameBoardPanel(boardConfiguration);
        this.statisticsPanel = new StatisticsPanel();
        this.historyPanel = new HistoryPanel();
        this.gameMenu = new GameMenu();

        GridPane gamePane = gameBoardPanel.generateBoard();
        VBox histBox = this.historyPanel.generatePanel();
        GridPane leftBox2 = statisticsPanel.generatePanel2();
        VBox menuBox = gameMenu.initialize();

        mainPane.setLeft(leftBox2);
        mainPane.setTop(menuBox);
        mainPane.setCenter(gamePane);
        mainPane.setRight(histBox);

    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public void updatePlayerNick(String newNick){
        statisticsPanel.updatePlayerNick(newNick);
    }

    public void updateNumberOfMovesToWin(int newNumberOfMoves) {
        statisticsPanel.updateNrOfMovesToWin(newNumberOfMoves);
    }

    public void changeStateAfterWin(String playerNick) {
        historyPanel.addEntry( playerNick + " win this game !!");
        gameBoardPanel.getNewMoveButton().setDisable(true);
        gameMenu.getGameSettings().setDisable(false);

    }

    public void resetForNewGame() {
        gameBoardPanel.clear();
        historyPanel.clear();
        statisticsPanel.clear();
        statisticsPanel.increaseGameCounter();
        gameMenu.getGameSettings().setDisable(true);
        gameBoardPanel.getNewMoveButton().setDisable(false);
    }

    public void resetForNewGameWhenTimeOut() {
        gameBoardPanel.clear();
        historyPanel.clear();
        statisticsPanel.clear();
        gameMenu.getGameSettings().setDisable(false);
        gameBoardPanel.getNewMoveButton().setDisable(true);
        statisticsPanel.updatePlayerNick("Unknown");
    }

    public void enableNewMoveButton() {
        gameBoardPanel.getNewMoveButton().setDisable(false);
    }

    public void disableewMoveButton() {
        gameBoardPanel.getNewMoveButton().setDisable(true);
    }

    public void updateStatistics(GameState state) {

        statisticsPanel.updateCurrentGameStatistics(state.getComputer());
        statisticsPanel.updateCurrentGameStatistics(state.getHuman());
    }

    public void updateMoveData(GameMove move) {
        if(move.isDoneByHumanPlayer()) {
            statisticsPanel.updateHumanDiceRoll(move.getDiceRoll());
        }
        else {
            statisticsPanel.updateComputerDiceRoll(move.getDiceRoll());
        }
    }

    private void handleInsertWhenCollisionWithOponent(GameMove move) {
        gameBoardPanel.removePiece(move.getOponentPieceInCollision());
        move.updatePlayerPieceCoordination();
        gameBoardPanel.addPiece(move.getPieceToDoAction());
        move.getPieceToDoAction().setOnBoard(true);

        historyPanel.addEntry(move.getPlayer().getNick() + " inserted and  captured " + move.getOponent().getNick() + "on position {" +
                move.getPieceNewCoordinates().getColumn() + "," + move.getPieceNewCoordinates().getRow() + ")");
    }

    private void handleMoveWhenCollisionWithOponent(GameMove move) {
        gameBoardPanel.removePiece(move.getOponentPieceInCollision());
        move.updatePlayerPieceCoordination();
        gameBoardPanel.movePiece(move.getPieceToDoAction(),move.getPieceNewCoordinates());

        historyPanel.addEntry(move.getPlayer().getNick() + " moved and captured " + move.getOponent().getNick() + "on position {" +
                move.getPieceNewCoordinates().getColumn() + "," + move.getPieceNewCoordinates().getRow() + ")");
    }

    private void handleRosetteWarning(GameMove move) {

        BoardCoordinates oponentPieceCoordinates = move.getOponentPieceInCollision().getPiecePositionOnBoard();
        if (move.getPlayer().isHuman()) {

            Alert alert = UITools.generateAlert("Rosette Alert", null,
                    "You can not capture oponent piece on position ("
                            + oponentPieceCoordinates.getColumn() + "," + oponentPieceCoordinates.getRow() + ") - rosette field.", Alert.AlertType.INFORMATION);
            alert.showAndWait();
            historyPanel.addEntry( "Player " + move.getPlayer().getNick() + " could not capture oponent piece on position ("
                    + oponentPieceCoordinates.getColumn() + "," + oponentPieceCoordinates.getRow() + " ) - rosette field.");
        } else {
            historyPanel.addEntry("Computer could not capture oponent piece on position ("
                    + oponentPieceCoordinates.getColumn()  + "," + oponentPieceCoordinates.getRow() + " ) - rosette field.");
        }
    }

    private void handleOwnPieceCollisionWarning(GameMove move) {

        if (move.getPlayer().isHuman()) {
            Alert alert = UITools.generateAlert("Collision Alert", null,
                    "You can not put piece to position ("
                            + move.getPieceToDoAction().getPiecePositionOnBoard().getColumn() + ","
                            + move.getPieceToDoAction().getPiecePositionOnBoard().getRow() +
                            ") -  collision with another piece.", Alert.AlertType.INFORMATION);

            alert.showAndWait();
            historyPanel.addEntry("Player could not  put piece to position ("
                    + move.getPieceToDoAction().getPiecePositionOnBoard().getColumn() + ","
                    + move.getPieceToDoAction().getPiecePositionOnBoard().getRow() +
                    ") -  collision with another piece.");
        }
        else {
            historyPanel.addEntry("Computer could not put piece to position ("
                    + move.getPieceToDoAction().getPiecePositionOnBoard().getColumn() + ","
                    + move.getPieceToDoAction().getPiecePositionOnBoard().getRow() +
                    ") -  collision with another piece.");
        }


    }

    private void handlePieceMovedTooFarWarning(GameMove move) {


        if(move.getPlayer().isHuman()){
            Alert alert = UITools.generateAlert("Piece moved too far", null,
                    "Piece on position (" + move.getPieceToDoAction().getPiecePositionOnBoard().getColumn() + ","
                            + move.getPieceToDoAction().getPiecePositionOnBoard().getRow() + ") cannot be moved - too far", Alert.AlertType.INFORMATION);
            alert.showAndWait();

            historyPanel.addEntry("Player piece on position (" + move.getPieceToDoAction().getPiecePositionOnBoard().getColumn() + ","
                    + move.getPieceToDoAction().getPiecePositionOnBoard().getRow() + ") cannot be moved - too far");
        }
        else {

            historyPanel.addEntry("Computer piece on position (" + move.getPieceToDoAction().getPiecePositionOnBoard().getColumn() + ","
                    + move.getPieceToDoAction().getPiecePositionOnBoard().getRow() + ") cannot be moved - too far");
        }

    }

    private void handleNoFreePiecesToInsertWarning(GameMove move) {

        if(move.getPlayer().isHuman()){
            Alert alert = UITools.generateAlert("No free pieces", null,
                    "No free pieces left to insert", Alert.AlertType.INFORMATION);
            alert.showAndWait();

            historyPanel.addEntry("Player" + move.getPlayer().getNick() + " could not insert piece - " +
                    " no free pieces left.  ");
        }
        else {

            historyPanel.addEntry("Computer  could not insert piece - no free pieces left. ");
        }
    }

    public void handleZeroRollWarning(GameMove move) {
        if (move.getPlayer().isHuman()) {
            Alert alert = UITools.generateAlert("Zero roll in move", null,
                    "0 in roll - no moves possible", Alert.AlertType.INFORMATION);
            alert.showAndWait();

            historyPanel.addEntry("Player" + move.getPlayer().getNick() + " could not move piece - " +
                    " 0 in roll.  ");
        } else {

            historyPanel.addEntry("Computer  could not move piece - 0 in roll. ");

        }
    }

    private void handleNormalInsertAction(GameMove move) {

        move.updatePlayerPieceCoordination();
        gameBoardPanel.addPiece(move.getPieceToDoAction());
        move.getPieceToDoAction().setOnBoard(true);

        if(move.getPlayer().isHuman()) {
            historyPanel.addEntry("Player " + move.getPlayer().getNick() + " added piece on position ("
                    + move.getPieceNewCoordinates().getColumn() + "," + move.getPieceNewCoordinates().getRow() + ")");
        }
        else {
            historyPanel.addEntry("Computer added piece on position ("
                    + move.getPieceNewCoordinates().getColumn() + "," + move.getPieceNewCoordinates().getRow() + ")");
        }

    }

    private void handleNormalMoveAction(GameMove move) {

        int oldRow = move.getPieceToDoAction().getPiecePositionOnBoard().getRow();
        int oldColumn = move.getPieceToDoAction().getPiecePositionOnBoard().getColumn();

         move.updatePlayerPieceCoordination();
        gameBoardPanel.movePiece(move.getPieceToDoAction(), move.getPieceNewCoordinates());


        if(move.getPlayer().isHuman()) {
            historyPanel.addEntry("Player " + move.getPlayer().getNick() + " moved piece from ("
                    + oldColumn + "," + oldRow + ") to ("
                    + move.getPieceNewCoordinates().getColumn() + "," + move.getPieceNewCoordinates().getRow() + ")");
        }
        else {
            historyPanel.addEntry("Computer moved piece from ("
                    + oldColumn + "," + oldRow + ") to ("
                    + move.getPieceNewCoordinates().getColumn() + "," + move.getPieceNewCoordinates().getRow() + ")");
        }
    }

    public void handlePieceOffTheBoard(GameMove move) {

        int oldColumn = move.getPieceToDoAction().getPiecePositionOnBoard().getColumn();
        int oldRow = move.getPieceToDoAction().getPiecePositionOnBoard().getRow();

        move.updatePlayerPieceCoordination();

        move.getPieceToDoAction().setOnBoard(false);
        move.getPieceToDoAction().setMovedThroughBoard(true);
        move.getPieceToDoAction().removeFromBoard(move.getPlayer());
        statisticsPanel.updateCurrentGameStatistics(move.getPlayer());

        if(move.getPlayer().isHuman()) {
            Alert alert = UITools.generateAlert("Piece moved", null,
                    "You moved succesfully piece which was on position (" +
                            oldColumn + "," + oldRow + ")", Alert.AlertType.INFORMATION);

            historyPanel.addEntry("Player " + move.getPlayer().getNick() + "  moved succesfully piece which was on position (" +
                    oldColumn + "," + oldRow + ")");

        }
        else {
            historyPanel.addEntry("Computer moved succesfully piece which was on position (" +
                    oldColumn + "," + oldRow + ")");
        }

        gameBoardPanel.removePiece(move.getPieceToDoAction());
    }

    public void handleInsertGameMove(GameMove move) {

        if(move.isInsertMove()) {
            if(move.isMovePossible()) {
                if(move.isCollisionWithOponentPiece()  ) {
                    handleInsertWhenCollisionWithOponent(move);
                }
                else {
                    handleNormalInsertAction(move);
                }
            }
            else {
                if(!move.isPlayerHavePiecesToInsert()) {
                    handleNoFreePiecesToInsertWarning(move);
                }
                if(move.isCollisionWithAnotherPlayerPiece()) {
                    handleOwnPieceCollisionWarning(move);
                }
                if(move.isOponentPieceONRosette()) {
                    handleRosetteWarning(move);
                }
            }
        }

    }

    public void handleMoveGameMove(GameMove move) {

        if(!move.isInsertMove()) {
            if(move.isMovePossible()) {
                if(move.isCollisionWithOponentPiece()) {
                    handleMoveWhenCollisionWithOponent(move);
                }
                else if(move.isCanBeMovedOutOfBoard()) {

                    handlePieceOffTheBoard(move);
                }else {
                    handleNormalMoveAction(move);
                }
            }
            else {
                if(move.isZeroDiceRoll()) {
                    handleZeroRollWarning(move);
                }
                if(move.isMoveOutOfTheGameBoardRange()) {
                    handlePieceMovedTooFarWarning(move);
                }
                if(move.isCollisionWithAnotherPlayerPiece()) {
                    handleOwnPieceCollisionWarning(move);
                }
                if(move.isOponentPieceONRosette()) {
                    handleRosetteWarning(move);
                }
            }
        }

    }

//    public void setEventHandlerForNewGameMenuItem(EventHandler<ActionEvent> eventToSet) {
//
//        gameMenu.getNewGameMenuItem().setOnAction(eventToSet);
//    }
//
//    public  void setEventHandlerForGameSettingsMenuItem(EventHandler<ActionEvent> eventToSet) {
//        gameMenu.getGameSettings().setOnAction(eventToSet);
//    }

    public  void setEventHandlerForNewMoveButton(EventHandler<ActionEvent> eventToSet) {
        gameBoardPanel.getNewMoveButton().setOnAction(eventToSet);
    }

//    public void  setEventHandlerForSaveStateMenu(EventHandler<ActionEvent> eventToSet) {
//
//    }

    public void setMenuItemEventHandler(EventHandler<ActionEvent> eventToSet , GameMenuItemsEnum.MenuItemText chosenItem) {
        gameMenu.setEventHandlerForMenuItem(eventToSet,chosenItem);
    }

    public void setEventHandlerOnPlayerPieces(EventHandler<MouseEvent> eventToSet, GameState state) {

        for(Piece p : state.getHuman().getPlayerPieces()) {
            p.getPieceRepresentation().setOnMouseClicked(eventToSet);
        }
    }

    public void disableEditSettingsMenuItem() {
        gameMenu.getGameSettings().setDisable(true);
    }

    public void enableEditSettingsMenuItem() {
        gameMenu.getGameSettings().setDisable(false);
    }


}
