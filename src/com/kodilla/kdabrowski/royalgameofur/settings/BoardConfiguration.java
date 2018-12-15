package com.kodilla.kdabrowski.royalgameofur.settings;

import com.kodilla.kdabrowski.royalgameofur.state.Player;
import com.kodilla.kdabrowski.royalgameofur.state.BoardCoordinates;

import java.util.ArrayList;
import java.util.List;

public class BoardConfiguration {
    private List<Rosette> fieldsWithRosette;
    private List<Integer> battleColumns;
    private int maxRowNumberOnBoard;
    private int maxColumnNumberOnBoard;
    private int humanFinalRow;
    private int humanFinalColumn;
    private int computerFinalRow;
    private int computerFinalColumn;
    private int humanStartRow;
    private int humanStartColumn;
    private int computerStartRow;
    private int computerStartColumn;


    public  BoardConfiguration() {
        fieldsWithRosette = new ArrayList<>();

        fieldsWithRosette.add(new Rosette(0,1));
        fieldsWithRosette.add(new Rosette(2,1));
        fieldsWithRosette.add(new Rosette(0,7));
        fieldsWithRosette.add(new Rosette(2,7));
        fieldsWithRosette.add(new Rosette(1,4));

        battleColumns = new ArrayList<>();

        battleColumns.add(new Integer(1));

        maxColumnNumberOnBoard=2;
        maxRowNumberOnBoard=7;
        humanFinalColumn=0;
        humanFinalRow=1;
        computerFinalColumn=2;
        computerFinalRow=1;
        humanStartRow = 4;
        humanStartColumn = 0;
        computerStartRow = 4;
        computerStartColumn = 2;

    }

    public boolean checkIfPieceIsOnRosette(BoardCoordinates pieceCoordiante) {
        boolean isOnRosette = false;

        for(int i=0; i<fieldsWithRosette.size() && !isOnRosette; i++) {
            Rosette actualRosetteField = fieldsWithRosette.get(i);
            if (pieceCoordiante.getColumn() == actualRosetteField.getRosettePosition().getColumn()
                    && pieceCoordiante.getColumn() == actualRosetteField.getRosettePosition().getRow()) {
                isOnRosette = true;
            }
        }

        return isOnRosette;
    }

    public boolean checkIfOnBattleField(BoardCoordinates pieceCoordinate) {
        boolean isOnBattleField = false;

        for(int i=0; i<battleColumns.size() && !isOnBattleField ; i++) {
            if (pieceCoordinate.getColumn() == battleColumns.get(i)) {
                isOnBattleField = true;
            }
        }

        return isOnBattleField;
    }

    public boolean checkIfOnBattleField(int columnToCheck) {
        boolean isOnBattleField = false;

        for(int i=0; i<battleColumns.size() && !isOnBattleField ; i++) {
            if (columnToCheck == battleColumns.get(i)) {
                isOnBattleField = true;
            }
        }

        return isOnBattleField;
    }

    public boolean checkIfPieceSuccesfullyMovedThroughBoard(BoardCoordinates pieceNewCoordinates ,  Player player) {
        boolean result = false;

        if (player.isHuman()) {
            if (pieceNewCoordinates.getColumn() == humanFinalColumn && pieceNewCoordinates.getRow() == (humanFinalRow+1)) {
                result = true;
            }

        } else {
            if (pieceNewCoordinates.getColumn() == computerFinalColumn && pieceNewCoordinates.getRow() == (computerFinalRow+1)) {
                result = true;
            }
        }

        return result;
    }

    public int getDistanceBetweenComputerPiecePositionAndEndOfBoard(BoardCoordinates coordinatesToCheck) {
        int result =0;
        int differenceBetweenStartPositionAndEndOfRow;
        int movesToEndStartingInRow7AndColumn2=11;
        int movesToEndStartingInRow0AndColumn2=2;
        int movesToEndStartingInRow0AndColumn1=3;

        if(coordinatesToCheck.getColumn()==maxColumnNumberOnBoard) {
            if(coordinatesToCheck.getRow()>=computerStartRow){
                differenceBetweenStartPositionAndEndOfRow = maxRowNumberOnBoard- coordinatesToCheck.getRow();
                result=movesToEndStartingInRow7AndColumn2+differenceBetweenStartPositionAndEndOfRow;
            }
            else if(coordinatesToCheck.getRow() >=computerStartRow && coordinatesToCheck.getRow()<computerFinalRow) {
                result = movesToEndStartingInRow0AndColumn2 - coordinatesToCheck.getRow();
            }

        } else if(coordinatesToCheck.getColumn()==1) {
            result=coordinatesToCheck.getRow()+movesToEndStartingInRow0AndColumn1;
        }

        return result;
    }

    public boolean checkIfPieceMovedTooFar(BoardCoordinates coordinatesToCheck ,int plannedMove) {
        boolean result = false;
        int movesToEnd;
        int movesNeededToEndAndExitFromColumn1=computerFinalRow+2;
        int movesNeededToEndAndExitFromColumn0Or2=computerFinalRow+1;

        if( coordinatesToCheck.getRow()==0 || coordinatesToCheck.getRow()==1) {

            if (coordinatesToCheck.getColumn() == 1) {
                movesToEnd = coordinatesToCheck.getRow() + movesNeededToEndAndExitFromColumn1;
                if (movesToEnd < plannedMove) {
                    result = true;
                }

            }

            if (coordinatesToCheck.getColumn() == 0 || coordinatesToCheck.getColumn() == 2) {
                movesToEnd = movesNeededToEndAndExitFromColumn0Or2 - coordinatesToCheck.getRow();
                if (movesToEnd < plannedMove) {
                    result = true;
                }
            }
        }

        return result;
    }

    public int getMaxRowNumberOnBoard() {
        return maxRowNumberOnBoard;
    }

    public int getMaxColumnNumberOnBoard() {
        return maxColumnNumberOnBoard;
    }


    public List<Rosette> getFieldsWithRosette() {
        return fieldsWithRosette;
    }
}
