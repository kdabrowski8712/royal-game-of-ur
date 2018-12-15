package com.kodilla.kdabrowski.royalgameofur.gameui;

import javafx.scene.control.Alert;

public  class UITools {
    public static Alert generateAlert(String title, String header, String content , Alert.AlertType type) {
        Alert res = new Alert(type);
        res.setTitle(title);
        res.setHeaderText(header);
        res.setContentText(content);

        return res;
    }
}
