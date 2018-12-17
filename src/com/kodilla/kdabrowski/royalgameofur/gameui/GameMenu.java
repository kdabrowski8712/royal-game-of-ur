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
    private Menu settingsMenu;
    private MenuItem newGame;
    private MenuItem gameSettings;
    private MenuItem saveGameState;
    private MenuItem saveSettings;
    private  MenuItem loadGameSettings;


    private VBox manuBox;

    public GameMenu() {
        gameMenuBar = new MenuBar();
        gameMenu = new Menu("Game");
        gameStateMenu = new Menu("Game State");
        settingsMenu = new Menu("Settings");

        newGame = new MenuItem("New game");
        gameSettings = new MenuItem("Edit Game Settings");
        saveGameState = new MenuItem("Save");
        saveSettings = new MenuItem("Save Game Settings");
        loadGameSettings = new MenuItem("Load Game Settings");

    }

    public VBox initialize() {
        VBox result = new VBox();
        this.manuBox = result;

        gameMenu.getItems().add(newGame);
        gameStateMenu.getItems().add(saveGameState);

        settingsMenu.getItems().add(gameSettings);
        settingsMenu.getItems().add(saveSettings);
        settingsMenu.getItems().add(loadGameSettings);

        gameMenuBar.getMenus().add(gameMenu);
        gameMenuBar.getMenus().add(gameStateMenu);
        gameMenuBar.getMenus().add(settingsMenu);

        result.getChildren().add(gameMenuBar);

        return result;
    }


    public MenuItem getNewGameMenuItem() {
        return newGame;
    }

    public MenuItem getGameSettings() {
        return gameSettings;
    }

    public MenuItem getLoadGameSettings() {
        return loadGameSettings;
    }

    public void setEventHandlerForMenuItem(EventHandler<ActionEvent> eventToSet , GameMenuItemsEnum menuIem) {


        switch(menuIem) {
            case NewGame: {
                this.newGame.setOnAction(eventToSet);
                break;
            }
            case EditSettings: {
                this.gameSettings.setOnAction(eventToSet);
                break;
            }
            case SaveState: {
                this.saveGameState.setOnAction(eventToSet);
                break;
            }
            case SaveSettings: {
                this.saveSettings.setOnAction(eventToSet);
                break;
            }
            case LoadSettings: {
                this.loadGameSettings.setOnAction(eventToSet);
            }

        }

//        if(menuIem.equals(GameMenuItemsEnum.MenuItemText.NewGame)) {
//            this.newGame.setOnAction(eventToSet);
//        }
//        else if(menuIem.equals(GameMenuItemsEnum.MenuItemText.EditSettings)) {
//            this.gameSettings.setOnAction(eventToSet);
//        }
//        else if(menuIem.equals(GameMenuItemsEnum.MenuItemText.SaveState)) {
//            this.saveGameState.setOnAction(eventToSet);
//        }
//        else if(menuIem.equals(GameMenuItemsEnum.MenuItemText.SaveSettings)) {
//            this.saveSettings.setOnAction(eventToSet);
//        }
//        else if(menuIem.equals())
    }
}
