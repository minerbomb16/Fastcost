package com.fastcost.fileOperations;

import com.fastcost.Main;
import static com.fastcost.buttons.MenuButtons.path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    FileChooser fileChooser = new FileChooser();
    Stage stage = Main.getStage();
    public File entryFile;
    public File outFile;
    public File eliFile;
    public File pathFile;
    public File pdfFile;

    public FileHandler() {}

    public void setEntryFile() {
        entryFile = chooseEntryFile();
    }

    public void setOutFile() {
        outFile = chooseOutFile();
    }

    public void setElisoftFile() {
        eliFile = chooseExcelFile();
    }

    public void setPathFile() {
        pathFile = choosePathFile();
    }

    public void setPdfFile() {
        pdfFile = choosePdfFile();
    }

    public File getEntryFile() {
        return entryFile;
    }

    public File getOutFile() {
        return outFile;
    }

    public File getElisoftFile() {
        return eliFile;
    }

    public File getPathFile() {
        return pathFile;
    }

    public File getPdfFile() {
        return pdfFile;
    }

    public File chooseEntryFile(){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FCMB files (*.fcmb)", "*.fcmb");
        fileChooser.getExtensionFilters().add(extFilter);
        if(!path.equals("") && Paths.get(path).getParent().toFile().isDirectory()) {
            fileChooser.setInitialDirectory(Paths.get(path).getParent().toFile());
        }
        return fileChooser.showOpenDialog(stage);
    }

    public File chooseOutFile(){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FCMB files (*.fcmb)", "*.fcmb");
        fileChooser.getExtensionFilters().add(extFilter);
        if(!path.equals("") && Paths.get(path).getParent().toFile().isDirectory()) {
            fileChooser.setInitialDirectory(Paths.get(path).getParent().toFile());
        }
        return fileChooser.showSaveDialog(stage);
    }

    public File chooseExcelFile(){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(stage);
    }

    public File choosePathFile(){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(stage);
    }

    public File choosePdfFile(){
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(stage);
    }

}
