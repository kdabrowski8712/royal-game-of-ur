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
    private MenuItem editGameSettings;
    private MenuItem saveGameState;
    private MenuItem loadGameState;
    private MenuItem saveGameSettings;
    private MenuItem endGame;
    private  MenuItem loadGameSettings;


    private VBox manuBox;

    public GameMenu() {
        gameMenuBar = new MenuBar();
        gameMenu = new Menu("Game");
        gameStateMenu = new Menu("Game State");
        settingsMenu = new Menu("Settings");

        newGame = new MenuItem("New game");
        editGameSettings = new MenuItem("Edit Game Settings");
        saveGameState = new MenuItem("Save");
        loadGameState = new MenuItem("Load");
        saveGameSettings = new MenuItem("Save Game Settings");
        loadGameSettings = new MenuItem("Load Game Settings");
        endGame = new MenuItem("End Game");

    }

    public VBox initialize() {
        VBox result = new VBox();
        this.manuBox = result;

        gameMenu.getItems().add(newGame);
        gameMenu.getItems().add(endGame);

        gameStateMenu.getItems().add(saveGameState);
        gameStateMenu.getItems().add(loadGameState);

        settingsMenu.getItems().add(editGameSettings);
        settingsMenu.getItems().add(saveGameSettings);
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

    public MenuItem getEditGameSettings() {
        return editGameSettings;
    }

    public MenuItem getLoadGameSettings() {
        return loadGameSettings;
    }

    public MenuItem getEndGame() {
        return endGame;
    }

    public void enableMenu(GameMenuItemsEnum menuToEnable) {

        switch(menuToEnable) {
            case NewGame: {
                this.newGame.setDisable(false);
                break;
            }
            case EditSettings: {
                this.editGameSettings.setDisable(false);
                break;
            }
            case SaveState: {
                this.saveGameState.setDisable(false);
                break;
            }
            case LoadState: {
                this.loadGameState.setDisable(false);
                break;
            }
            case SaveSettings: {
                this.saveGameSettings.setDisable(false);
                break;
            }
            case LoadSettings: {
                this.loadGameSettings.setDisable(false);
                break;
            }
            case EndGame: {
                this.endGame.setDisable(false);
                break;
            }

        }

    }

    public void disableMenu(GameMenuItemsEnum menuToEnable) {

        switch(menuToEnable) {
            case NewGame: {
                this.newGame.setDisable(true);
                break;
            }
            case EditSettings: {
                this.editGameSettings.setDisable(true);
                break;
            }
            case SaveState: {
                this.saveGameState.setDisable(true);
                break;
            }
            case LoadState: {
                this.loadGameState.setDisable(true);
                break;
            }
            case SaveSettings: {
                this.saveGameSettings.setDisable(true);
                break;
            }
            case LoadSettings: {
                this.loadGameSettings.setDisable(true);
                break;
            }
            case EndGame: {
                this.endGame.setDisable(true);
                break;
            }

        }


    }



    public void setEventHandlerForMenuItem(EventHandler<ActionEvent> eventToSet , GameMenuItemsEnum menuIem) {


        switch(menuIem) {
            case NewGame: {
                this.newGame.setOnAction(eventToSet);
                break;
            }
            case EditSettings: {
                this.editGameSettings.setOnAction(eventToSet);
                break;
            }
            case SaveState: {
                this.saveGameState.setOnAction(eventToSet);
                break;
            }
            case LoadState: {
                this.loadGameState.setOnAction(eventToSet);
                break;
            }
            case SaveSettings: {
                this.saveGameSettings.setOnAction(eventToSet);
                break;
            }
            case LoadSettings: {
                this.loadGameSettings.setOnAction(eventToSet);
                break;
            }
            case EndGame: {
                this.endGame.setOnAction(eventToSet);
                break;
            }

        }

    }
}
