package com.kodilla.kdabrowski.royalgameofur.state;

import com.kodilla.kdabrowski.royalgameofur.gameui.MovesHostoryToSave;

public class LoadedState {
    private  SimplifiedGameStateToSave loadedState;
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
