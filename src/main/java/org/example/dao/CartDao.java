package org.example.dao;

import org.example.entity.Cart;
import org.example.entity.Customer;
import org.example.entity.Product;

public interface CartDao {
    public boolean createCart(Customer customer);
    public boolean deleteCart(int cartId);
    public Cart fetchCart(Customer customer);
    public Product[] fetchCartProducts(int cartId);
    public boolean addProductToCart(int cartId, Product product);
    public boolean deleteProductFromCart(int cartId, Product product);
}
