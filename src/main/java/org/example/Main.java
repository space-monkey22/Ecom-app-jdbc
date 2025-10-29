package org.example;
import org.example.dao.*;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.services.CustomerServices;
import org.example.services.ProductServices;

import java.util.Scanner;
public class Main {

    static Scanner sc = new Scanner(System.in);

    // Creating objects for implementing classes in dao
    static CustomerDao customerDao = new CustomerDaoImpl();
    static ProductDao productDao = new ProductDaoImpl();
    static OrderProcessRepository orderProcessRepository = new OrderProcessRepositoryImpl();

    static CustomerServices customerService = new CustomerServices();
    static ProductServices productServices = new ProductServices();

    public static void main(String[] args) {
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

    static void customerInterface(Customer customer) {
        while(true) {
            System.out.println("""
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
                    String term = sc.nextLine();
                    Product[] products = productServices.searchByName(term);
                    productServices.listProducts(products);
                    break;
                }
                case 3: {
                    System.out.println("Search: ");
                    String term = sc.nextLine();
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