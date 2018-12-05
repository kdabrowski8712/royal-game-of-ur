package com.kodilla;

import javafx.scene.paint.Color;

public class Human extends GenericPlayer {
    public Human(String nick, Color c) {
        super(nick,c);
        for (Piece p : super.getPlayerPieces()) {
            p.setColumnPositionOnBoard(0);
            p.setRowPositionOnBoard(4);
        }
    }

}
