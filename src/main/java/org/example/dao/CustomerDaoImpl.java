package org.example.dao;

import org.example.entity.Customer;
import org.example.util.DBConnectionUtil;

import java.sql.*;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao {
    Scanner sc = new Scanner(System.in);

    @Override
    public Customer authorizeUser(String email, String pwd) {
        Customer customer = null;
        try {
            Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select * from customers");
            ResultSet customers = ps.executeQuery();

            while(customers.next()) {
                if(email.equals(customers.getString("email"))
                        && pwd.equals(customers.getString("password"))) {
                    int id = customers.getInt("customer_id");
                    String name = customers.getString("name");
                    customer = new Customer(name, id, email);
                }
            }

            ps.close();
            customers.close();

            return customer;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addCustomer(String name, String email, String phone, String pwd) {
        try {
            Connection conn = DBConnectionUtil.getConnection();
            String sql = "insert into customers(name, email, phone, password) values (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, pwd);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // TODO: Delete customer functionality
    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }

    // TODO: Update customer functionality
    @Override
    public boolean updateCustomer(String id, Customer customer) {
        return false;
    }

    // TODO: Fetch customer functionality
    @Override
    public Customer fetchCustomer(String id) {
        return null;
    }
}
