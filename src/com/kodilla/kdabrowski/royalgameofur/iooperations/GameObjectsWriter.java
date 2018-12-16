package com.kodilla.kdabrowski.royalgameofur.iooperations;

import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameObjectsWriter {

    public static void saveGameState() {

    }

    public  static void saveGameSettings(GameSettings gameSettings, File fileToSave)  throws IOException {

        try(FileWriter settingFW = new FileWriter(fileToSave)) {
            settingFW.write(gameSettings.toString());
        }

    }
}
