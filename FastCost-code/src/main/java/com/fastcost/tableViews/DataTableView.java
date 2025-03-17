package com.fastcost.tableViews;

import com.fastcost.customCells.*;
import com.fastcost.operations.StringDecimalSwap;
import com.fastcost.tableObjects.DataTableObject;
import com.fastcost.tableObjects.ProductTableObject;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

import static com.fastcost.Main.stage;

public class DataTableView {

    public static TableView<DataTableObject> dataTableView;
    private final TableColumn<DataTableObject, String> OrdinalNumber;
    private final TableColumn<DataTableObject, String> Function;
    private final TableColumn<DataTableObject, String> ID;
    private final TableColumn<DataTableObject, String> ElementName;
    private final TableColumn<DataTableObject, String> Piece;
    private final TableColumn<DataTableObject, String> UnitPrice;
    private final TableColumn<DataTableObject, String> TotalPrice;
    private final TableColumn<DataTableObject, String> Comments;
    private final ObservableList<ProductTableObject> dataList;
    private final TextField hiddenSearch;
    private final TableView<ProductTableObject> productTableView;
    private DataTableObject selectedData = new DataTableObject();

    public DataTableView(TableView<DataTableObject> dataTableView,
                         TableColumn<DataTableObject, String> OrdinalNumber,
                         TableColumn<DataTableObject, String> Function,
                         TableColumn<DataTableObject, String> ID,
                         TableColumn<DataTableObject, String> ElementName,
                         TableColumn<DataTableObject, String> Piece,
                         TableColumn<DataTableObject, String> UnitPrice,
                         TableColumn<DataTableObject, String> TotalPrice,
                         TableColumn<DataTableObject, String> Comments,
                         ObservableList<ProductTableObject> dataList,
                         TextField hiddenSearch,
                         TableView<ProductTableObject> productTableView) {
        DataTableView.dataTableView = dataTableView;
        this.OrdinalNumber = OrdinalNumber;
        this.Function = Function;
        this.ID = ID;
        this.ElementName = ElementName;
        this.Piece = Piece;
        this.UnitPrice = UnitPrice;
        this.TotalPrice = TotalPrice;
        this.Comments = Comments;
        this.dataList = dataList;
        this.hiddenSearch = hiddenSearch;
        this.productTableView = productTableView;
    }

