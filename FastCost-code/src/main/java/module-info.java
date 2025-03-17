module com.fastcost {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;


    opens com.fastcost to javafx.fxml;
    exports com.fastcost;
    exports com.fastcost.operations;
    exports com.fastcost.buttons;
    opens com.fastcost.operations to javafx.fxml;
    exports com.fastcost.tableObjects;
    opens com.fastcost.tableObjects to javafx.fxml;
    exports com.fastcost.fileOperations;
    opens com.fastcost.fileOperations to javafx.fxml;
}