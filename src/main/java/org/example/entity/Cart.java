package org.example.entity;

public class Cart {
    private int cartid;
    private int customerid;
    private double total;
    private int purchased;
    public Cart(int cartid,int customerid,double total,int purchased){
        this.cartid= cartid;
        this.customerid=customerid;
        this.total=total;
        this.purchased=purchased;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }
}
