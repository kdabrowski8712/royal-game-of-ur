package com.kodilla.kdabrowski.royalgameofur.settings;

import com.kodilla.kdabrowski.royalgameofur.state.BoardCoordinates;

import java.io.Serializable;
public class Rosette  {
    private BoardCoordinates rosettePosition;

    public Rosette(int rosetteColumn, int rosetteRow) {
        rosettePosition = new BoardCoordinates(rosetteColumn,rosetteRow);

    }

    public BoardCoordinates getRosettePosition() {
        return rosettePosition;
    }
}
