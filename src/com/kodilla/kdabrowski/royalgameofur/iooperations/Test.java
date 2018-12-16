package com.kodilla.kdabrowski.royalgameofur.iooperations;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class Test {
    public static void main(String[] args) {

        Color c = Color.RED;
        String colstr = c.toString();

        Color c2 = Color.valueOf(colstr);
        System.out.println(colstr);


    }
}
