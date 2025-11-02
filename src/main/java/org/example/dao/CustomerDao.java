package org.example.dao;

import org.example.entity.Customer;

public interface CustomerDao{

    public Customer authorizeUser(String email, String pwd);
    public boolean addCustomer(String name, String email, String phone, String pwd);
    public boolean deleteCustomer(String id);
    public boolean updateCustomer(String id, Customer customer);
    public Customer fetchCustomer(String id);
}
