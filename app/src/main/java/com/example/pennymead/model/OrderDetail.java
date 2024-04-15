package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetail {
    @SerializedName("autoship")
    private Integer autoship;
    @SerializedName("payment")
    private String payment;
    @SerializedName("itemsdetail")
    private List<CollectableItemsListData> itemsdetail;
    @SerializedName("ordertotal")
    private Integer ordertotal;
    @SerializedName("postageandpacking")
    private Integer postageandpacking;
    @SerializedName("discount")
    private Integer discount;
    @SerializedName("grandtotal")
    private Integer grandtotal;
    @SerializedName("custmerdetail")
    private CustomerDetail custmerdetail;
    @SerializedName("countrydata")
    private List<OrderCountryDetail> countrydata;

    public Integer getAutoship() {
        return autoship;
    }

    public void setAutoship(Integer autoship) {
        this.autoship = autoship;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<CollectableItemsListData> getItemsdetail() {
        return itemsdetail;
    }

    public void setItemsdetail(List<CollectableItemsListData> itemsdetail) {
        this.itemsdetail = itemsdetail;
    }

    public Integer getOrdertotal() {
        return ordertotal;
    }

    public void setOrdertotal(Integer ordertotal) {
        this.ordertotal = ordertotal;
    }

    public Integer getPostageandpacking() {
        return postageandpacking;
    }

    public void setPostageandpacking(Integer postageandpacking) {
        this.postageandpacking = postageandpacking;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getGrandtotal() {
        return grandtotal;
    }

    public void setGrandtotal(Integer grandtotal) {
        this.grandtotal = grandtotal;
    }

    public CustomerDetail getCustmerdetail() {
        return custmerdetail;
    }

    public void setCustmerdetail(CustomerDetail custmerdetail) {
        this.custmerdetail = custmerdetail;
    }

    public List<OrderCountryDetail> getCountrydata() {
        return countrydata;
    }

    public void setCountrydata(List<OrderCountryDetail> countrydata) {
        this.countrydata = countrydata;
    }
}
