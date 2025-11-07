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
            System.out.println("\n\u001B[33mYour cart is empty, shop more!\u001B[0m");
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

        // Options
        System.out.println("\u001B[1m1. Place Order\u001B[0m\n2. Back\nPress item number followed by d (2d) to remove item from cart");
        String n = br.readLine();

        if(n.equals("1")) {
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
        } else if(n.equals("2")) {
            return;
        } else if(n.endsWith("d")){
            try {
                int index = Integer.parseInt(n.substring(0, n.length() - 1)) - 1;

                if (index >= 0 && index < products.length) {
                    Product toRemove = products[index];

                    boolean status = cartDao.deleteProductFromCart(cart.getCartId(), toRemove);
                    System.out.println(status ? "\n\u001B[33m" + toRemove.getName() + " removed from cart!\u001B[0m" :
                            "\u001B[31mFailed to remove item from cart\u001B[0m");
                } else {
                    System.out.println("\n\u001B[31mInvalid item number!\u001B[0m[31mInvalid item number!\u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n\u001B[31mInvalid input format! Use like 2d to delete item 2.\u001B[0m");
            }
        }
    }

    public void clearCart(Customer customer) {

        Cart cart = cartDao.fetchCart(customer);
        Product[] products = cartDao.fetchCartProducts(cart.getCartId());

        // Delete cart items from cart_items table
        for(Product p: products) {
            cartDao.deleteProductFromCart(cart.getCartId(), p);
        }
        // Delete cart from carts table
        cartDao.deleteCart(cart.getCartId());
    }
}
