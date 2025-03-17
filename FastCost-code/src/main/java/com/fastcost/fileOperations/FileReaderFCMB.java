package com.fastcost.fileOperations;

import com.fastcost.tableObjects.DataTableObject;
import com.fastcost.tableObjects.InformationTableObject;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileReaderFCMB {
    private final String path;
    private final TableView<DataTableObject> dataTableView;
    private final TableView<InformationTableObject> informationsTableView;

    public FileReaderFCMB(String path, TableView<DataTableObject> dataTableView, TableView<InformationTableObject> informationsTableView) {
        this.path = path;
        this.dataTableView = dataTableView;
        this.informationsTableView = informationsTableView;
    }

    public void read() {
        File file = new File(path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            informationsTableView.getItems().subList(0, informationsTableView.getItems().size()).clear();
            informationsTableView.getItems().addAll(
                    new InformationTableObject("Nr Oferty", bufferedReader.readLine()),
                    new InformationTableObject("Data", bufferedReader.readLine()),
                    new InformationTableObject("Klient", bufferedReader.readLine()),
                    new InformationTableObject("Nazwa obiektu / instalacji", bufferedReader.readLine()),
                    new InformationTableObject("Towar / usługa", bufferedReader.readLine()),
                    new InformationTableObject("Opracował", bufferedReader.readLine())

            );

            dataTableView.getItems().subList(0, dataTableView.getItems().size()).clear();
            String line;
            int counter = 0;
            String[] row = new String[8];
            while ((line = bufferedReader.readLine()) != null) {
                if(counter%8 != 7) {
                    row[counter%8] = line;
                } else {
                    row[counter%8] = line;
                    dataTableView.getItems().addAll(new DataTableObject(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
                }
                counter++;
            }
        } catch (IOException e) {
            System.out.println("ERROR: Cannot read from file!");
        }
    }

}
