package org.example.entity;

public class Cart {

    private int cartId;
    private int customerId;
    private double total;
    private boolean purchased;

    public Cart(int cartId,int customerId,double total,boolean purchased){
        this.cartId= cartId;
        this.customerId = customerId;
        this.total = total;
        this.purchased = purchased;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}
