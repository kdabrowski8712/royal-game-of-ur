package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.settings.BoardConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameMove {

    private Piece pieceToDoAction;
    private Piece oponentPieceInCollision;
    private BoardConfiguration boardConfiguration;
    private GameState gameState;
    private boolean isDoneByHumanPlayer;
    private boolean insertMove;
    private int diceRoll;
    private BoardCoordinates pieceNewCoordinates;
    private boolean collisionWithAnotherPlayerPiece;
    private boolean collisionWithOponentPiece;
    private boolean isOponentPieceONRosette;
    private boolean isOponentPieceOnBattleFiels;
    private boolean canBeMovedOutOfBoard;
    private boolean isMovePossible;
    private boolean playerHavePiecesToInsert;
    private boolean isMoveOutOfTheGameBoardRange;
    private Player player;
    private Player oponent;
    private BoardCoordinates clickedCoordiantes;
    private boolean zeroDiceRoll;


    public GameMove(boolean human , boolean ifInsert, BoardConfiguration boardConfiguration, GameState gameState) {
        isDoneByHumanPlayer =human;
        insertMove=ifInsert;
        diceRoll = generateDiceRoll();
        this.boardConfiguration = boardConfiguration;
        this.gameState = gameState;

        if(human) {
            player = gameState.getHuman();
            oponent = gameState.getComputer();
        }
        else {
            player = gameState.getComputer();
            oponent = gameState.getHuman();

        }

        pieceNewCoordinates = new BoardCoordinates(0,0);
        clickedCoordiantes = new BoardCoordinates(0,0);
    }


    public GameMove(boolean human , boolean ifInsert,BoardConfiguration boardConfiguration, GameState gameState,
                    int clickedPieceColumn, int clickedPieceRow) {

        this(true,false,boardConfiguration,gameState);
        clickedCoordiantes = new BoardCoordinates(clickedPieceColumn,clickedPieceRow);
    }


    private int generateDiceRoll() {
        Random r = new Random();

        int result = 0;
        result = r.nextInt(5);

        return result;
    }

    private void checkCollisionConditions() {

        doMoveChecks();
        if(collisionWithAnotherPlayerPiece) {
            isMovePossible=false;
        }


        if(collisionWithOponentPiece) {
            findAndCheckOponentPiece();
            if(isOponentPieceOnBattleFiels && isOponentPieceONRosette) {
                isMovePossible=false;
            }
        }

    }

    public void preparePlayerPiecesForInsert(){
        pieceToDoAction = player.getFreePiece();

        if(pieceToDoAction!=null) {
            playerHavePiecesToInsert=true;
            generatePossibleNewRowCoordinate();
            generatePossibleNewColumnCoordinate();

            isMovePossible=true;
            checkCollisionConditions();
        }
        else {
            isMovePossible = false;
            playerHavePiecesToInsert=false;
        }

    }

    public void preparePlayerPieceForMove() {

        isMovePossible = false;
        if(player.isHuman()) {
            pieceToDoAction = player.getPieceByBoardPosition(clickedCoordiantes);

            if(diceRoll==0) {
                zeroDiceRoll=true;
                isMovePossible=false;
            }
        }
        else {
            prepareComputerPiecesForMove();
        }

        if(pieceToDoAction!=null) {

            generatePossibleNewColumnCoordinate();
            generatePossibleNewRowCoordinate();
            makeMoveChecks();
        }
    }

    public void makeMoveChecks() {

        isMoveOutOfTheGameBoardRange = boardConfiguration.checkIfPieceMovedTooFar(pieceToDoAction.getPiecePositionOnBoard(),diceRoll);

        if(isMoveOutOfTheGameBoardRange)
        {
            isMoveOutOfTheGameBoardRange =true;
            isMovePossible = false;
        }
        else {
            isMovePossible=true;
            checkCollisionConditions();
        }

    }

    public void reInitializeMove() {

        generatePossibleNewColumnCoordinate();
        generatePossibleNewRowCoordinate();
        makeMoveChecks();
    }

    private void prepareComputerPiecesForMove() {

        List<GameMove> possibleMoves = returnComputerPossibleMovesBasedOnThatMove();

        if(possibleMoves != null && possibleMoves.size()>0) {
            GameMove moveToExitWithPiece = returnComputerPieceToRemove(possibleMoves);
            if(moveToExitWithPiece!=null) {
                copyDataFromAnotherGameMove(moveToExitWithPiece);
                isMovePossible=true;
            } else {
                GameMove moveClosestToExit = getGameMoveClosestToExit(possibleMoves);

                if(moveClosestToExit!=null) {
                    copyDataFromAnotherGameMove(moveClosestToExit);
                    isMovePossible=true;
                }
                else {
                    isMovePossible = false;
                }
            }
        }
        else {
            isMovePossible = false;
        }

    }

    private void doMoveChecks() {
        canBeMovedOutOfBoard = boardConfiguration.checkIfPieceSuccesfullyMovedThroughBoard(pieceNewCoordinates,player);
        collisionWithAnotherPlayerPiece = player.checkCollisionWithPlayerPieces(pieceNewCoordinates);
        collisionWithOponentPiece = oponent.checkCollisionWithPlayerPieces(pieceNewCoordinates);
    }

    private void findAndCheckOponentPiece() {
        if(collisionWithOponentPiece) {
            oponentPieceInCollision = oponent.getPieceByBoardPosition(pieceNewCoordinates);
            isOponentPieceOnBattleFiels = boardConfiguration.checkIfOnBattleField(oponentPieceInCollision.getPiecePositionOnBoard());
            isOponentPieceONRosette = boardConfiguration.checkIfPieceIsOnRosette(oponentPieceInCollision.getPiecePositionOnBoard());
        }
    }

    private void generatePossibleNewColumnCoordinate() {

        int newColumnCoordinate;


        if (pieceToDoAction.getPiecePositionOnBoard().getColumn() == 0 || pieceToDoAction.getPiecePositionOnBoard().getColumn()== boardConfiguration.getMaxColumnNumberOnBoard()) {
            newColumnCoordinate = pieceToDoAction.getPiecePositionOnBoard().getRow() + diceRoll;
            if (newColumnCoordinate <= boardConfiguration.getMaxRowNumberOnBoard()) {
                pieceNewCoordinates.setColumn(pieceToDoAction.getPiecePositionOnBoard().getColumn());
            } else {
                pieceNewCoordinates.setColumn(1);
            }

        }

        if (pieceToDoAction.getPiecePositionOnBoard().getColumn() == 1) {
            newColumnCoordinate = pieceToDoAction.getPiecePositionOnBoard().getRow() - diceRoll;
            if (newColumnCoordinate >= 0) {
                pieceNewCoordinates.setColumn(pieceToDoAction.getPiecePositionOnBoard().getColumn());
            } else {
                if (isDoneByHumanPlayer) {
                    pieceNewCoordinates.setColumn(0);
                } else {
                    pieceNewCoordinates.setColumn(2);
                }
            }
        }

    }

    private void generatePossibleNewRowCoordinate() {

        int newRowCoordinate;
        int movesToEndOfTheBoard;
        int restMoves = 0;

        if (pieceToDoAction.getPiecePositionOnBoard().getColumn() == 0 || pieceToDoAction.getPiecePositionOnBoard().getColumn() == 2) {
            newRowCoordinate = pieceToDoAction.getPiecePositionOnBoard().getRow() + diceRoll;
            if (newRowCoordinate <= boardConfiguration.getMaxRowNumberOnBoard()) {
                pieceNewCoordinates.setRow(newRowCoordinate);
            } else {

                movesToEndOfTheBoard = boardConfiguration.getMaxRowNumberOnBoard() - pieceToDoAction.getPiecePositionOnBoard().getRow() ; // move to end of board
                restMoves = diceRoll - movesToEndOfTheBoard;
                restMoves -= 1;
                pieceNewCoordinates.setRow( 7 - restMoves);
            }
        }

        if (pieceToDoAction.getPiecePositionOnBoard().getColumn() == 1) {
            newRowCoordinate = pieceToDoAction.getPiecePositionOnBoard().getRow() - diceRoll;
            if (newRowCoordinate >= 0) {
                pieceNewCoordinates.setRow(newRowCoordinate);
            } else {
                movesToEndOfTheBoard = pieceToDoAction.getPiecePositionOnBoard().getRow();
                restMoves = diceRoll - movesToEndOfTheBoard;
                restMoves -= 1;
                pieceNewCoordinates.setRow(0 + restMoves);
            }
        }

    }

    public void copyDataFromAnotherGameMove(GameMove move) {
        pieceToDoAction = move.getPieceToDoAction();
        oponentPieceInCollision = move.getOponentPieceInCollision();
        boardConfiguration  =  move.getBoardConfiguration();
        gameState = move.getGameState();
        isDoneByHumanPlayer = move.isDoneByHumanPlayer();
        insertMove = move.isInsertMove();
        diceRoll = move.getDiceRoll();
        pieceNewCoordinates.setColumn(move.getPieceNewCoordinates().getColumn());
        pieceNewCoordinates.setRow(move.getPieceNewCoordinates().getRow());
        collisionWithAnotherPlayerPiece = move.isCollisionWithAnotherPlayerPiece();
        collisionWithOponentPiece = move.isCollisionWithOponentPiece();
        isOponentPieceONRosette = move.isOponentPieceONRosette();
        isOponentPieceOnBattleFiels = move.isOponentPieceOnBattleFiels();
        canBeMovedOutOfBoard = move.isCanBeMovedOutOfBoard();
        isMovePossible = move.isMovePossible();
        playerHavePiecesToInsert = move.isPlayerHavePiecesToInsert();
        isMoveOutOfTheGameBoardRange = move.isMoveOutOfTheGameBoardRange();
        player = move.getPlayer();
        oponent = move.getOponent();
        clickedCoordiantes.setRow(move.getClickedCoordiantes().getRow());
        clickedCoordiantes.setColumn(move.getClickedCoordiantes().getRow());
        zeroDiceRoll = move.isZeroDiceRoll();

    }

    private List<GameMove> returnComputerPossibleMovesBasedOnThatMove() {
        List<GameMove> result = new ArrayList<>();

        for(Piece p : gameState.getComputer().getPlayerPieces()){

            if(p.isOnBoard()) {
                GameMove possibleMove = new GameMove(false, false, boardConfiguration, gameState);
                possibleMove.setPieceToDoAction(p);
                possibleMove.setDiceRoll(diceRoll);
                possibleMove.reInitializeMove();

                if(possibleMove.isMovePossible()) {
                    result.add(possibleMove);

                }
            }
        }

        return  result;
    }

    private GameMove returnComputerPieceToRemove (List<GameMove> input) {
        GameMove result = null;
        boolean end = false;

        Iterator<GameMove> pieceIterator = input.iterator();

        while(!end && pieceIterator.hasNext()) {
            GameMove move = pieceIterator.next();

            if(move.pieceCnBeMovedOutOfBoard()){
                end = true;
                result = move;
            }
        }
        return  result;
    }

    private GameMove getGameMoveClosestToExit(List<GameMove> input) {
        GameMove result = null;
        int lastDistance = 0;
        int actualDistance = 0;

        for(GameMove move  : input) {
            actualDistance = boardConfiguration.getDistanceBetweenComputerPiecePositionAndEndOfBoard(move.pieceNewCoordinates);
            if(lastDistance==0) {
                lastDistance = actualDistance;
                result = move;
            }
            else {
                if(actualDistance<lastDistance) {
                    lastDistance = actualDistance;
                    result = move;
                }
            }
        }


        return  result;
    }

    public Piece getPieceToDoAction() {
        return pieceToDoAction;
    }

    public BoardConfiguration getBoardConfiguration() {
        return boardConfiguration;
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean isDoneByHumanPlayer() {
        return isDoneByHumanPlayer;
    }

    public boolean isInsertMove() {
        return insertMove;
    }

    public boolean isCollisionWithAnotherPlayerPiece() {
        return collisionWithAnotherPlayerPiece;
    }

    public boolean isCollisionWithOponentPiece() {
        return collisionWithOponentPiece;
    }

    public boolean isOponentPieceONRosette() {
        return isOponentPieceONRosette;
    }

    public boolean isOponentPieceOnBattleFiels() {
        return isOponentPieceOnBattleFiels;
    }

    public boolean isCanBeMovedOutOfBoard() {
        return canBeMovedOutOfBoard;
    }

    public boolean isPlayerHavePiecesToInsert() {
        return playerHavePiecesToInsert;
    }

    public boolean isMoveOutOfTheGameBoardRange() {
        return isMoveOutOfTheGameBoardRange;
    }

    public Piece getOponentPieceInCollision() {
        return oponentPieceInCollision;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOponent() {
        return oponent;
    }

    public BoardCoordinates getPieceNewCoordinates() {
        return pieceNewCoordinates;
    }

    public void setPieceToDoAction(Piece pieceToDoAction) {
        this.pieceToDoAction = pieceToDoAction;
    }

    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public boolean isMovePossible() {
        return isMovePossible;
    }

    public boolean pieceCnBeMovedOutOfBoard() {
        return canBeMovedOutOfBoard;
    }

    public void updatePlayerPieceCoordination() {
        pieceToDoAction.setPieceCoordinates(pieceNewCoordinates);
    }

    public boolean isZeroDiceRoll() {
        return zeroDiceRoll;
    }

    public BoardCoordinates getClickedCoordiantes() {
        return clickedCoordiantes;
    }

    public void setInsertMove(boolean insertMove) {
        this.insertMove = insertMove;
    }
}
