package com.fastcost.buttons;

import com.fastcost.tableObjects.DataTableObject;

import static com.fastcost.tableViews.DataTableView.dataTableView;

public class LeftClickButtons {
    public LeftClickButtons() {}

    public void add() {
        DataTableObject data = new DataTableObject("","z","","","","","","");
        int row = dataTableView.getSelectionModel().getSelectedIndex();
        addRow(data, row);
    }

    public void delete() {
        int row = dataTableView.getSelectionModel().getSelectedIndex();
        if(row >= 0) {
            String funkcja = dataTableView.getColumns().get(1).getCellObservableValue(dataTableView.getSelectionModel().getSelectedIndex()).getValue().toString();
            if(funkcja.equals("m") || funkcja.equals("z") || funkcja.equals("t")) {
                dataTableView.getItems().remove(row);
            } else {
                if(funkcja.equals("p") || funkcja.equals("kp")) {
                    threeRowRemove(row);
                } else if (funkcja.equals("n") || funkcja.equals("kn")) {
                    row-=1;
                    threeRowRemove(row);
                } else {
                    row-=2;
                    threeRowRemove(row);
                }
            }
            dataTableView.getSelectionModel().clearSelection();
        }
    }

    public void title() {
        DataTableObject data = new DataTableObject("","t","","","","","","");
        int row = dataTableView.getSelectionModel().getSelectedIndex();
        addRow(data, row);
    }

    public void summary() {
        DataTableObject data1 = new DataTableObject("","p","","","","Suma","","");
        DataTableObject data2 = new DataTableObject("","n","","","","Narzut w %","","");
        DataTableObject data3 = new DataTableObject("","s","","","","Suma             z narzutem","","");
        int row = dataTableView.getSelectionModel().getSelectedIndex();
        try {
            String funkcja = dataTableView.getColumns().get(1).getCellObservableValue(dataTableView.getSelectionModel().getSelectedIndex()).getValue().toString();
            if(funkcja.equals("p") || funkcja.equals("pk")) {
                row+=2;
            } else if(funkcja.equals("n") || funkcja.equals("nk")) {
                row++;
            }
            if (row >= 0) {
                dataTableView.getItems().add(row + 1, data1);
                dataTableView.getItems().add(row + 2, data2);
                dataTableView.getItems().add(row + 3, data3);
                dataTableView.getSelectionModel().clearSelection();
            } else {
                dataTableView.getItems().add(data1);
                dataTableView.getItems().add(data2);
                dataTableView.getItems().add(data3);
            }
        } catch (Exception e) {
            dataTableView.getItems().add(data1);
            dataTableView.getItems().add(data2);
            dataTableView.getItems().add(data3);
        }
    }

    public void finalSummary() {
        DataTableObject data1 = new DataTableObject("","kp","","","","Suma końcowa","","");
        DataTableObject data2 = new DataTableObject("","kn","","","","Narzut w %","","");
        DataTableObject data3 = new DataTableObject("","ks","","","","Suma końcowa       z narzutem","","");
        dataTableView.getItems().add(data1);
        dataTableView.getItems().add(data2);
        dataTableView.getItems().add(data3);
    }

    private static void threeRowRemove(int row) {
        dataTableView.getItems().remove(row);
        dataTableView.getItems().remove(row);
        dataTableView.getItems().remove(row);
    }

    private static void addRow(DataTableObject data, int row) {
        try {
            String function = dataTableView.getColumns().get(1).getCellObservableValue(dataTableView.getSelectionModel().getSelectedIndex()).getValue().toString();
            if(function.equals("p") || function.equals("kp")) {
                row+=2;
            } else if(function.equals("n") || function.equals("kn")) {
                row++;
            }
            if (row >= 0) {
                dataTableView.getItems().add(row + 1, data);
                dataTableView.getSelectionModel().clearSelection();
            } else {
                dataTableView.getItems().add(data);
            }
        } catch (Exception e) {
            dataTableView.getItems().add(data);
        }
    }
}
