package org.example.services;
import org.example.dao.*;
import org.example.entity.Cart;
import org.example.entity.Customer;
import org.example.entity.Product;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class OrderServices {
    ProductDao productDao = new ProductDaoImpl();
    CartServices cartServices = new CartServices();
    OrderProcessRepository orderprocess= new OrderProcessRepositoryImpl();
    Scanner sc = new Scanner(System.in);

    public void customerorder(int id)
    {
        List<Map<Product, Integer>> items = orderprocess.getOrderbyID(id);

        if (items == null || items.isEmpty()) {
            System.out.println("\n\u001B[31mNo items found \u001B[0m");
            return;
        }


        double grandTotal = 0;

        System.out.println("\n\u001B[36m============= Order Summary =============\u001B[0m\n");
        System.out.printf("\u001B[1m%-25s %-8s %-10s %-10s\u001B[0m%n", "Product", "Qty", "Price", "Total");
        System.out.println("---------------------------------------------------------------");

        for (Map<Product, Integer> entry : items) {
            for (Product p : entry.keySet()) {

                int qty = entry.get(p);
                double price = p.getPrice();
                double total = qty * price;
                grandTotal += total;

                System.out.printf("%-25s %-8d %-10.2f %-10.2f%n",
                        p.getName(), qty, price, total);
            }
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println("\n");



    }
}