    public void initializeTable() {
        dataTableView.setEditable(true);

        OrdinalNumber.setCellValueFactory(new PropertyValueFactory<>("ordinalNumber"));
        Function.setCellValueFactory(new PropertyValueFactory<>("function"));
        ID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        ElementName.setCellValueFactory(new PropertyValueFactory<>("elementName"));
        Piece.setCellValueFactory(new PropertyValueFactory<>("piece"));
        UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        TotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        Comments.setCellValueFactory(new PropertyValueFactory<>("comments"));

        OrdinalNumber.setReorderable(false);
        Function.setReorderable(false);
        ID.setReorderable(false);
        ElementName.setReorderable(false);
        Piece.setReorderable(false);
        UnitPrice.setReorderable(false);
        TotalPrice.setReorderable(false);
        Comments.setReorderable(false);

        OrdinalNumber.setCellFactory(tc -> new EditableStringTableCell<DataTableObject, String>());
        Function.setCellFactory(tc -> new EditableStringTableCell<DataTableObject, String>());
        ID.setCellFactory(tc -> new SearchTableCell<DataTableObject, String>(hiddenSearch));
        ElementName.setCellFactory(tc ->  new SearchTableCell<DataTableObject, String>(hiddenSearch));
        Piece.setCellFactory(tc -> new PieceCell<DataTableObject, String>());
        UnitPrice.setCellFactory(tc -> new UnitPriceCell<>(dataList));
        TotalPrice.setCellFactory(tc -> new TotalPriceCell<>());
        Comments.setCellFactory(tc -> new EditableStringTableCell<>());

        OrdinalNumber.setEditable(false);
        Function.setEditable(false);

        OrdinalNumber.setSortable(false);
        Function.setSortable(false);
        ID.setSortable(false);
        ElementName.setSortable(false);
        Piece.setSortable(false);
        UnitPrice.setSortable(false);
        TotalPrice.setSortable(false);
        Comments.setSortable(false);

        ID.setOnEditCommit(event -> {
            DataTableObject data = event.getRowValue();
            if(!data.getFunction().equals("t")) {
                data.setID(event.getNewValue());
                int id = checkExistId(data.getID());
                updateData(data, id);
                updateTotalPrice(data);
            }
            dataTableView.refresh();
        });

        ID.setOnEditStart(this::search);

        ElementName.setOnEditCommit(event -> {
            DataTableObject data = event.getRowValue();
            data.setElementName(event.getNewValue());
            if(!data.getFunction().equals("t")) {
                int id = checkExistName(data.getElementName());
                updateData(data, id);
                updateTotalPrice(data);
            }
            dataTableView.refresh();
        });

        ElementName.setOnEditStart(this::search);

        Piece.setOnEditCommit(event -> {
            DataTableObject data = event.getRowValue();
            if(!data.getFunction().equals("t")) {
                data.setPiece(StringDecimalSwap.stringDecimalFormatOption(event.getNewValue(), 0));
                updateTotalPrice(data);
            }
            dataTableView.refresh();
        });

        UnitPrice.setOnEditCommit(event -> {
            DataTableObject data = event.getRowValue();
            if(!data.getFunction().equals("t")) {
                data.setUnitPrice(StringDecimalSwap.stringDecimalFormatOption(event.getNewValue(), 1));
                updateTotalPrice(data);
            }
            dataTableView.refresh();
        });

        TotalPrice.setOnEditCommit(event -> {
            DataTableObject data = event.getRowValue();
            if(!data.getFunction().equals("t")) {
                if (!(data.getFunction().equals("m") || data.getFunction().equals("z"))) {
                    data.setTotalPrice(StringDecimalSwap.stringDecimalFormatOption(event.getNewValue(), 1));
                }
            }
            dataTableView.refresh();
        });

        Comments.setOnEditCommit(event -> {
            DataTableObject data = event.getRowValue();
            if(!data.getFunction().equals("t")) {
                data.setComments(event.getNewValue());
            }
            dataTableView.refresh();
        });

        OrdinalNumber.setCellValueFactory(dataStringCellDataFeatures -> {
            int value = dataTableView.getItems().indexOf(dataStringCellDataFeatures.getValue()) + 1;
            return new ReadOnlyObjectWrapper<>(String.valueOf(value));
        });

        productTableView.setRowFactory(tv -> {
            TableRow<ProductTableObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    if(selectedData.getFunction() != null && !selectedData.getFunction().equals("t")) {
                        ProductTableObject rowData = row.getItem();
                        selectedData.setFunction("m");
                        selectedData.setID(String.valueOf(rowData.getID()));
                        selectedData.setElementName(rowData.getName());
                        selectedData.setUnitPrice(rowData.getNetPrice());
                        updateTotalPrice(selectedData);
                        dataTableView.refresh();
                    }
                }
            });
            return row;
        });

        dataTableView.getItems().addAll(new DataTableObject("", "t", "", "", "", "", "", ""));
        dataTableView.layoutXProperty().bind(stage.widthProperty().divide(3));
        dataTableView.prefWidthProperty().bind(stage.widthProperty().divide(1.5).add(-45));
        dataTableView.prefHeightProperty().bind(stage.heightProperty().add(-110));
        ElementName.prefWidthProperty().bind(dataTableView.widthProperty().add(-456));
    }

    private void search(TableColumn.CellEditEvent<DataTableObject, String> event) {
        if (event.getOldValue() != null) {
            hiddenSearch.setText(event.getOldValue());
        }
        if (event.getOldValue() == null) {
            hiddenSearch.setText("");
        }
        if (hiddenSearch.getText() == null) {
            hiddenSearch.setText("");
        }
        selectedData = event.getRowValue();
    }

    private void updateTotalPrice(DataTableObject data) {
        if(data.getID().equals("") && data.getElementName().equals("") && data.getPiece().equals("") && data.getUnitPrice().equals("")) {
            data.setTotalPrice("");
        } else {
            BigDecimal piece = StringDecimalSwap.stringToDecimal(data.getPiece());
            BigDecimal unitPrice = StringDecimalSwap.stringToDecimal(data.getUnitPrice());
            data.setTotalPrice(StringDecimalSwap.decimalToString(piece.multiply(unitPrice)));
        }
    }

    private void updateData(DataTableObject data, int id) {
        //tutaj
        if(id == -1) {
            if(data.getFunction().equals("m")) {
                data.setUnitPrice("0.00");
            }
            data.setFunction("z");
            data.setID("");
        } else {
            data.setFunction("m");
            for(ProductTableObject item: productTableView.getItems()) {
                if(item.getID() == id) {
                    data.setID(String.valueOf(id));
                    data.setElementName(item.getName());
                    data.setUnitPrice(item.getNetPrice());
                    break;
                }
            }
        }
    }

    private int checkExistName(String name) {
        for(ProductTableObject item: productTableView.getItems()) {
            if(item.getName().equalsIgnoreCase(name)) {
                return item.getID();
            }
        }
        return -1;
    }

    private int checkExistId(String id) {
        for(ProductTableObject item: productTableView.getItems()) {
            try {
                if (item.getID() == Integer.parseInt(id)) {
                    return item.getID();
                }
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    public static void calculateSum() {
        BigDecimal sum = new BigDecimal("0.00");
        BigDecimal sumOfAll = new BigDecimal("0.00");
        for(int i = 0; i < dataTableView.getItems().size(); i++) {
            DataTableObject rowData = dataTableView.getItems().get(i);
            switch (rowData.getFunction()) {
                case "m", "z" -> sum = sum.add(StringDecimalSwap.stringToDecimal(rowData.getTotalPrice()));
                case "p" -> rowData.setTotalPrice(StringDecimalSwap.decimalToString(sum));
                case "n" -> sum = sum.add(sum.multiply(StringDecimalSwap.stringToDecimal(rowData.getTotalPrice())).multiply(BigDecimal.valueOf(0.01)));
                case "s" -> {
                    rowData.setTotalPrice(StringDecimalSwap.decimalToString(sum));
                    sumOfAll = sumOfAll.add(sum);
                    sum = new BigDecimal("0.00");
                }
                case "kp" -> {
                    if (sum.equals(new BigDecimal("0.00"))) {
                        rowData.setTotalPrice(StringDecimalSwap.decimalToString(sumOfAll));
                    } else {
                        rowData.setTotalPrice("0.00");
                    }
                }
                case "kn" -> sumOfAll = sumOfAll.add(sumOfAll.multiply(StringDecimalSwap.stringToDecimal(rowData.getTotalPrice())).multiply(BigDecimal.valueOf(0.01)));
                case "ks" -> {
                    if (sum.equals(new BigDecimal("0.00"))) {
                        rowData.setTotalPrice(StringDecimalSwap.decimalToString(sumOfAll));
                        sumOfAll = new BigDecimal("0.00");
                    } else {
                        rowData.setTotalPrice("0.00");
                    }
                }
            }
        }
    }
}
