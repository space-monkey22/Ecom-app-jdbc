package org.example.entity;

public class Customer {
    private String customerName;
    private int customerId;
    private String customerEmail;
    private String shippingAddress;

    public Customer() {}

    public Customer(String customer_name, int customerId, String customerEmail) {
        this.customerEmail = customerEmail;
        this.customerName = customer_name;
        this.customerId = customerId;
    }

    public Customer(String customerName, String customerEmail){
        this.customerEmail = customerEmail;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerName = '" + customerName + '\'' +
                ", customerId = " + customerId +
                ", customerEmail = '" + customerEmail + '\'' +
                ", shippingAddress = '" + shippingAddress + '\'' +
                '}';
    }
}
