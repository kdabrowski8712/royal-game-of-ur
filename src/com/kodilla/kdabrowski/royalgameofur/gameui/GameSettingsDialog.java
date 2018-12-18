package com.kodilla.kdabrowski.royalgameofur.gameui;

import com.kodilla.kdabrowski.royalgameofur.settings.GameSettings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameSettingsDialog {

    private Label nrOfPiecesToWinLabel;
    private Label humanPieceColor;
    private Label computerPieceColor;
    private Label timeOfPlaying;
    private TextField inputNrOfPieces;
    private TextField timeInput;
    private ColorPicker humanColorPicker;
    private ColorPicker computerColorPicker;
    private ButtonType okButton;
    private ButtonType cancelButton;
    private CheckBox defaultCheckbox;
    private CheckBox defaultTimeCheckBox;

    private GridPane dailogPanel;
    private Dialog<GameSettings> dialogObject;

    public GameSettingsDialog(GameSettings settings) {

        dailogPanel = new GridPane();
        nrOfPiecesToWinLabel = new Label("Nr of cleared pieces to win:");
        humanPieceColor = new Label("Human piece color: ");
        computerPieceColor = new Label("Computer piece color:");
        timeOfPlaying = new Label("Time of playing(minutes): ");
        inputNrOfPieces = new TextField();
        timeInput = new TextField();

       dailogPanel.setVgap(10);
       dailogPanel.setPadding(new Insets(0,10,0,10));
       dailogPanel.setHgap(10);

        if(!settings.isSettingsLoadedFromFile()) {
            inputNrOfPieces.setText("7");
            timeInput.setText("5");
        }
        else {
            inputNrOfPieces.setText(String.valueOf(settings.getNrOfPiecesToWin()));
            timeInput.setText(String.valueOf(settings.getTimeOfPlaying()));
        }

        if(!settings.isSettingsLoadedFromFile()) {
            inputNrOfPieces.setDisable(true);
            timeInput.setDisable(true);
        }


        defaultCheckbox = new CheckBox("Default (7)");
        defaultTimeCheckBox = new CheckBox("Default (5)");

        if(!settings.isSettingsLoadedFromFile()) {
            defaultCheckbox.setSelected(true);
            defaultTimeCheckBox.setSelected(true);
        }

        humanColorPicker = new ColorPicker(Color.YELLOW);
        computerColorPicker = new ColorPicker(Color.GRAY);

        dailogPanel.add(nrOfPiecesToWinLabel, 0, 3);
        dailogPanel.add(inputNrOfPieces, 1, 3);
        dailogPanel.add(defaultCheckbox, 2, 3);

        dailogPanel.add(timeOfPlaying, 0, 4);
        dailogPanel.add(timeInput, 1, 4);
        dailogPanel.add(defaultTimeCheckBox, 2, 4);

        dailogPanel.add(humanPieceColor, 0, 5);
        dailogPanel.add(humanColorPicker, 1, 5);
        dailogPanel.add(computerPieceColor, 0, 6);
        dailogPanel.add(computerColorPicker, 1, 6);

        okButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        dialogObject = new Dialog<>();

        dialogObject.setTitle("Edit Game Settings");

        dialogObject.getDialogPane().setContent(this.dailogPanel);
        dialogObject.getDialogPane().getButtonTypes().add(okButton);
        dialogObject.getDialogPane().getButtonTypes().add(cancelButton);

        setEventHandlers();
    }

    private void setEventHandlers() {
        defaultCheckbox.setOnAction(produceCheckBoxEvent("7",inputNrOfPieces));
        defaultTimeCheckBox.setOnAction(produceCheckBoxEvent("5",timeInput));
        setHandlersForButtons();
    }

    private void setHandlersForButtons() {

        dialogObject.setResultConverter((button) -> {
            GameSettings value;
            if (button == okButton) {
                try {
                    int nrOfMovesToWin = Integer.parseInt(inputNrOfPieces.getText());
                    int timeOfMove = Integer.parseInt(timeInput.getText());

//                    value = new GameSettings(nrOfMovesToWin, humanColorPicker.getValue(), computerColorPicker.getValue(),timeOfMove);
                    value = new GameSettings(nrOfMovesToWin, humanColorPicker.getValue().toString(), computerColorPicker.getValue().toString(),timeOfMove);
                } catch (NumberFormatException ex) {
                    Alert alert = UITools.generateAlert("Parsing error", null, "Wrong data in text fields. Nr of muves will be set to 7. " +
                            " Time for play will be  set to 5 minutes ", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
//                    value = new GameSettings(7, humanColorPicker.getValue(), computerColorPicker.getValue(), 5);
                    value = new GameSettings(7, humanColorPicker.getValue().toString(), computerColorPicker.getValue().toString(), 5);
                }
                return value;
            } else {
                return null;
            }

        });

    }

    private EventHandler<ActionEvent> produceCheckBoxEvent(String texrToSet, TextField filedToControl) {

        EventHandler<ActionEvent> result = (event -> {
            CheckBox chk = (CheckBox) event.getSource();

            if (chk.isSelected()) {
                filedToControl.setDisable(true);
                filedToControl.setText(texrToSet);
            }
            if (!chk.isSelected()) {
                filedToControl.setDisable(false);
            }

        });

        return result;
    }


    public Dialog<GameSettings> returnDialog() {

        return dialogObject;
    }


}
