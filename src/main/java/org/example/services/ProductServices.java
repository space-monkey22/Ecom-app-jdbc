package org.example.services;

import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductServices {

    ProductDao productDao = new ProductDaoImpl();
    CartServices cartServices = new CartServices();

    Scanner sc = new Scanner(System.in);

    public Product[] searchByName(String name) {
        String lowerName = name.toLowerCase();
        Product[] products = productDao.fetchProducts("name", lowerName, "search");
        return products;
    }

    public Product[] searchByCategory(String category) {
        String lowerCategory = category.toLowerCase();
        Product[] products = productDao.fetchProducts("category", lowerCategory, "search");
        return products;
    }

    public Product[] browseProducts() {
        Product[] products = productDao.fetchProducts("", "", "peek");
        return products;
    }

    public void listProducts(Product[] products, Customer customer) {

        if(products.length == 0) {
            System.out.println("\n------No results were found------");
            System.out.println("1. Back");
            int n = sc.nextInt();
            return;
        }

        System.out.println("\n------" + products.length + " result(s) were found------");
        while(true) {
            System.out.println();
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i].getPname() + " | " + products[i].getPrice());
            }

            System.out.print("100.Back\n\nSelect product: ");
            int n = sc.nextInt();

            if(n == 100) return;

            productDetails(products[n], customer);
        }
    }

    public void productDetails(Product product, Customer customer) {
        System.out.println("------------------------");
        System.out.println("Product id: " + product.getProduct_id());
        System.out.println("Name: " + product.getPname());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Description: " + product.getDesc());
        System.out.println("------------------------");

        if(product.getQuantity() < 5) {
            System.out.println("\u001B[31m Only " + product.getQuantity() + " left in stock!\u001B[0m");
        }

        // TODO: Add to cart functionality for customer
        System.out.println("\n1. Add to Cart\n2. Back");
        int n = sc.nextInt();

        switch (n) {
            case 1:
                cartServices.addToCart(product, customer);
                break;
            case 2:
                return;
        }
    }
}
