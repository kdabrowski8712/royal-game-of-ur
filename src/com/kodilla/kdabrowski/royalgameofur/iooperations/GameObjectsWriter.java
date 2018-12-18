package com.kodilla.kdabrowski.royalgameofur.iooperations;

import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;

import java.io.*;

public class GameObjectsWriter {

    public static void saveGameState() {

    }

    public  static void saveGameSettings(GameSettings gameSettings, File fileToSave)  throws IOException {

        try(FileWriter settingFW = new FileWriter(fileToSave)) {
            settingFW.write(gameSettings.toString());
        }

    }

    public  static  void saveGameSettingsObject(GameSettings gameSettings, File fileToSave ) throws IOException {

        try (ObjectOutputStream ous = new ObjectOutputStream( new FileOutputStream(fileToSave))) {
            System.out.println("pocz");
            ous.writeObject(gameSettings);

            System.out.println("koniec");
        }
    }
}
