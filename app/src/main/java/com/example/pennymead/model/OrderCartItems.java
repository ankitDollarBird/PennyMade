package com.example.pennymead.model;

public class OrderCartItems {
    private String sysid;
    private String pricePerItem;

    public OrderCartItems(String sysid, String pricePerItem) {
        this.sysid = sysid;
        this.pricePerItem = pricePerItem;
    }

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(String pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

}

