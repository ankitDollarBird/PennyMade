package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

public class OrderPlacedDetail {
    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("customerid")
    private String customerid;
    @SerializedName("customerEmailid")
    private String customerEmailid;
    @SerializedName("orderList")
    private String orderList;
    @SerializedName("ordernumber")
    private Integer ordernumber;
    @SerializedName("OrderDetail")
    private OrderDetail orderDetail;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomerEmailid() {
        return customerEmailid;
    }

    public void setCustomerEmailid(String customerEmailid) {
        this.customerEmailid = customerEmailid;
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
