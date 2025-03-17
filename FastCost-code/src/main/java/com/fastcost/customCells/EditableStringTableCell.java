package com.fastcost.customCells;

import com.fastcost.tableObjects.DataTableObject;
import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import static com.fastcost.tableViews.DataTableView.dataTableView;

public class EditableStringTableCell<T, E> extends TableCell<T, String> {
    protected TextField textField;
    protected Text text;
    protected ChangeListener<? super Boolean> changeListener = (obs,ov, nv) -> {
        if (!nv) {
            commitEdit(textField.getText());
        }
    };

    public EditableStringTableCell () {}

    @Override
    public void startEdit() {
        if(editableProperty().get()){
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.requestFocus();
                textField.positionCaret(textField.getText().length());
            }
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
        if (getItem() != null) {
            text = new Text(getItem());
            text.setWrappingWidth(super.getWidth());
            this.setWrapText(true);
            setText(null);
            setGraphic(text);
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        update(item, empty);

        if(getTableView().equals(dataTableView)) {
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
    }

    protected void update(String item, boolean empty) {
        super.updateItem(item, empty);

        if (isEmpty()) {
            setText(null);
            setGraphic(null);
        } else {
            if (item != null) {
                text = new Text(item);
                text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(10));
                this.setWrapText(true);
                setText(null);
                setGraphic(text);
            } else {
                this.setWrapText(true);
                setText(getString());
                setGraphic(null);
            }
        }
    }

    protected void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(changeListener);
        textField.setOnAction(evt -> commitEdit(textField.getText()));

        textField.setOnKeyPressed((ke) -> {
            if (ke.getCode().equals(KeyCode.ESCAPE)) {
                textField.focusedProperty().removeListener(changeListener);
                cancelEdit();
            }
            if (ke.getCode().equals(KeyCode.TAB)) {
                commitEdit(textField.getText());
            }
        });
    }

    protected String getString() {
        return getItem() == null ? "" : getItem();
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void commitEdit(String item) {
        textField.focusedProperty().removeListener(changeListener);
        if (isEditing()) {
            super.commitEdit(item);
        } else {
            final TableView table = getTableView();
            if (table != null) {
                TablePosition position = new TablePosition(getTableView(),
                        getTableRow().getIndex(), getTableColumn());
                CellEditEvent editEvent = new CellEditEvent(table, position,
                        TableColumn.editCommitEvent(), item);
                Event.fireEvent(getTableColumn(), editEvent);
            }
            updateItem(item, false);
            if (table != null) {
                table.edit(-1, null);
            }

        }
    }

    protected boolean checkIfPartOfSum(DataTableObject currentData) {
        String func = currentData.getFunction();
        return func.equals("z") || func.equals("m") || func.equals("t");
    }

}
