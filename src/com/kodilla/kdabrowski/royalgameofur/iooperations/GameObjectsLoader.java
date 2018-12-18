package com.kodilla.kdabrowski.royalgameofur.iooperations;

import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class GameObjectsLoader {

    public static GameSettings loadGameSettings2( File filkeWithSettings) throws Exception {

        GameSettings result = null;

        try(ObjectInputStream ois = new ObjectInputStream( new FileInputStream(filkeWithSettings))) {

            try {

                Object readSettings = ois.readObject();

                if(readSettings instanceof GameSettings) {
                    result = (GameSettings) readSettings;
                }
            }catch(Exception ex) {
                throw ex;
            }
        }

        return result;
    }

    public static GameSettings loadGameSettings( File filkeWithSettings) throws IOException {

        GameSettings result = null;

        try(FileReader settingFR = new FileReader(filkeWithSettings);
            BufferedReader bReader = new BufferedReader(settingFR)) {

            List<String> lines =  bReader.lines()
                    .collect(Collectors.toList());
            String[] settingsLineSplitted = lines.get(0).split(",");

            int nrOfMovesToWin = Integer.parseInt(settingsLineSplitted[0]);
            Color humanColor = Color.web(settingsLineSplitted[1]);
            Color computerColor = Color.web(settingsLineSplitted[2]);
            int time = Integer.parseInt(settingsLineSplitted[3]);


            //result = new GameSettings(nrOfMovesToWin,humanColor,computerColor,time);
            result.setSettingsLoadedFromFile(true);


        }

        return result;
    }
}
