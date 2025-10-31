package org.example;
import org.example.dao.*;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.services.CustomerServices;
import org.example.services.ProductServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Main {

    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    // Creating objects for implementing classes in dao
    static CustomerDao customerDao = new CustomerDaoImpl();
    static ProductDao productDao = new ProductDaoImpl();
    static OrderProcessRepository orderProcessRepository = new OrderProcessRepositoryImpl();

    static CustomerServices customerService = new CustomerServices();
    static ProductServices productServices = new ProductServices();

    public static void main(String[] args) throws IOException {
        System.out.println("--------------------welcome to zmazoa---------------------");

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
                    customerService.registerCustomer();
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
    }

    static void customerInterface(Customer customer) throws IOException {
        System.out.println("------Welcome " + customer.getCustomer_name() + "!------" );
        while(true) {
            System.out.println(
                    """
                    \n
                    1. Browse Products
                    2. Search by name
                    3. Search by category
                    4. View Cart
                    5. Back
                    """);
            int n = sc.nextInt();

            switch (n) {
                case 1: {
                    Product[] products = productServices.browseProducts();
                    productServices.listProducts(products);
                    break;
                }
                case 2: {
                    System.out.print("Search: ");
                    String term = br.readLine();
                    Product[] products = productServices.searchByName(term);
                    productServices.listProducts(products);
                    break;
                }
                case 3: {
                    System.out.print("Search: ");
                    String term = br.readLine();
                    Product[] products = productServices.searchByCategory(term);

                    productServices.listProducts(products);
                    break;
                }
                case 5: {
                    return;
                }
            }
        }
    }

    public static void adminInterface() {

    }
}