package com.fastcost;

import com.fastcost.buttons.MenuButtons;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.fastcost.operations.AlertCreator.*;

public class Main extends Application {
    public static Stage stage;
    private static String[] arguments;

    @Override
    public void init() {
        arguments = getParameters().getRaw().toArray(new String[0]);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fastcost-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 870, 735);

        MainController controller = fxmlLoader.getController();
        controller.setArgs(arguments);

        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/com/fastcost/ikonaFastCost.png")).toExternalForm());
        stage.getIcons().add(icon);

        String css = Objects.requireNonNull(this.getClass().getResource("/com/fastcost/view.css")).toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("FastCost");
        stage.setScene(scene);
        stage.setMinHeight(790);
        stage.setMinWidth(920);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            closeSave();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    private void closeSave() {
        Alert closeAlert = exitAlert("Zapisać plik przed wyjściem?");
        closeAlert.showAndWait().ifPresent(response -> {
            MenuButtons exitButtons = new MenuButtons();
            if (response == zapisz) {
                if (exitButtons.save()) {
                    stage.close();
                }
            } else if (response == zapiszJako) {
                if (exitButtons.saveAs()) {
                    stage.close();
                }
            } else if (response == nie) {
                stage.close();
            }
        });
    }
}