package com.fastcost.buttons;

import com.fastcost.fileOperations.FileHandler;
import com.fastcost.fileOperations.FileReaderFCMB;
import com.fastcost.fileOperations.FileWriterFCMB;
import com.fastcost.operations.*;
import com.fastcost.tableViews.InformationTableView;

import com.fastcost.tableObjects.ProductTableObject;
import com.fastcost.tableObjects.DataTableObject;
import com.fastcost.tableObjects.InformationTableObject;
import com.fastcost.tableViews.ProductTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.fastcost.operations.AlertCreator.*;
import static com.fastcost.tableViews.DataTableView.dataTableView;
import static com.fastcost.tableViews.ProductTableView.productTableView;

public class MenuButtons {
    private ObservableList<ProductTableObject> dataList = FXCollections.observableArrayList();
    public static String path = "";
    public MenuButtons(ObservableList<ProductTableObject> dataList) {
        this.dataList = dataList;
    }
    public MenuButtons() {}

    public void newDocument() {
        Alert closeAlert = exitAlert("Zapisać plik przed otwarciem nowego?");
        closeAlert.showAndWait().ifPresent(response -> {
            if(response == zapisz) {
                if (save()) {
                    path = "";
                    clearTables();
                }
            } else if(response == zapiszJako) {
                if(saveAs()) {
                    clearTables();
                    path = "";
                }
            } else if(response == nie) {
                clearTables();
                path = "";
            }
        });
    }

    public boolean save() {
        if(!path.equals("")) {
            FileWriterFCMB fileWriterFCMB = new FileWriterFCMB(path, dataTableView, InformationTableView.getTable());
            fileWriterFCMB.write();
            AlertCreator.showWindow("Zapisano plik", Alert.AlertType.INFORMATION);
            return true;
        } else {
            return saveAs();
        }
    }

    public boolean saveAs() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.setOutFile();
        if(fileHandler.getOutFile() != null) {
            path = fileHandler.getOutFile().getPath();
            FileWriterFCMB fileWriterFCMB = new FileWriterFCMB(path, dataTableView, InformationTableView.getTable());
            fileWriterFCMB.write();
            AlertCreator.showWindow("Zapisano plik", Alert.AlertType.INFORMATION);
            return true;
        } else {
            AlertCreator.showWindow("Nie można zapisać pliku", Alert.AlertType.ERROR);
            return false;
        }
    }

    public void loadFile() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.setEntryFile();
        if(fileHandler.getEntryFile() != null) {
            path = fileHandler.getEntryFile().getPath();
            FileReaderFCMB fileReaderFCMB = new FileReaderFCMB(path, dataTableView, InformationTableView.getTable());
            fileReaderFCMB.read();
            AlertCreator.showWindow("Wczytano plik", Alert.AlertType.INFORMATION);
        } else {
            AlertCreator.showWindow("Nie można wczytać pliku", Alert.AlertType.ERROR);
        }
    }

    public void loadFile(String filePath) {
        path = filePath;
        FileReaderFCMB fileReaderFCMB = new FileReaderFCMB(path, dataTableView, InformationTableView.getTable());
        fileReaderFCMB.read();
    }

    public void createPDF() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.setPdfFile();
        if(fileHandler.getPdfFile() != null) {
            PDFcreator pdfCreator = new PDFcreator();
            pdfCreator.create(fileHandler.getPdfFile().getPath());
        } else {
            AlertCreator.showWindow("Nie można stworzyć pliku PDF", Alert.AlertType.ERROR);
        }
    }

    public void loadElisotData() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.setElisoftFile();
        try {
            File dataElisoft = new File("data/Dane.xlsx");
            if(!dataElisoft.createNewFile()) {
                dataElisoft.delete();
                dataElisoft.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(dataElisoft);
            Files.copy(Path.of(fileHandler.getElisoftFile().getPath()), fop);
            fop.close();
            loadData();
            AlertCreator.showWindow("Dane Elisoft zostały wczytane", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            AlertCreator.showWindow("Nie można wczytać danych Elisoft", Alert.AlertType.ERROR);
        }
    }

    public void addPath() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.setPathFile();
        try {
            if(!fileHandler.getPathFile().getPath().isEmpty()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("data/Data.txt"));
                writer.write(fileHandler.getPathFile().getPath());
                writer.close();
                AlertCreator.showWindow("Wczytano ścieżkę", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            AlertCreator.showWindow("Nie udało się wczytać ścieżki", Alert.AlertType.ERROR);
        }
    }

    public void updateData() {
        ServerDataCheck.check();
        loadData();
    }

    public void legend() {
        AlertCreator.showWindow(
                """
                        Znaczenie funkcji:
                        z - produktu nie ma w bazie danych
                        m - produkt widnieje w bazie danych
                        t - odstęp z miejscem na tytuł
                        p - suma cen łącznych danej części
                        n - narzut dla powyższej sumy
                        s - suma z narzutem
                        kp - końcowa suma wszystkich części
                        kn - narzut dla sumy końcowej
                        ks - suma końcowa z narzutem
                     """,
        Alert.AlertType.INFORMATION);
    }

    private void loadData() {
        ElisoftReader elisoftReader = new ElisoftReader();
        elisoftReader.readData();

        ProductTableObject[] dataHandlers = new ProductTableObject[elisoftReader.rows];
        dataList.clear();
        for(int r = 0; r < elisoftReader.rows; r++) {
            String fvalue = elisoftReader.getValue(r, ProductTableView.colOfNetPrice);
            if(!fvalue.contains(".")) {
                fvalue = fvalue + ".00";
            } else {
                fvalue = fvalue + "00";
            }
            for(int i = fvalue.length()-1; i-3>0; i--) {
                if(fvalue.charAt(i-2) == '.') {
                    break;
                }
                fvalue = fvalue.substring(0, i);
            }
            dataHandlers[r] = new ProductTableObject(Math.round(Float.parseFloat(elisoftReader.getValue(r,0))), elisoftReader.getValue(r,2), fvalue);
            dataList.add(dataHandlers[r]);
        }
        productTableView.refresh();
    }

    public void clearTables() {
        InformationTableView.getTable().getItems().subList(0, InformationTableView.getTable().getItems().size()).clear();
        InformationTableView.getTable().getItems().addAll(
                new InformationTableObject("Nr Oferty", ""),
                new InformationTableObject("Data", ""),
                new InformationTableObject("Klient", ""),
                new InformationTableObject("Nazwa obiektu / instalacji", ""),
                new InformationTableObject("Towar / usługa", ""),
                new InformationTableObject("Opracował", "")

        );

        dataTableView.getItems().subList(0, dataTableView.getItems().size()).clear();
        dataTableView.getItems().addAll(
                new DataTableObject("", "t", "", "", "", "", "", "")
        );

        InformationTableView.getTable().refresh();
        dataTableView.refresh();
    }
}
