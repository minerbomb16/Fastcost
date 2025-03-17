package com.fastcost.tableObjects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductTableObject {
    private final SimpleIntegerProperty iD;
    private final SimpleStringProperty name;
    private final SimpleStringProperty netPrice;

    public ProductTableObject(Integer iD, String name, String netPrice) {
        this.iD = new SimpleIntegerProperty(iD);
        this.name = new SimpleStringProperty(name);
        this.netPrice = new SimpleStringProperty(netPrice);
    }

    public int getID() {
        return iD.get();
    }
    public void setIDCol(int iD) {
        this.iD.set(iD);
    }
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getNetPrice() {
        return netPrice.get();
    }
    public void setNetPrice(String netPrice) {
        this.netPrice.set(netPrice);
    }
}
