package com.kodilla.kdabrowski.royalgameofur.gameui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class GameMenu {
    private MenuBar gameMenuBar;
    private Menu gameMenu;
    private Menu gameStateMenu;
    private MenuItem newGame;
    private MenuItem gameSettings;
    private MenuItem saveGameState;


    private VBox manuBox;

    public GameMenu() {
        gameMenuBar = new MenuBar();
        gameMenu = new Menu("Game");
        gameStateMenu = new Menu("Game State");

        newGame = new MenuItem("New game");
        gameSettings = new MenuItem("Game Settings");
        saveGameState = new MenuItem("Save");

    }

    public VBox initialize() {
        VBox result = new VBox();
        this.manuBox = result;

        gameMenu.getItems().add(gameSettings);
        gameMenu.getItems().add(newGame);
        gameStateMenu.getItems().add(saveGameState);

        gameMenuBar.getMenus().add(gameMenu);
        gameMenuBar.getMenus().add(gameStateMenu);

        result.getChildren().add(gameMenuBar);

        return result;
    }


    public MenuItem getNewGameMenuItem() {
        return newGame;
    }

    public MenuItem getGameSettings() {
        return gameSettings;
    }

    public void setEventHandlerForMenuItem(EventHandler<ActionEvent> eventToSet , GameMenuItemsEnum.MenuItemText menuIem) {

        if(menuIem.equals(GameMenuItemsEnum.MenuItemText.NewGame)) {
            this.newGame.setOnAction(eventToSet);
        }
        else if(menuIem.equals(GameMenuItemsEnum.MenuItemText.EditSettings)) {
            this.gameSettings.setOnAction(eventToSet);
        }
        else if(menuIem.equals(GameMenuItemsEnum.MenuItemText.SaveState)) {
            this.saveGameState.setOnAction(eventToSet);
        }
    }
}
