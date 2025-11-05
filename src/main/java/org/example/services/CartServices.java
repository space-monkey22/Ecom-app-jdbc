package org.example.services;

import org.example.dao.CartDao;
import org.example.dao.CartDaoImpl;
import org.example.dao.OrderProcessRepository;
import org.example.dao.OrderProcessRepositoryImpl;
import org.example.entity.Cart;
import org.example.entity.Customer;
import org.example.entity.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.*;

public class CartServices {
    CartDao cartDao = new CartDaoImpl();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    OrderProcessRepository orderProcessRepository = new OrderProcessRepositoryImpl();

    public boolean addToCart(Product product, Customer customer) {
        Cart cart = cartDao.fetchCart(customer);

        if(cart == null) {
            cartDao.createCart(customer);
            cart = cartDao.fetchCart(customer);
        }

        if(cartDao.addProductToCart(cart.getCartId(), product)) {
            System.out.println("\u001B[33m" + product.getName() + " added to cart!\u001B[0m");
        }
        return false;
    }

    public void viewCart(Customer customer) throws IOException {
        Cart cart = cartDao.fetchCart(customer);
        if(cart == null) {
            System.out.println("\u001B[33mYour cart is empty, shop more!\u001B[0m");
            return;
        }
        Product[] products = cartDao.fetchCartProducts(cart.getCartId());
        double total = 0;

        System.out.println("-----------------------------------------------");
        System.out.printf("%-5s %-20s %-10s %-10s%n", "No.", "Name", "Qty", "Price");
        System.out.println("-----------------------------------------------");

        for (int i = 0; i < products.length; i++) {
            Product p = products[i];
            double price = p.getPrice() * p.getQuantity();
            System.out.printf("%-5d %-20s %-10d %-10.2f%n", (i + 1), p.getName(), p.getQuantity(), price);
            total += price;
        }

        System.out.println("-----------------------------------------------");
        System.out.printf("\u001B[1m%-5s %-20s %-10s %-10.2f%n\u001B[0m", "", "Total", "", total);
        System.out.println("-----------------------------------------------");

        System.out.println("\u001B[1m1. Place Order\u001B[0m");
        int n = Integer.parseInt(br.readLine());
        System.out.println(n + " Customer shipping address: " + customer.getShippingAddress()); // testing
        if(n == 1) {
            if(customer.getShippingAddress() == null) {
                System.out.println("Shipping Address: ");
                String address = br.readLine();
                customer.setShippingAddress(address);
            }

            Map<Product, Integer> map = new HashMap<>();
            for(Product p: products) {
                map.put(p, p.getQuantity());
            }
            orderProcessRepository.placeOrder(customer, map, customer.getShippingAddress());
            clearCart(customer);
        }
    }

    public void clearCart(Customer customer) {
        // Delete cart items from cart_items table
        Cart cart = cartDao.fetchCart(customer);
        Product[] products = cartDao.fetchCartProducts(cart.getCartId());

        for(Product p: products) {
            cartDao.deleteProductFromCart(cart.getCartId(), p);
        }

        cartDao.deleteCart(cart.getCartId());
    }
}
