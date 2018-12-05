package com.kodilla;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HistoryPanel {
    private VBox panel;
    private Label titleLabel;
    private HBox titleBox;
    private ListView<String> histView;
    private ObservableList<String> histValues;



    private String cssLayout4 = "-fx-border-color: black;\n" +
            "-fx-border-insets: 5;\n" +
            "-fx-border-width: 1;\n" +
            "-fx-border-style: solid;\n";

    public HistoryPanel() {

        titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        titleLabel = new Label("MOVES HISTORY");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 26");

        histView = new ListView<String>();
        histView.getItems().add("No records");
    }


    public VBox generatePanel() {
        VBox result = new VBox();
        this.panel = result;

        result.setPadding(new Insets(5 , 10 ,10 ,10 ));
        result.setSpacing(8);
        result.setStyle(cssLayout4);
        result.setPrefWidth(300);

        titleBox.getChildren().add(titleLabel);
        result.getChildren().add(titleBox);
        result.getChildren().add(histView);

        return result;
    }

    public void addEntry(String entry) {
        this.histView.getItems().add(entry);
    }

    public void clear() {
        this.histView.getItems().remove(0,histView.getItems().size());
        //this.histView.getItems().add("No records");
    }

}
