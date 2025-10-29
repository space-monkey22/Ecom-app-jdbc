package org.example.entity;
import java.util.Date;

public class Order {
    private int orderid;
    private int customerid;
    private Date date;
    private double amount;
    private String status;

    public Order(int orderid, int customerid, Date date, double amount, String status) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.date = date;
        this.amount = amount;
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
