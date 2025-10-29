package org.example.services;

import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductServices {

    ProductDao productDao = new ProductDaoImpl();
    Scanner sc = new Scanner(System.in);

    public Product[] searchByName(String name) {
        try {
            Connection conn = DBConnectionUtil.getConnection();
        } catch(SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
        return new Product[0];
    }

    public Product[] searchByCategory(String category) {

        return new Product[0];
    }

    public Product[] browseProducts() {

        return new Product[0];
    }

    public void listProducts(Product[] products) {
        while(true) {
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i].getPname() + " | " + products[i].getPrice());
            }

            System.out.print("100.Back\n Select product: ");
            int n = sc.nextInt();

            if(n == 100) return;

            productDetails(products[n - 1]);
        }
    }

    public void productDetails(Product product) {
        System.out.println("Product id: " + product.getProduct_id());
        System.out.println("Name: " + product.getPname());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Description: " + product.getDesc());

        if(product.getQuantity() < 5) {
            System.out.println("\u001B[31m Only " + product.getQuantity() + " left in stock!\u001B[0m");
        }

        System.out.println("\n1. Add to Cart\n2. Back");
        int n = sc.nextInt();

        switch (n) {
            case 2:
                return;
        }
    }
}
