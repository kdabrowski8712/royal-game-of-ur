package com.kodilla;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class GameMenu {
    private MenuBar gameMenuBar;
    private Menu gameMenu;
    private MenuItem newGame;
    private MenuItem gameSettings;

    private VBox manuBox;

    public GameMenu() {
        gameMenuBar = new MenuBar();
        gameMenu = new Menu("Game");
        newGame = new MenuItem("New game");
        gameSettings = new MenuItem("Game Settings");

    }

    public VBox initialize() {
        VBox result = new VBox();
        this.manuBox = result;

        gameMenu.getItems().add(gameSettings);
        gameMenu.getItems().add(newGame);
        gameMenuBar.getMenus().add(gameMenu);

        result.getChildren().add(gameMenuBar);

        return result;
    }


    public MenuItem getNewGameMenuItem() {
        return newGame;
    }

    public MenuItem getGameSettings() {
        return gameSettings;
    }
}
