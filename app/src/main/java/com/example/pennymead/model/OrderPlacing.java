package com.example.pennymead.model;

import java.util.List;

public class OrderPlacing {

    private String name;
    private String email;
    private String address1;
    private String address2;
    private String hphone;
    private String county;
    private String postcode;
    private String country;
    private String town;
    private String payment;

    private String message;
    private Integer promotional_emails;
    private List<OrderCartItems> items;

    public OrderPlacing(String name, String email, String address1, String address2, String hphone, String county, String postcode, String country, String town, String payment, String message, Integer promotional_emails, List<OrderCartItems> items) {
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.hphone = hphone;
        this.county = county;
        this.postcode = postcode;
        this.country = country;
        this.town = town;
        this.payment = payment;
        this.message = message;
        this.promotional_emails = promotional_emails;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHphone() {
        return hphone;
    }

    public void setHphone(String hphone) {
        this.hphone = hphone;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPromotionalEmails() {
        return promotional_emails;
    }

    public void setPromotionalEmails(Integer promotional_emails) {
        this.promotional_emails = promotional_emails;
    }

    public List<OrderCartItems> getItems() {
        return items;
    }

    public void setItems(List<OrderCartItems> items) {
        this.items = items;
    }

}
