package org.example.services;

import org.example.dao.CustomerDao;
import org.example.dao.CustomerDaoImpl;
import org.example.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerServices {
    CustomerDao customerDao = new CustomerDaoImpl();
    Scanner sc = new Scanner(System.in);

    public void registerCustomer() {
        System.out.println("\u001B[34m------Sign up------\u001B[0m\n");
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
                System.out.println("\u001B[31mPasswords don't match, try again.\u001B[0m");
            }
        }
        if(customerDao.addCustomer(name, email, phone, pwd)) {
            System.out.println("Registration Success!");
        }
    }
}
