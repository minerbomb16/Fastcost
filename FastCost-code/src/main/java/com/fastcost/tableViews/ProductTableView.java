package com.fastcost.tableViews;

import com.fastcost.MainController;
import com.fastcost.customCells.EditableStringTableCell;
import com.fastcost.operations.StringDecimalSwap;
import com.fastcost.tableObjects.ProductTableObject;
import com.fastcost.operations.ElisoftReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

import static com.fastcost.Main.stage;

public class ProductTableView {
    public static TableView<ProductTableObject> productTableView;
    private final TableColumn<ProductTableObject, String> IDCol;
    private final TableColumn<ProductTableObject, String> NameCol;
    private final ObservableList<ProductTableObject> dataList;
    private final TableColumn<ProductTableObject, String> NetPriceCol;
    private FilteredList<ProductTableObject> filteredData;
    private final TextField hiedenSearch;
    int colOfID = 0;
    int colOfName = 2;
    public static int colOfNetPrice = 12;

    public ProductTableView(TableView<ProductTableObject> productTableView, TableColumn<ProductTableObject, String> IDCol, TableColumn<ProductTableObject, String> NameCol, ObservableList<ProductTableObject> dataList, TableColumn<ProductTableObject, String> NetPriceCol, TextField hiedenSearch) {
        ProductTableView.productTableView = productTableView;
        this.IDCol = IDCol;
        this.NameCol = NameCol;
        this.dataList = dataList;
        this.NetPriceCol = NetPriceCol;
        this.hiedenSearch = hiedenSearch;
    }

    public void initializeTable() {
        IDCol.setCellValueFactory(new PropertyValueFactory<>("iD"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        NetPriceCol.setCellValueFactory(new PropertyValueFactory<>("netPrice"));

        IDCol.setReorderable(false);
        NameCol.setReorderable(false);
        NetPriceCol.setReorderable(false);

        NameCol.setCellFactory(tc -> new EditableStringTableCell<>());

        ElisoftReader elisoftReader = new ElisoftReader();
        elisoftReader.readData();

        ProductTableObject[] dataHandlers = new ProductTableObject[elisoftReader.rows];
        dataList.clear();
        for(int r = 0; r < elisoftReader.rows; r++) {
            dataHandlers[r] = new ProductTableObject(Math.round(Float.parseFloat(elisoftReader.getValue(r,colOfID))), elisoftReader.getValue(r,colOfName), StringDecimalSwap.decimalToString(new BigDecimal(elisoftReader.getValue(r,colOfNetPrice))));
            dataList.add(dataHandlers[r]);
        }

        filteredData = new FilteredList<>(dataList, b -> true);

        hiedenSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(dataHandler -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (String.valueOf(dataHandler.getID()).contains(lowerCaseFilter)) {
                return true;
            } else {
                return dataHandler.getName().toLowerCase().contains(lowerCaseFilter);
            }
        }));

        SortedList<ProductTableObject> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
        productTableView.setItems(sortedData);

        productTableView.prefWidthProperty().bind(stage.widthProperty().divide(3).add(-35));
        productTableView.prefHeightProperty().bind(stage.heightProperty().add(-280));
        NameCol.prefWidthProperty().bind(productTableView.widthProperty().add(-144));
    }
}
