package com.fastcost.customCells;

import com.fastcost.tableObjects.DataTableObject;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class PieceCell<T, E> extends EditableStringTableCell<T, String> {
    @Override
    public void startEdit() {
        if(checkIfPartOfSum((DataTableObject)getTableView().getItems().get(getIndex()))) {
            super.startEdit();
        }
    }
}
