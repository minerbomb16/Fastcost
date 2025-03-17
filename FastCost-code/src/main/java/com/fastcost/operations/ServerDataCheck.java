package com.fastcost.operations;

import javafx.scene.control.Alert;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerDataCheck {
    public ServerDataCheck() {}

    public static void check() {
        File dataFile = new File("data/Data.txt");
        try {
            dataFile.createNewFile();
            if(dataFile.length() > 0) {
                BufferedReader mainPath = new BufferedReader(new FileReader(dataFile));
                File mainFile = new File(mainPath.readLine());
                FileInputStream pathFile = new FileInputStream(mainFile);
                if(pathFile.read() != -1) {
                    FileOutputStream fop = new FileOutputStream("data/Dane.xlsx");
                    Files.copy(Path.of(mainFile.getPath()), fop);
                    fop.close();
                }
                mainPath.close();
                pathFile.close();
                AlertCreator.showWindow("Zaktualizowano dane Elisoft", Alert.AlertType.INFORMATION);
            } else {
                AlertCreator.showWindow("Nie podano ścieżki do danych Elisoft", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            AlertCreator.showWindow("Nie można zaktualizować danych Elisoft", Alert.AlertType.ERROR);
        }
    }
}
