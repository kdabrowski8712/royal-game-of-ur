package com.kodilla;

import javafx.scene.control.Alert;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameProcessor {

    public boolean checkCollision(int column , int row , List<Piece> possibleCollisions) {
        boolean found = false;

        Iterator<Piece> pieceIterator = possibleCollisions.iterator();

        while(!found && pieceIterator.hasNext()) {

            Piece p = pieceIterator.next();

            if(p.isOnBoard()) {
                if(p.getColumnPositionOnBoard()==column && p.getRowPositionOnBoard()==row) {
                    found = true;
                }
            }

        }

        return found;
    }

    public boolean checkRosette(int column , int row) {
        boolean isOnRosette = false;

        if(column==1 && row==4) {
            isOnRosette=true;
        }

        return  isOnRosette;
    }

    public boolean checkIfOnBattleField(int column , int row) {
        boolean isOnBattleField = false;

        if(column==1 ) {
            isOnBattleField = true;
        }
        return  isOnBattleField;
    }

    public int generateNewColumnCoordinate(int column , int row , int move , GenericPlayer player) {
        int result = 0;
        int temp ;

        if(column==0 || column ==2 ) {
             temp = row+move;
            if(temp <=7) {
                result = column;
            }
            else {
                result=1;
            }

        }

        if(column==1) {
            temp = row-move;
            if(temp>=0) {
                result=column;
            }
            else {
                if(player instanceof Human) {
                    result=0;
                } else if ( player instanceof Computer) {
                    result =2;
                }
            }
        }

        return  result;
    }

    public  int generateNewRowCoordinate(int column , int row , int move , GenericPlayer player){
        int result = 0;
        int temp;
        int restMoves=0;

        if(column ==0 || column==2) {
            temp = row + move;
            if(temp <=7) {
                result = temp;
            } else {

                temp = 7-row; // move to end of board
                restMoves = move - temp;
                restMoves-=1;
                result=7-restMoves;
            }
        }

        if(column==1) {
            temp = row -move;
            if(temp>=0) {
                result=temp;
            }
            else {
                temp=row;
                restMoves=move-temp;
                restMoves-=1;
                result=0+restMoves;
            }
        }

        return  result;
    }

    public boolean checkIfPieceSuccesfullyMovedThroughBoard(int column , int row , int move , GenericPlayer player) {
        boolean result = false;

        int newColumn = generateNewColumnCoordinate(column,row,move,player);
        int newRow = generateNewRowCoordinate(column,row,move,player);

        if(player instanceof  Human ) {
            if(newColumn==0 && newRow==2) {
                result = true;
            }

        }else {
            if(newColumn==2 && newRow==2) {
                result = true;
            }
        }


        return result;
    }

    public int generateDiceRoll() {
        Random r = new Random();
        int result =0;
        result = r.nextInt(5);


        return result;
    }


    public void insertNewPiece(GenericPlayer player , GenericPlayer oponent , GameBoardPanel board, int move) {

        int newCol=0;
        int newRow=0;
        boolean isInCollisionWithPlayer = false;
        boolean isInCollisionWithOponent = false;
        boolean isOnBattleField = false;
        boolean isOnRosette= false;

        Piece p = player.getFreePiece();

        newCol = generateNewColumnCoordinate(p.getColumnPositionOnBoard(),p.getRowPositionOnBoard(),move,player);
        newRow = generateNewRowCoordinate(p.getColumnPositionOnBoard(),p.getRowPositionOnBoard(),move,player);

        isInCollisionWithPlayer = checkCollision(newCol,newRow,player.getPlayerPieces());

        if(isInCollisionWithPlayer) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Collision Allert");
            alert.setHeaderText(null);
            alert.setContentText("Your cannot put piece to position (" + newCol + "," + newRow + ") in this move - collision with another piece.");
            alert.showAndWait();
        }
        else {
            isInCollisionWithOponent = checkCollision(newCol,newRow,oponent.getPlayerPieces());
            if(isInCollisionWithOponent) {

                isOnBattleField = checkIfOnBattleField(newCol,newRow);
                isOnRosette = checkRosette(newCol,newRow);

                if(isOnBattleField && !isOnRosette) {
                    Piece oponentPiece = oponent.getPieceByCoordinates(newCol,newRow);
                    board.removePiece(oponentPiece);
                    oponentPiece.setOnBoard(false);
                    oponentPiece.resetPosition(oponent);
                    board.addPiece(p,newRow,newCol);
                    p.setOnBoard(true);
                    p.setRowPositionOnBoard(newRow);
                    p.setColumnPositionOnBoard(newCol);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Rosette Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("Your cannot put piece to position (" + newCol + "," + newRow + ") in this move - rosette field.");
                    alert.showAndWait();
                }


            } else {
                board.addPiece(p,newRow,newCol);
                p.setOnBoard(true);
                p.setColumnPositionOnBoard(newCol);
                p.setRowPositionOnBoard(newRow);
            }
        }








    }


}
