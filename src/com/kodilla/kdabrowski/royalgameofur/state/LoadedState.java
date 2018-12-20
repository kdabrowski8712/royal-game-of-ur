package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.iooperations.MovesHostoryToSave;
import com.kodilla.kdabrowski.royalgameofur.iooperations.SimplifiedGameStateToSave;

public class LoadedState {
    private SimplifiedGameStateToSave loadedState;
    private MovesHostoryToSave history ;

    public SimplifiedGameStateToSave getLoadedState() {
        return loadedState;
    }

    public void setLoadedState(SimplifiedGameStateToSave loadedState) {
        this.loadedState = loadedState;
    }

    public MovesHostoryToSave getHistory() {
        return history;
    }

    public void setHistory(MovesHostoryToSave history) {
        this.history = history;
    }
}
