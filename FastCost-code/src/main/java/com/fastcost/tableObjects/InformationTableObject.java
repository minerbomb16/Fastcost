package com.fastcost.tableObjects;

public class InformationTableObject {
    private String description;
    private String information;

    public InformationTableObject(String description, String information) {
        this.description = description;
        this.information = information;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getInformation() { return information; }
    public void setInformation(String information) { this.information = information; }
}