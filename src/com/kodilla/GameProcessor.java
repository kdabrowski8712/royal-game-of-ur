package com.kodilla;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameProcessor {

    private Alert generateAlert(String title, String header, String content, Alert.AlertType type) {
        Alert res = new Alert(type);
        res.setTitle(title);
        res.setHeaderText(header);
        res.setContentText(content);

        return res;
    }

    private boolean checkCollision(int column, int row, List<Piece> possibleCollisions) {
        boolean found = false;

        Iterator<Piece> pieceIterator = possibleCollisions.iterator();

        while (!found && pieceIterator.hasNext()) {

            Piece p = pieceIterator.next();

            if (p.isOnBoard()) {
                if (p.getColumnPositionOnBoard() == column && p.getRowPositionOnBoard() == row) {
                    found = true;
                }
            }

        }

        return found;
    }

    private boolean checkRosette(int column, int row) {
        boolean isOnRosette = false;

        if (column == 1 && row == 4) {
            isOnRosette = true;
        }

        return isOnRosette;
    }

    private boolean checkIfOnBattleField(int column, int row) {
        boolean isOnBattleField = false;

        if (column == 1) {
            isOnBattleField = true;
        }
        return isOnBattleField;
    }

    private int generateNewColumnCoordinate(int column, int row, int move, GenericPlayer player) {
        int result = 0;
        int temp;

        if (column == 0 || column == 2) {
            temp = row + move;
            if (temp <= 7) {
                result = column;
            } else {
                result = 1;
            }

        }

        if (column == 1) {
            temp = row - move;
            if (temp >= 0) {
                result = column;
            } else {
                if (player instanceof Human) {
                    result = 0;
                } else if (player instanceof Computer) {
                    result = 2;
                }
            }
        }

        return result;
    }

    private int generateNewRowCoordinate(int column, int row, int move, GenericPlayer player) {
        int result = 0;
        int temp;
        int restMoves = 0;

        if (column == 0 || column == 2) {
            temp = row + move;
            if (temp <= 7) {
                result = temp;
            } else {

                temp = 7 - row; // move to end of board
                restMoves = move - temp;
                restMoves -= 1;
                result = 7 - restMoves;
            }
        }

        if (column == 1) {
            temp = row - move;
            if (temp >= 0) {
                result = temp;
            } else {
                temp = row;
                restMoves = move - temp;
                restMoves -= 1;
                result = 0 + restMoves;
            }
        }

        return result;
    }

    private boolean checkIfPieceSuccesfullyMovedThroughBoard(int column, int row, GenericPlayer player) {
        boolean result = false;

        if (player instanceof Human) {
            if (column == 0 && row == 2) {
                result = true;
            }

        } else {
            if (column == 2 && row == 2) {
                result = true;
            }
        }

        return result;
    }

    private boolean checkIfPieceMovedTooFar(int row, int column, GenericPlayer player) {
        boolean result = false;

        if (player instanceof Human) {

            if (column == 0 && row > 2 && row<4) {
                result = true;
            }

        } else {
            if (column == 2 && row > 2 && row<4) {
                result = true;
            }

        }


        return result;
    }

    public int generateDiceRoll() {
        Random r = new Random();
        int result = 0;
        result = r.nextInt(5);


        return result;
    }

    private void commonInsertAndHumanMoveActions(Piece p, GenericPlayer player, GenericPlayer oponent, StatisticsPanel statisticsPanel,  GameBoardPanel board, HistoryPanel history, int newRow, int newCol) {


        boolean isOnBattleField = false;
        boolean isOnRosette = false;

        isOnBattleField = checkIfOnBattleField(newCol, newRow);
        isOnRosette = checkRosette(newCol, newRow);

        if (isOnBattleField && !isOnRosette) {
            Piece oponentPiece = oponent.getPieceByCoordinates(newCol, newRow);
            board.removePiece(oponentPiece);
            oponentPiece.setOnBoard(false);
            oponentPiece.resetPosition(oponent);
            oponent.piecesStatisticRecalculation();
            statisticsPanel.updateCurrentGameStatistics(oponent);

            board.addPiece(p, newRow, newCol);
            p.setOnBoard(true);
            p.setRowPositionOnBoard(newRow);
            p.setColumnPositionOnBoard(newCol);


            history.addEntry("Player " + player.getNick() + " captured " + oponent.getNick() + "on position {" +
                    newCol + "," + newRow + ")");

        } else {
            Alert alert = generateAlert("Rosette Alert", null,
                    "Your cannot capture oponent piece to position (" + newCol + "," + newRow + ") in this move - rosette field.", Alert.AlertType.INFORMATION);
            alert.showAndWait();
        }


    }

    public void insertNewPieceHuman(GenericPlayer player, GenericPlayer oponent, GameBoardPanel board, HistoryPanel history, StatisticsPanel statisticsPanel, int move) {

        int newCol = 0;
        int newRow = 0;
        boolean isInCollisionWithPlayer = false;
        boolean isInCollisionWithOponent = false;

        Piece p = player.getFreePiece();

        newCol = generateNewColumnCoordinate(p.getColumnPositionOnBoard(), p.getRowPositionOnBoard(), move, player);
        newRow = generateNewRowCoordinate(p.getColumnPositionOnBoard(), p.getRowPositionOnBoard(), move, player);

        isInCollisionWithPlayer = checkCollision(newCol, newRow, player.getPlayerPieces());

        if (isInCollisionWithPlayer) {
            Alert alert = generateAlert("Collision Allert", null,
                    "Your cannot put piece to position (" + newCol + "," + newRow + ") in this move - collision with another piece.", Alert.AlertType.INFORMATION);

            alert.showAndWait();
        } else {

            isInCollisionWithOponent = checkCollision(newCol, newRow, oponent.getPlayerPieces());
            if (isInCollisionWithOponent) {

                commonInsertAndHumanMoveActions(p, player, oponent, statisticsPanel, board, history, newRow, newCol);


            } else {
                board.addPiece(p, newRow, newCol);
                p.setOnBoard(true);
                p.setColumnPositionOnBoard(newCol);
                p.setRowPositionOnBoard(newRow);

                history.addEntry("Player " + player.getNick() + " added piece on position ("
                        + newCol + "," + newRow + ")");
            }
        }


    }

    public void movePieceHuman(int clickedPieceColumn, int clickedPieceRow, GenericPlayer player, GenericPlayer oponent, GameBoardPanel board, HistoryPanel history, StatisticsPanel stPanel, int move) {
        int newCol = 0;
        int newRow = 0;
        int oldCol = 0;
        int oldRow=0;
        boolean isMovedFromBoard = false;
        boolean cannotBeMovedTooFar = false;
        boolean isInCollisionWithPlayer = false;
        boolean isInCollisionWithOponent = false;


        Piece p = player.getPieceByCoordinates(clickedPieceColumn, clickedPieceRow);

            if (p != null) {

                oldCol = p.getColumnPositionOnBoard();
                oldRow = p.getRowPositionOnBoard();

                newCol = generateNewColumnCoordinate(p.getColumnPositionOnBoard(), p.getRowPositionOnBoard(), move, player);
                newRow = generateNewRowCoordinate(p.getColumnPositionOnBoard(), p.getRowPositionOnBoard(), move, player);

                isMovedFromBoard = checkIfPieceSuccesfullyMovedThroughBoard(newCol, newRow, player);
                cannotBeMovedTooFar = checkIfPieceMovedTooFar(newRow, newCol, player);
                isInCollisionWithPlayer = checkCollision(newCol, newRow, player.getPlayerPieces());

                if (isMovedFromBoard) {
                    Alert alert = generateAlert("Piece moved", null,
                            "Gratulations - piece on position (" + newCol + "," + newRow + ") succesfully left the board", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                    history.addEntry("Player " + player.getNick() + "succesfully moved piece on position ( " +  newCol + "," + newRow + ") out of the board"  );

                    p.setOnBoard(false);
                    p.setMovedThroughBoard(true);
                    player.piecesStatisticRecalculation();
                    stPanel.updateCurrentGameStatistics(player);
                    board.removePiece(p);
                }

                if (cannotBeMovedTooFar) {
                    Alert alert = generateAlert("Piece moved too far", null,
                            "Piece on position (" + newCol + "," + newRow + ") cannot be moved - too far", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                }

                if (isInCollisionWithPlayer) {
                    Alert alert = generateAlert("Collision Alert", null,
                            "Your cannot put piece to position (" + newCol + "," + newRow + ") in this move - collision with another piece.", Alert.AlertType.INFORMATION);

                    alert.showAndWait();
                } else {

                    isInCollisionWithOponent = checkCollision(newCol, newRow, oponent.getPlayerPieces());
                    if (isInCollisionWithOponent) {

                        commonInsertAndHumanMoveActions(p, player, oponent,stPanel, board, history, newRow, newCol);

                    } else {
                        board.movePiece(p, newRow, newCol);
                        p.setColumnPositionOnBoard(newCol);
                        p.setRowPositionOnBoard(newRow);
                        history.addEntry("Player " + player.getNick() + " moved piece from (" + oldCol + "," + oldRow + ") to (" + newCol + "," + newRow + ")");

                    }

                }

            }

    }

    public boolean movePieceComputers(Computer computer, Human human, StatisticsPanel statPanel,  GameBoardPanel board , HistoryPanel historyPanel, int move){
        boolean moved = false;
        boolean collisionWithHuman = false;
        // Plan jest taki
        // 1. patrzymy czy mamy jakies ruchy
        List<Piece> possibleMoves = returnComputerPossibleMoves(computer,move);
        if(possibleMoves!=null && possibleMoves.size()>0){
            Piece pieceToRemove = returnComputerPieceToRemove(possibleMoves,move,computer);
            if(pieceToRemove!=null) {
                pieceToRemove.setOnBoard(false);
                pieceToRemove.setMovedThroughBoard(true);
                computer.piecesStatisticRecalculation();
                statPanel.updateCurrentGameStatistics(computer);
                board.removePiece(pieceToRemove);
                historyPanel.addEntry("Computer succesfully moved piece on position (" + pieceToRemove.getColumnPositionOnBoard() + "," +
                        pieceToRemove.getRowPositionOnBoard() + ") from the board.");
                moved = true;
            }
            else {
                Piece closestPieceToMove = getPieceClosestToExit(possibleMoves);
                collisionWithHuman = checkCollision(closestPieceToMove.getPossibleNewColumn(),closestPieceToMove.getPossibleNewRow(),human.getPlayerPieces());
                if(collisionWithHuman) {
                    Piece humanPieceToRemove = human.getPieceByCoordinates(closestPieceToMove.getPossibleNewColumn(),closestPieceToMove.getPossibleNewRow());
                    humanPieceToRemove.setOnBoard(false);
                    human.piecesStatisticRecalculation();
                    humanPieceToRemove.resetPosition(human);
                    statPanel.updateCurrentGameStatistics(human);
                    board.removePiece(humanPieceToRemove);
                    board.movePiece(closestPieceToMove,closestPieceToMove.getPossibleNewRow(),closestPieceToMove.getPossibleNewColumn());
                    closestPieceToMove.setColumnPositionOnBoard(closestPieceToMove.getPossibleNewColumn());
                    closestPieceToMove.setRowPositionOnBoard(closestPieceToMove.getPossibleNewRow());
                    historyPanel.addEntry("Computer remved player piece on position( " + closestPieceToMove.getColumnPositionOnBoard() + "," +
                            closestPieceToMove.getRowPositionOnBoard() + " )");
                }
                else {
                    board.movePiece(closestPieceToMove,closestPieceToMove.getPossibleNewRow(),closestPieceToMove.getPossibleNewColumn());
                    historyPanel.addEntry("Computer moved piece  from position (" + closestPieceToMove.getColumnPositionOnBoard() + ","
                    + closestPieceToMove.getRowPositionOnBoard() + " ) to position " + closestPieceToMove.getPossibleNewColumn() + "," +
                    closestPieceToMove.getPossibleNewRow() + " )");

                    closestPieceToMove.setRowPositionOnBoard(closestPieceToMove.getPossibleNewRow());
                    closestPieceToMove.setColumnPositionOnBoard(closestPieceToMove.getPossibleNewColumn());
                }
                moved=true;

            }

        }
        // 3. jak nie toruszamy tym ktory najblizej
        // 4. jak mamy pionek przeciwnika to zbijamy
        // 5. jak sie nie da to proba dostawienia pionka

        return moved;
    }

    private Piece getPieceClosestToExit(List<Piece> input) {
        Piece result = null;
        int lastDistance = 0;
        int actualDistance = 0;

        for(Piece p : input) {
            actualDistance = getDistanceBetweenComputerPiecePositionAndEndOfBoard(p.getColumnPositionOnBoard(),p.getRowPositionOnBoard());
            if(lastDistance==0) {
                lastDistance = actualDistance;
                result = p;
            }
            else {
                if(actualDistance<lastDistance) {
                    lastDistance = actualDistance;
                    result = p;
                }
            }
        }


        return  result;
    }

    private List<Piece> returnComputerPossibleMoves(Computer computer, int move) {
        List<Piece> result = new ArrayList<>();
        int newColumn;
        int newRow;
        boolean isOffTheBoard;
        boolean isIncollisionWithPlayer;

        for(Piece p : computer.getPlayerPieces()) {

            if(p.isOnBoard()) {
                newColumn = generateNewColumnCoordinate(p.getColumnPositionOnBoard(), p.getRowPositionOnBoard(), move, computer);
                newRow = generateNewRowCoordinate(p.getColumnPositionOnBoard(), p.getRowPositionOnBoard(), move, computer);

                isOffTheBoard = checkIfPieceMovedTooFar(newRow, newColumn, computer);
                isIncollisionWithPlayer = checkCollision(newColumn, newRow, computer.getPlayerPieces());

                if (!isIncollisionWithPlayer && !isOffTheBoard) {
                    result.add(p);
                    p.setPossibleNewColumn(newColumn);
                    p.setPossibleNewRow(newRow);
                }
            }

        }


        return  result;
    }

    private Piece returnComputerPieceToRemove (List<Piece> input , int move, Computer computer) {
        Piece result = null;
        boolean end = false;
        int newColumn;
        int newRow;
        boolean canBeMoved = false;

        Iterator<Piece> pieceIterator = input.iterator();

        while(!end && pieceIterator.hasNext()) {
            Piece p = pieceIterator.next();

            canBeMoved = checkIfPieceSuccesfullyMovedThroughBoard(p.getPossibleNewColumn(),p.getPossibleNewRow(),computer);

            if(canBeMoved) {
                end = true;
                result = p;
            }

        }

        return  result;
    }

    private int getDistanceBetweenComputerPiecePositionAndEndOfBoard(int column, int row) {
        int result =0;
        int difference;

        if(column==2) {

           if(row>=4){
               difference = 7- row;
               result=11+difference;
           }
           else if(row >=0 && row<1) {
                result = 2 - row;
           }

        } else if(column==1) {
            result=row+3;
        }

        return result;
    }








}


