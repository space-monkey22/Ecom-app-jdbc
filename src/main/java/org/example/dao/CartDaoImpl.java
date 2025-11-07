package org.example.dao;

import org.example.entity.Cart;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements CartDao {

    private final Connection conn;

    public CartDaoImpl() {
        try {
            this.conn = DBConnectionUtil.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CartDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean createCart(Customer customer) {
        try {
            String sql = "INSERT INTO carts (customer_id) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, customer.getCustomerId());

            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCart(int cartId) {
        try {
            String sql = "DELETE FROM carts WHERE cart_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, cartId);

            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cart fetchCart(Customer customer) {
        Cart cart = null;
        try {

            String sql = "SELECT * FROM carts WHERE customer_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customer.getCustomerId());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                cart = new Cart(rs.getInt("cart_id"),
                        rs.getInt("customer_id"));
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public Product[] fetchCartProducts(int cartId) {
        List<Product> productList = new ArrayList<>();
        try {
            String sql = """
                SELECT p.product_id, p.name, p.price, p.stock_quantity, p.category, p.description, ci.quantity
                FROM cart_items ci
                INNER JOIN products p ON ci.product_id = p.product_id
                WHERE ci.cart_id = ?
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                productList.add(product);
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList.toArray(new Product[0]);
    }

    @Override
    public boolean addProductToCart(int cartId, Product product) {
        try {
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

                ps2.setInt(1, cartId);
                ps2.setInt(2, product.getProduct_id());
                ps2.setDouble(3, product.getPrice());

                ps2.execute();
                ps2.close();
            }
            rs.close();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteProductFromCart(int cartId, Product product) {
        try {
            String sql = "DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, cartId);
            ps.setInt(2, product.getProduct_id());

            ps.execute();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
