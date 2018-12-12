package com.kodilla;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameSettings {
    public GameSettings(int nrOfPiecesToWin, Color humanColor, Color computerColor) {
        this.nrOfPiecesToWin = nrOfPiecesToWin;
        this.humanColor = humanColor;
        this.computerColor = computerColor;
    }

    private int nrOfPiecesToWin;
    private Color humanColor;
    private Color computerColor;

    public int getNrOfPiecesToWin() {
        return nrOfPiecesToWin;
    }

    public void setNrOfPiecesToWin(int nrOfPiecesToWin) {
        this.nrOfPiecesToWin = nrOfPiecesToWin;
    }

    public Color getHumanColor() {
        return humanColor;
    }

    public void setHumanColor(Color humanColor) {
        this.humanColor = humanColor;
    }

    public Color getComputerColor() {
        return computerColor;
    }

    public void setComputerColor(Color computerColor) {
        this.computerColor = computerColor;
    }

    public Dialog<GameSettings> buildDialogForGameSettings()  {
        Dialog<GameSettings> result = new Dialog<>();
        result.setTitle("Game Settings");

        GridPane content = new GridPane();
        content.setHgap(20);
        content.setVgap(10);
        content.setPadding(new Insets(10,10,0,10));

        Label testL = new Label("Nr of cleared pieces to win:");
        Label humanPieceColor = new Label("Human piece color: ");
        Label computerPieceColor = new Label("Computer piece color:");
        TextField input = new TextField();
        input.setText("7");
        input.setDisable(true);
        CheckBox defaultCheckbox = new CheckBox("Default (7)");
        defaultCheckbox.setSelected(true);

        ColorPicker humanColorPicker = new ColorPicker(Color.YELLOW);
        ColorPicker computerColorPicker = new ColorPicker(Color.GRAY);

        content.add(testL,0,3);
        content.add(input,1,3);
        content.add(defaultCheckbox,2,3);
        content.add(humanPieceColor,0,4);
        content.add(humanColorPicker,1,4);
        content.add(computerPieceColor,0,5);
        content.add(computerColorPicker,1,5);

        result.getDialogPane().setContent(content);

        ButtonType okButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType ocancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        result.getDialogPane().getButtonTypes().add(okButton);
        result.getDialogPane().getButtonTypes().add(ocancelButton);

        defaultCheckbox.setOnAction(event -> {
            CheckBox chk = (CheckBox)event.getSource();

            if(chk.isSelected()) {
                input.setDisable(true);
                input.setText("7");
            }
            if(!chk.isSelected()) {
                input.setDisable(false);
            }

        });

        result.setResultConverter((button )-> {
            GameSettings value;
            if(button==okButton){
                try {
                    int nrOfMovesToWin = Integer.parseInt(input.getText());
                    value = new GameSettings(nrOfMovesToWin,humanColorPicker.getValue(),computerColorPicker.getValue());
                }
                catch (NumberFormatException ex) {
                    Alert alert = UITools.generateAlert("Parsing error",null,"Wrong data in text filed - default(7) will be used", Alert.AlertType.INFORMATION);
                    alert.showAndWait();
                    value = new GameSettings(7,humanColorPicker.getValue(),computerColorPicker.getValue());
                }
                return value;
            }  else {
                return null;
            }
        });

        return result;
    }
}
