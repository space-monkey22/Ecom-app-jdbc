package org.example.services;

import org.example.dao.CartDao;
import org.example.dao.CartDaoImpl;
import org.example.entity.Cart;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartServices {
    CartDao cartDao = new CartDaoImpl();

    // TODO: Add product to Cart function
    public boolean addToCart(Product product, Customer customer) {
        Cart cart = cartDao.fetchCart(customer);

        if(cart == null) {
            cartDao.createCart(customer);
            cart = cartDao.fetchCart(customer);
        }

        if(cartDao.addProductToCart(cart.getCartId(), product)) {
            System.out.println("Product added to Cart");
        }
        return false;
    }
}
