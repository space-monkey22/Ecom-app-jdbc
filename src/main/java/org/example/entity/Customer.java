package org.example.entity;

public class Customer {
    private String customer_name;
    private long customer_id;
    private String customer_email;

    public Customer(){}

    public Customer(String customer_name, long customer_id, String customer_email){
        this.customer_email = customer_email;
        this.customer_name = customer_name;
        this.customer_id = customer_id;
    }
    public Customer(String customer_name, String customer_email){
        this.customer_email = customer_email;
        this.customer_name = customer_name;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_email() {
        return customer_email;
    }
    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

//    public String getCustomer_password() {
//        return customer_password;
//    }
//    public void setCustomer_password(String customer_password) {
//        this.customer_password = customer_password;
//    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    @Override
    public String toString() {
        return
                "customer_name='" + customer_name + '\'' +
                ", customer_id=" + customer_id +
                ", customer_email='" + customer_email ;
    }
}
