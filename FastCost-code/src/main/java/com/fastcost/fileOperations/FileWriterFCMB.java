package com.fastcost.fileOperations;

import com.fastcost.tableObjects.DataTableObject;
import com.fastcost.tableObjects.InformationTableObject;
import javafx.scene.control.TableView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterFCMB {
    private final String path;
    private final TableView<DataTableObject> dataTableView;
    private final TableView<InformationTableObject> informationsTableView;

    public FileWriterFCMB(String path, TableView<DataTableObject> dataTableView, TableView<InformationTableObject> informationsTableView) {
        this.path = path;
        this.dataTableView = dataTableView;
        this.informationsTableView = informationsTableView;
    }

    public void write() {
        try {
            File file = new File(path);
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(file));

            for(int i = 0; i < informationsTableView.getItems().size(); i++) {
                if(informationsTableView.getColumns().get(1).getCellObservableValue(i).getValue() != null){
                    outWriter.write(informationsTableView.getColumns().get(1).getCellObservableValue(i).getValue().toString());
                } else {
                    outWriter.write("");
                }
                outWriter.newLine();
            }

            for(int i = 0; i < dataTableView.getItems().size(); i++) {
                for(int j = 0; j < 8; j++) {
                    if(dataTableView.getColumns().get(j).getCellObservableValue(i).getValue() != null){
                        outWriter.write(dataTableView.getColumns().get(j).getCellObservableValue(i).getValue().toString());
                    } else {
                        outWriter.write("");
                    }
                    outWriter.newLine();
                }
            }
            outWriter.close();
        } catch (IOException e) {
            System.err.println("ERROR: Cannot write to file!");
        }
    }
}