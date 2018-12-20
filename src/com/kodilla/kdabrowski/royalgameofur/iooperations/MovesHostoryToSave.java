package com.kodilla.kdabrowski.royalgameofur.iooperations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovesHostoryToSave implements Serializable {
    private List<String> histryEntries;

    public MovesHostoryToSave() {
        histryEntries = new ArrayList<>();
    }

    public List<String> getHistryEntries() {
        return histryEntries;
    }

    public void setHistryEntries(List<String> histryEntries) {
        this.histryEntries = histryEntries;
    }
}
