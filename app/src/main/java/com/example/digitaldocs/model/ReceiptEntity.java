package com.example.digitaldocs.model;

import java.util.ArrayList;
import java.util.List;

public class ReceiptEntity {

    private String storeName;
    private String date;
    private List<String> lineItems;
    private List<String> lineItemPrices;
    private double totalPrice;

    /*
    A receipt has a store name, date of purchase, total price and line item names and prices
    We have to separate the line items into two arrays as firebase doesn't support nested arrays or maps
    or anything like that
     */
    public ReceiptEntity(String storeName, String date, double totalPrice) {
        this.storeName = storeName;
        this.date = date;
        this.lineItems = new ArrayList<>();
        this.lineItemPrices = new ArrayList<>();
        this.totalPrice = totalPrice;
    }

    public ReceiptEntity() { // to add info from custom classes to firebase we need an empty constructor
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getLineItems() {
        return lineItems;
    }

    public List<String> getLineItemPrices() {
        return lineItemPrices;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
