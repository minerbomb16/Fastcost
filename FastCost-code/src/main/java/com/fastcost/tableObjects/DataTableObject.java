package com.fastcost.tableObjects;

public class DataTableObject {
    private String ordinalNumber;
    private String function;
    private String iD;
    private String elementName;
    private String piece;
    private String unitPrice;
    private String totalPrice;
    private String comments;

    public DataTableObject() {}

    public DataTableObject(String ordinalNumber, String function, String iD, String elementName, String piece, String unitPrice, String totalPrice, String comments) {
        super();
        this.ordinalNumber = ordinalNumber;
        this.function = function;
        this.iD = iD;
        this.elementName = elementName;
        this.piece = piece;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.comments = comments;
    }

    public String getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(String ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
