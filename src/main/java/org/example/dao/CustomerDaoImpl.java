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
            String sql = "INSERT INTO customers (name, email, phone, password) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, pwd);

            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(int id) {
        try {
            Connection conn = DBConnectionUtil.getConnection();
            String sql = "DELETE FROM customers WHERE customer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.execute();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // TODO: Update customer functionality
    @Override
    public boolean updateCustomer(int id, Customer customer) {
        return false;
    }

    @Override
    public Customer fetchCustomer(int id) {
        Customer customer = new Customer();
        try {
            Connection conn = DBConnectionUtil.getConnection();
            String sql = "SELECT * FROM customers WHERE customer_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCustomerName(rs.getString("name"));
                customer.setCustomerEmail(rs.getString("email"));
            }

            rs.close();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }
}
