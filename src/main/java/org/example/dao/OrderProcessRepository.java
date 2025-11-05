package org.example.dao;

import org.example.entity.Customer;
import org.example.entity.Order;
import org.example.entity.Product;

import java.util.List;
import java.util.Map;

public interface OrderProcessRepository {

    public void placeOrder(Customer customer, Map<Product, Integer> orderItems, String shippingAddress);

}
