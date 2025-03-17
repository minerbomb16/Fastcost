package com.fastcost.customCells;

import com.fastcost.tableObjects.DataTableObject;
import com.fastcost.tableObjects.ProductTableObject;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class UnitPriceCell<T, E> extends EditableStringTableCell<T, String> {
    private final ObservableList<ProductTableObject> dataList;

    public UnitPriceCell(ObservableList<ProductTableObject> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void startEdit() {
        if(checkIfPartOfSum((DataTableObject)getTableView().getItems().get(getIndex()))) {
            super.startEdit();
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        update(item, empty);

        if(!empty) {
            DataTableObject currentData = (DataTableObject) getTableView().getItems().get(getIndex());
            if(currentData.getFunction().equals("t")) {
                setStyle("-fx-background-color: #4EB8D1");
            } else if (!item.equals("") && checkExist(currentData.getElementName(), item)) {
                setStyle("-fx-background-color: #FEFF70");
            } else {
                setStyle("");
            }
        } else {
            setStyle("-fx-background-color: #FFFFFF");
        }
    }

    private boolean checkExist(String name, String price) {
        for (ProductTableObject item : dataList) {
            if (item.getName().equals(name) && item.getNetPrice().equals(price)) {
                return false;
            }
        }
        for (ProductTableObject item : dataList) {
            if (item.getName().equals(name) && !item.getNetPrice().equals(price)) {
                return true;
            }
        }
        return false;
    }

}
