package org.example;
import org.example.dao.*;
import org.example.entity.Customer;
import org.example.entity.Product;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("--------------------welcome to zmazoa---------------------");
        Scanner sc= new Scanner(System.in);
        //creating objects for implementing classes in dao
        CustomerDao customerdao= new CustomerDaoImpl();
        ProductDao productdao= new ProductDaoImpl();
        OrderProcessRepository orderprocessrepo= new OrderProcessRepositoryImpl();
        //the menu
        System.out.println("""
                what do you want to do ?
                1. Register customer
                2.Add product
                3.Delete product
                4.Add to cart
                5.View cart
                6.Place order
                7.View customer order""");
        String input=sc.next();

        switch(input){
            case "1":
                System.out.println("enter customer name:");
                String name=sc.next();
                System.out.println("enter email id :");
                String email=sc.next();
                System.out.println("enter the password:");
                String password= sc.next();
                Customer joins= new Customer(name,email,password);
                customerdao.addCustomer(joins);
                break;
            case "2":
                System.out.println("enter name of the product:");
                String pname= sc.next();
                System.out.println("enter the price:");
                double price= sc.nextDouble();
                System.out.println("enter description:");
                String desc= sc.next();
                System.out.println("enter the stock quantity:");
                int quantity= sc.nextInt();
                Product item= new Product(pname,price,desc,quantity);
                productdao.addProduct(item);
                break;
            case "3":
                System.out.println("enter the product_id");
                long product_id= sc.nextLong();
                productdao.deleteProduct(product_id);
                break;


        }

    }
}