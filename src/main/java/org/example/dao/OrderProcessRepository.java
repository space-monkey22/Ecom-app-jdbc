package org.example.dao;

import org.example.entity.Customer;
import org.example.entity.Product;

import java.util.Map;

public interface OrderProcessRepository {
    void placeOrder(Customer customer, Map<Product, Integer> orderItems, String shippingAddress);
}
