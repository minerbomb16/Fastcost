package com.fastcost.operations;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Objects;

public class AlertCreator {
    public static ButtonType zapisz = new ButtonType("Zapisz");
    public static ButtonType zapiszJako = new ButtonType("Zapisz Jako");
    public static ButtonType nie = new ButtonType("Nie");
    public static ButtonType anuluj = new ButtonType("Anuluj");
    public AlertCreator() {}
    public static void showWindow(String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("FastCost");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(AlertCreator.class.getResource("/com/fastcost/view.css")).toExternalForm());
        alert.showAndWait();
    }

    public static Alert exitAlert(String text) {
        Alert closeAlert = new Alert(Alert.AlertType.INFORMATION, "FastCost", zapisz, zapiszJako ,nie, anuluj);
        closeAlert.setTitle("FastCost");
        closeAlert.setHeaderText(null);
        closeAlert.setContentText(text);
        closeAlert.getDialogPane().getStylesheets().add(Objects.requireNonNull(AlertCreator.class.getResource("/com/fastcost/view.css")).toExternalForm());
        return closeAlert;
    }
}
