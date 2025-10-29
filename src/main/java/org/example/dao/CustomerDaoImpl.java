package org.example.dao;

import org.example.entity.Customer;

public class CustomerDaoImpl implements CustomerDao {
    public boolean addCustomer(Customer c) {

        System.out.println("Customer added: " + c.getCustomer_name()
                +"\n"+c.getCustomer_email()
                +"\n"+ c.getCustomer_password()
                );
        //have to do db connection

        return false;
    }
}
