package com.kodilla;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class GameMenu {
    private MenuBar gameMenuBar;
    private Menu gameMenu;
    private MenuItem newMove;
    private MenuItem newGame;

    private VBox manuBox;

    public GameMenu() {
        gameMenuBar = new MenuBar();
        gameMenu = new Menu("Game");
        newMove = new MenuItem("New move");
        newGame = new MenuItem("New game");
        newMove.setDisable(true);
    }

    public VBox initialize() {
        VBox result = new VBox();
        this.manuBox = result;

        gameMenu.getItems().add(newMove);
        gameMenu.getItems().add(newGame);

        gameMenuBar.getMenus().add(gameMenu);

        result.getChildren().add(gameMenuBar);

        return result;
    }

    public MenuItem getNewMoveMenuItem() {
        return newMove;
    }

    public MenuItem getNewGameMenuItem() {
        return newGame;
    }
}
