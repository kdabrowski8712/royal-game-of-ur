package com.kodilla.kdabrowski.royalgameofur.iooperations;

import java.io.Serializable;

public class SampleClass implements Serializable {

    private int value;
    private String description;

    public SampleClass(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
