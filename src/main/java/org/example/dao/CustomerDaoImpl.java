package org.example.dao;

import org.example.entity.Customer;
import org.example.util.DBConnectionUtil;

import java.sql.*;
import java.util.Scanner;

public class CustomerDaoImpl implements CustomerDao {
    Scanner sc = new Scanner(System.in);

    @Override
    public Customer loginCustomer() {
        System.out.println("------Login Customer------\n");
        System.out.print("Enter email: ");
        String email = sc.next();

        System.out.println("Enter password: ");
        String pwd = sc.next();

        try {
            Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select * from customers");
            ResultSet customers = ps.executeQuery();

            while(customers.next()) {
                if(email.equals(customers.getString("email"))
                                    && pwd.equals(customers.getString("password"))) {
                    long id = customers.getInt("customer_id");
                    String name = customers.getString("name");
                    System.out.println("\nCustomer Login Successful!\n");
                    return new Customer(name, id, email);
                }
            }

            conn.close();
            customers.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public boolean registerCustomer() {
        System.out.println("------Register Customer------\n");
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.next();

        System.out.print("Phone: ");
        String phone = sc.next();

        System.out.print("Password: ");
        String pwd = sc.next();

        while(true) {
            System.out.print("Re-enter password: ");
            String rePWD = sc.next();

            if(pwd.equals(rePWD)) {
                System.out.println("Passwords match!");
                break;
            }
            else {
                System.out.println("Passwords don't match, try again.");
            }
        }

        try {
            Connection conn = DBConnectionUtil.getConnection();
            String sql = "insert into customers(name, email, phone, password) values (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, pwd);

            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
