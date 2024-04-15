package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

public class CountryList {
    @SerializedName("iso")
    private String iso;
    @SerializedName("name")
    private String name;
    @SerializedName("printable_name")
    private String printableName;
    @SerializedName("iso3")
    private String iso3;
    @SerializedName("numcode")
    private String numcode;
    @SerializedName("shipzone")
    private String shipzone;
    @SerializedName("sortorder")
    private String sortorder;
    @SerializedName("sortorder1")
    private String sortorder1;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintableName() {
        return printableName;
    }

    public void setPrintableName(String printableName) {
        this.printableName = printableName;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getNumcode() {
        return numcode;
    }

    public void setNumcode(String numcode) {
        this.numcode = numcode;
    }

    public String getShipzone() {
        return shipzone;
    }

    public void setShipzone(String shipzone) {
        this.shipzone = shipzone;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getSortorder1() {
        return sortorder1;
    }

    public void setSortorder1(String sortorder1) {
        this.sortorder1 = sortorder1;
    }

}

