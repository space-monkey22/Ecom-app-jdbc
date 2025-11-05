package org.example.entity;

public class Customer {
    private String customer_name;
    private int customer_id;
    private String customer_email;
    private String shippingAddress;

    public Customer(){}

    public Customer(String customer_name, int customer_id, String customer_email){
        this.customer_email = customer_email;
        this.customer_name = customer_name;
        this.customer_id = customer_id;
    }
    public Customer(String customer_name, String customer_email){
        this.customer_email = customer_email;
        this.customer_name = customer_name;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public String getCustomerEmail() {
        return customer_email;
    }
    public void setCustomerEmail(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return
                "customer_name='" + customer_name + '\'' +
                ", customer_id=" + customer_id +
                ", customer_email='" + customer_email ;
    }
}
