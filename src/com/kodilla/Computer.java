package com.kodilla;

import javafx.scene.paint.Color;

public class Computer extends GenericPlayer {
    public Computer(Color c ) {
        super("Computer",c);
        for (Piece p : super.getPlayerPieces()) {
            p.setColumnPositionOnBoard(2);
            p.setRowPositionOnBoard(4);
        }
    }


}
