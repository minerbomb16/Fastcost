package com.fastcost.customCells;

import com.fastcost.tableObjects.DataTableObject;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class SearchTableCell<T, E> extends EditableStringTableCell<T, String> {
    private final TextField hiddenSearch;

    public SearchTableCell (TextField hiddenSearch) {
        this.hiddenSearch = hiddenSearch;
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
            } else {
                setStyle("");
            }
        } else {
            setStyle("-fx-background-color: #FFFFFF");
        }
    }

    @Override
    protected void createTextField() {
        DataTableObject currentData = (DataTableObject)getTableView().getItems().get(getIndex());
        if(checkIfPartOfSum(currentData)) {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(changeListener);
            textField.setOnAction(evt -> commitEdit(textField.getText()));

            textField.setOnKeyTyped((ke) -> {
                String func = currentData.getFunction();
                if(!func.equals("t")) {
                    hiddenSearch.setText(textField.getText());
                }
                if (ke.getCode().equals(KeyCode.ESCAPE)) {
                    textField.focusedProperty().removeListener(changeListener);
                    cancelEdit();
                }
                if (ke.getCode().equals(KeyCode.TAB)) {
                    commitEdit(textField.getText());
                }
            });
        }
    }

}