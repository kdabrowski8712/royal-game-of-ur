package com.kodilla.kdabrowski.royalgameofur.iooperations;

import com.kodilla.kdabrowski.royalgameofur.gameui.GameUI;
import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;
import com.kodilla.kdabrowski.royalgameofur.state.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameObjectsWriter {

    public static void saveGameState(GameState state, GameUI ui, File fileToSave) throws IOException {

        try (ObjectOutputStream ous = new ObjectOutputStream( new FileOutputStream(fileToSave))){

            MovesHostoryToSave histToSave = ui.returnHistoryToSave();
            SimplifiedGameStateToSave stateToSave = state.returnStateToSave();

            ous.writeObject(stateToSave);
            ous.writeObject(histToSave);

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
