package com.fastcost.tableViews;

import com.fastcost.customCells.EditableStringTableCell;
import com.fastcost.tableObjects.InformationTableObject;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.fastcost.Main.stage;

public class InformationTableView {
    private static TableView<InformationTableObject> informationTableView;
    private final TableColumn<InformationTableObject, String> DescriptionCol;
    private final TableColumn<InformationTableObject, String> InformationCol;

    public InformationTableView(TableView<InformationTableObject> informationsTableView, TableColumn<InformationTableObject, String> DescriptionCol, TableColumn<InformationTableObject, String> InformationCol) {
        InformationTableView.informationTableView = informationsTableView;
        this.DescriptionCol = DescriptionCol;
        this.InformationCol = InformationCol;
    }

    public static TableView<InformationTableObject> getTable() {
        return informationTableView;
    }

    public void initializeTable() {
        informationTableView.setEditable(true);

        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        InformationCol.setCellValueFactory(new PropertyValueFactory<>("information"));

        DescriptionCol.setCellFactory(tc -> new EditableStringTableCell<>());
        InformationCol.setCellFactory(tc -> new EditableStringTableCell<>());

        DescriptionCol.setReorderable(false);
        InformationCol.setReorderable(false);

        DescriptionCol.setSortable(false);
        InformationCol.setSortable(false);

        DescriptionCol.setEditable(false);

        InformationCol.setOnEditCommit(event -> {
            InformationTableObject informations = event.getRowValue();
            informations.setInformation(event.getNewValue());
            informationTableView.refresh();
        });

        informationTableView.getItems().addAll(
                new InformationTableObject("Nr Oferty", ""),
                new InformationTableObject("Data", ""),
                new InformationTableObject("Klient", ""),
                new InformationTableObject("Nazwa obiektu / instalacji", ""),
                new InformationTableObject("Towar / usługa", ""),
                new InformationTableObject("Opracował", "")
        );

        informationTableView.prefWidthProperty().bind(stage.widthProperty().divide(3).add(-35));
        InformationCol.prefWidthProperty().bind(informationTableView.widthProperty().add(-100));
    }
}
