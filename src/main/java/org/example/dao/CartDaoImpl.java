package org.example.dao;

import org.example.entity.Cart;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDaoImpl implements CartDao {

    @Override
    public boolean createCart(Customer customer) {
        try {
            Connection conn = DBConnectionUtil.getConnection();
            String sql = "INSERT INTO carts (customer_id) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, customer.getCustomerId());

            ps.execute();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // TODO: Delete cart function
    @Override
    public boolean deleteCart(int cartId) {
        return false;
    }

    // TODO: Fetch cart function
    @Override
    public Cart fetchCart(Customer customer) {
        Cart cart = null;
        try {
            Connection conn = DBConnectionUtil.getConnection();

            String sql = "SELECT * FROM carts WHERE customer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                cart = new Cart(rs.getInt("cart_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("total_amount"),
                        rs.getBoolean("purchased"));
                System.out.println("Cart created for user " + customer.getCustomer_name()); // testing
            }
            rs.close();
            ps.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public boolean addProductToCart(int cartId, Product product) {
        try {
            Connection conn =  DBConnectionUtil.getConnection();
            String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND product_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,cartId);
            ps.setInt(2, product.getProduct_id());
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String sql1 = "UPDATE cart_items SET quantity = quantity + 1, price = price + ? WHERE cart_id = ? AND product_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sql1);
                ps1.setDouble(1, product.getPrice());
                ps1.setInt(2,cartId);
                ps1.setInt(3, product.getProduct_id());

                ps1.execute();
                ps1.close();
            }
            else {
                String sql2 = "INSERT INTO cart_items (cart_id, product_id, quantity, price) VALUES (?, ?, 1, ?)";
                PreparedStatement ps2 = conn.prepareStatement(sql2);

                System.out.println("\nAdding item..");
                System.out.println("Name: " + product.getPname());
                System.out.println("Id: " + product.getProduct_id());

                ps2.setInt(1, cartId);
                ps2.setInt(2, product.getProduct_id());
                ps2.setDouble(3, product.getPrice());

                ps2.execute();
                ps2.close();
                System.out.println("Product " + product.getPname() +" added to cart " + cartId); // testing
            }
            rs.close();
            ps.close();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeProductFromCart(int cartId, Product product) {
        return false;
    }
}
