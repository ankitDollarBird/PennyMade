package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetail {

    @SerializedName("custno")
    private String custno;
    @SerializedName("orderno")
    private String orderno;
    @SerializedName("ordate")
    private String ordate;
    @SerializedName("items")
    private String items;
    @SerializedName("postage")
    private Object postage;
    @SerializedName("pricechange")
    private Object pricechange;
    @SerializedName("sellnotes")
    private Object sellnotes;
    @SerializedName("buynotes")
    private String buynotes;
    @SerializedName("invoiced")
    private String invoiced;
    @SerializedName("transtatus")
    private String transtatus;
    @SerializedName("trantime")
    private String trantime;
    @SerializedName("cardprefix")
    private String cardprefix;
    @SerializedName("paymeth")
    private String paymeth;
    @SerializedName("trantimestamp")
    private String trantimestamp;
    @SerializedName("address1")
    private String address1;
    @SerializedName("address2")
    private String address2;
    @SerializedName("town")
    private String town;
    @SerializedName("county")
    private String county;
    @SerializedName("postcode")
    private String postcode;
    @SerializedName("country")
    private String country;
    @SerializedName("status")
    private String status;
    @SerializedName("package_id")
    private Object packageId;
    @SerializedName("order_status")
    private Object orderStatus;
    @SerializedName("order_note")
    private Object orderNote;

    public String getCustno() {
        return custno;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getOrdate() {
        return ordate;
    }

    public void setOrdate(String ordate) {
        this.ordate = ordate;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Object getPostage() {
        return postage;
    }

    public void setPostage(Object postage) {
        this.postage = postage;
    }

    public Object getPricechange() {
        return pricechange;
    }

    public void setPricechange(Object pricechange) {
        this.pricechange = pricechange;
    }

    public Object getSellnotes() {
        return sellnotes;
    }

    public void setSellnotes(Object sellnotes) {
        this.sellnotes = sellnotes;
    }

    public String getBuynotes() {
        return buynotes;
    }

    public void setBuynotes(String buynotes) {
        this.buynotes = buynotes;
    }

    public String getInvoiced() {
        return invoiced;
    }

    public void setInvoiced(String invoiced) {
        this.invoiced = invoiced;
    }

    public String getTranstatus() {
        return transtatus;
    }

    public void setTranstatus(String transtatus) {
        this.transtatus = transtatus;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

    public String getCardprefix() {
        return cardprefix;
    }

    public void setCardprefix(String cardprefix) {
        this.cardprefix = cardprefix;
    }

    public String getPaymeth() {
        return paymeth;
    }

    public void setPaymeth(String paymeth) {
        this.paymeth = paymeth;
    }

    public String getTrantimestamp() {
        return trantimestamp;
    }

    public void setTrantimestamp(String trantimestamp) {
        this.trantimestamp = trantimestamp;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getPackageId() {
        return packageId;
    }

    public void setPackageId(Object packageId) {
        this.packageId = packageId;
    }

    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(Object orderNote) {
        this.orderNote = orderNote;
    }
}
