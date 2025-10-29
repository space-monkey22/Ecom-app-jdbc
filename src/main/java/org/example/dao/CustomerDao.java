package org.example.dao;

import org.example.entity.Customer;

public interface CustomerDao{

    public Customer loginCustomer();
    public boolean registerCustomer();

    // boolean addCustomer(Customer name);
}
