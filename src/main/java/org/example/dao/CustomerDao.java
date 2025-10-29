package org.example.dao;

import org.example.entity.Customer;

public interface CustomerDao{

    public Customer loginCustomer();
    public boolean addCustomer(String name, String email, String phone, String pwd);

    // boolean addCustomer(Customer name);
}
