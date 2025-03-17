package com.fastcost;

import com.fastcost.buttons.LeftClickButtons;
import com.fastcost.buttons.MenuButtons;
import com.fastcost.tableViews.DataTableView;
import com.fastcost.tableViews.InformationTableView;
import com.fastcost.tableViews.ProductTableView;

import com.fastcost.tableObjects.ProductTableObject;
import com.fastcost.tableObjects.DataTableObject;
import com.fastcost.tableObjects.InformationTableObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static com.fastcost.Main.stage;


public class MainController implements Initializable {
    @FXML private TableView<ProductTableObject> productTableView;
    @FXML private TableColumn<ProductTableObject, String> IDCol;
    @FXML private TableColumn<ProductTableObject, String> NameCol;
    @FXML private TableColumn<ProductTableObject, String> NetPriceCol;

    @FXML private TableView<InformationTableObject> informationTableView;
    @FXML private TableColumn<InformationTableObject, String> DescriptionCol;
    @FXML private TableColumn<InformationTableObject, String> InformationCol;

    @FXML private TableView<DataTableObject> dataTableView;
    @FXML private TableColumn<DataTableObject, String> OrdinalNumber;
    @FXML private TableColumn<DataTableObject, String> Function;
    @FXML private TableColumn<DataTableObject, String> ID;
    @FXML private TableColumn<DataTableObject, String> ElementName;
    @FXML private TableColumn<DataTableObject, String> Piece;
    @FXML private TableColumn<DataTableObject, String> UnitPrice;
    @FXML private TableColumn<DataTableObject, String> TotalPrice;
    @FXML private TableColumn<DataTableObject, String> Comments;

    @FXML private MenuButton MenuButton;

    private final ObservableList<ProductTableObject> dataList = FXCollections.observableArrayList();
    public TextField hiddenSearch = new TextField();
    private final LeftClickButtons leftClickButtons = new LeftClickButtons();
    private final MenuButtons visibleButtons = new MenuButtons(dataList);

    public void setArgs(String[] args) {
        if (args.length > 0) {
            visibleButtons.loadFile(args[0]);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (Exception e) {
            System.err.println("ERROR: Directory 'data' cannot be created!");
        }

        new ProductTableView(productTableView, IDCol, NameCol, dataList, NetPriceCol, hiddenSearch).initializeTable();
        new DataTableView(dataTableView, OrdinalNumber, Function, ID, ElementName, Piece, UnitPrice, TotalPrice, Comments, dataList, hiddenSearch, productTableView).initializeTable();
        new InformationTableView(informationTableView, DescriptionCol, InformationCol).initializeTable();

        MenuButton.layoutXProperty().bind(stage.widthProperty().add(-83));
    }

    @FXML
    public void onAddButtonClick() { leftClickButtons.add(); }

    @FXML
    public void onDeleteButtonClick() {
        leftClickButtons.delete();
    }

    @FXML
    public void onTitleButtonClick() {
        leftClickButtons.title();
    }

    @FXML
    public void onSummaryButtonClick() {
        leftClickButtons.summary();
    }

    @FXML
    public void onFinalSummaryButtonClick() {
        leftClickButtons.finalSummary();
    }

    @FXML
    private void onNewDocumentButtonClick() { visibleButtons.newDocument(); }

    @FXML
    private void onSaveButtonClick() { visibleButtons.save(); }

    @FXML
    private void onSaveAsButtonClick() { visibleButtons.saveAs(); }

    @FXML
    private void onLoadFileButtonClick() { visibleButtons.loadFile(); }

    @FXML
    private void onPDFButtonClick() { visibleButtons.createPDF(); }

    @FXML
    private void onLoadElisoftDataButtonClick() { visibleButtons.loadElisotData(); }

    @FXML
    public void onAddPathButtonClick() { visibleButtons.addPath(); }

    @FXML
    public void onUpdateDataButtonClick() { visibleButtons.updateData(); }

    @FXML
    public void onLegendButtonClick() { visibleButtons.legend(); }

}