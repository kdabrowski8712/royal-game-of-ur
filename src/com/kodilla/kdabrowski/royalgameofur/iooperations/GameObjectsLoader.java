package com.kodilla.kdabrowski.royalgameofur.iooperations;

import com.kodilla.kdabrowski.royalgameofur.gameui.MovesHostoryToSave;
import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;
import com.kodilla.kdabrowski.royalgameofur.state.LoadedState;
import com.kodilla.kdabrowski.royalgameofur.state.SimplifiedGameStateToSave;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
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

    public static MovesHostoryToSave loadHistoryFromFile(File f) throws IOException {
        MovesHostoryToSave result = null;

        try(ObjectInputStream ois = new ObjectInputStream( new FileInputStream(f))) {
            try {
                Object o = ois.readObject();

                if (o instanceof MovesHostoryToSave) {
                    result = (MovesHostoryToSave) o;
                    System.out.println("czytanie history");
                }
                else if(o instanceof  SimplifiedGameStateToSave) {
                    System.out.println("simplified state");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }


        return  result;

    }

    public static SimplifiedGameStateToSave loadStateFromFile(File fileWithState) throws IOException {
        SimplifiedGameStateToSave result = null;

        try(ObjectInputStream ois = new ObjectInputStream( new FileInputStream(fileWithState))) {
            try {
                Object o = ois.readObject();

                if (o instanceof SimplifiedGameStateToSave) {
                    result = (SimplifiedGameStateToSave) o;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }


        return result;
    }

    public static LoadedState loadState(File fileWithState) throws IOException {
        LoadedState result = new LoadedState();

        try(ObjectInputStream ois = new ObjectInputStream( new FileInputStream(fileWithState))) {
            try {
                Object simpState = ois.readObject();

                if (simpState instanceof SimplifiedGameStateToSave) {
                    result.setLoadedState((SimplifiedGameStateToSave) simpState);
                    System.out.println("simple");
                }

                Object hist = ois.readObject();

                if(hist instanceof MovesHostoryToSave) {
                    result.setHistory((MovesHostoryToSave) hist);
                    System.out.println("hist");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }


        return result;
    }


    public static List<SampleClass> testLoad(File f) throws IOException {


        List<SampleClass> result = null;

        try(ObjectInputStream ois = new ObjectInputStream( new FileInputStream(f))) {
            try {
                Object o = ois.readObject();

                if (o instanceof ArrayList) {
                    result = (ArrayList<SampleClass>) o;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
