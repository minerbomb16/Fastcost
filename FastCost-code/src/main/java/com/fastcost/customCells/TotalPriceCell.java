package com.fastcost.customCells;

import com.fastcost.tableViews.DataTableView;
import com.fastcost.tableObjects.DataTableObject;

public class TotalPriceCell<T, E> extends EditableStringTableCell<T, String> {

    public TotalPriceCell() {}

    @Override
    public void updateItem(String item, boolean empty) {
        update(item, empty);

        DataTableView.calculateSum();

        if(!empty) {
            DataTableObject currentData = (DataTableObject) getTableView().getItems().get(getIndex());
            if(currentData.getFunction().equals("t")) {
                setStyle("-fx-background-color: #4EB8D1");
            } else if (!(currentData.getFunction().equals("n") || currentData.getFunction().equals("kn")) && item.equals("0.00")) {
                setStyle("-fx-background-color: #FF7272");
            } else {
                setStyle("");
            }
        } else {
            setStyle("-fx-background-color: #FFFFFF");
        }
    }

}
