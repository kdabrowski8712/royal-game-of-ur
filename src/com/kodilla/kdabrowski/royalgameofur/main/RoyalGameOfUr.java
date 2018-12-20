package com.kodilla.kdabrowski.royalgameofur.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class RoyalGameOfUr extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        UrGame game = new UrGame(primaryStage);
        primaryStage.setTitle("Royal Game Of Ur");
        primaryStage.setScene(game.getSceneToRender());
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
