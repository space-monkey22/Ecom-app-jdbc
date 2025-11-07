package org.example.dao;
import org.example.entity.Customer;

public interface CustomerDao{

    public Customer authorizeUser(String email, String pwd);
    public boolean addCustomer(String name, String email, String phone, String pwd);
    public boolean deleteCustomer(int id);
    public boolean updateCustomer(int id, Customer customer);
    public Customer fetchCustomer(int id);
}
