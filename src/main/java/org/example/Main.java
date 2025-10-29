package org.example;
import org.example.dao.*;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        System.out.println("--------------------welcome to zmazoa---------------------");
        Scanner sc= new Scanner(System.in);

        // Creating objects for implementing classes in dao
        CustomerDao customerDao = new CustomerDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        OrderProcessRepository orderProcessRepository = new OrderProcessRepositoryImpl();

        Customer customer = null;
        String userType = null;

        // The menu
        System.out.println("""
                Login as:
                1. Customer
                2. Admin
                """);

        int user = sc.nextInt();

        switch(user) {
            case 1:
                System.out.println("1. Login\n2. Register");
                int n = sc.nextInt();

                if(n == 1) {
                    customer = customerDao.loginCustomer();
                }
                else if(n == 2) {
                    customerDao.registerCustomer();
                    customer = customerDao.loginCustomer();
                }
                userType = "customer";
                break;

            case 2:
                while(true) {
                    System.out.print("Enter password: ");
                    String pwd = sc.next();

                    if(pwd.equals("admin123")) {
                        System.out.println("\nAdmin Login Successful!\n");
                        userType = "admin";
                        break;
                    } else {
                        System.out.println("Wrong password, try again.");
                    }
                }
                break;
        }

        if(userType.equals("customer")) {
            customerInterface(customer);
        }
        else if(userType.equals("admin")) {
            adminInterface();
        }

//        System.out.println("""
//                what do you want to do ?
//                1.Register customer
//                2.Add product
//                3.Delete product
//                4.Add to cart
//                5.View cart
//                6.Place order
//                7.View customer order""");
//        String input=sc.next();
//
//        switch(input){
//
//            case "1":
//                System.out.println("enter customer name:");
//                String name=sc.next();
//                System.out.println("enter email id :");
//                String email=sc.next();
//                System.out.println("enter the password:");
//                String password= sc.next();
//                Customer joins= new Customer(name,email,password);
//                //customerdao.addCustomer(joins);
//                break;
//
//            case "2":
//                System.out.println("enter name of the product:");
//                String pname= sc.next();
//                System.out.println("enter the price:");
//                double price= sc.nextDouble();
//                System.out.println("enter description:");
//                String desc= sc.next();
//                System.out.println("enter the stock quantity:");
//                int quantity= sc.nextInt();
//                Product item= new Product(pname,price,desc,quantity);
//                productdao.addProduct(item);
//                break;
//
//            case "3":
//                System.out.println("enter the product_id");
//                long product_id= sc.nextLong();
//                productdao.deleteProduct(product_id);
//                break;
//        }
    }

    public static void customerInterface(Customer customer) {

    }

    public static void adminInterface() {

    }
}