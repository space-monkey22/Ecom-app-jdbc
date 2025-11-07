package org.example.dao;

import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.*;
import java.util.Map;

public class OrderProcessRepositoryImpl implements OrderProcessRepository {

    private final Connection conn;

    public OrderProcessRepositoryImpl() {
        try {
            this.conn = DBConnectionUtil.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public OrderProcessRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void placeOrder(Customer customer, Map<Product, Integer> orderItems, String shippingAddress) {
        try {
            // Create Order
            String sql1 = "INSERT INTO orders (customer_id, amount, shipping_address) VALUES (?, ?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);

            ps1.setInt(1, customer.getCustomerId());
            ps1.setDouble(2, calculateTotal(orderItems));
            ps1.setString(3, shippingAddress);

            ps1.execute();

            // Get OrderId
            ResultSet rs = ps1.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            rs.close();
            ps1.close();

            // Insert order items into order_items table
            for(Product p: orderItems.keySet()) {
                String sql2 = "INSERT INTO order_items (order_id, product_id, quantity, price) values (?, ?, ?, ?)";
                PreparedStatement ps2 = conn.prepareStatement(sql2);

                ps2.setInt(1, orderId);
                ps2.setInt(2, p.getProduct_id());
                ps2.setInt(3, orderItems.get(p));
                ps2.setDouble(4, p.getPrice() * orderItems.get(p));

                ps2.execute();
                ps2.close();
            }

            System.out.println("\n\u001B[36mYour order has been placed, thanks for shopping!\u001B[0m");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double calculateTotal(Map<Product, Integer> orderItems) {
        double total = 0;
        for(Product p: orderItems.keySet()) {
            total += p.getPrice() * orderItems.get(p);
        }
        return total;
    }

    public List<Map<Product,Integer>> getOrderbyID(int id) {
        List<Map<Product, Integer>> orderList = new ArrayList<>();

        String sql = """
                    SELECT p.product_id, p.name, p.price, p.description, p.stock_quantity, p.category,
                           oi.quantity
                    FROM orders o
                    JOIN order_items oi ON o.order_id = oi.order_id
                    JOIN products p ON oi.product_id = p.product_id
                    WHERE o.customer_id = ?
                    ORDER BY o.order_id;
                """;

        try
        {
            Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description"),
                        rs.getInt("stock_quantity"),
                        rs.getString("category")
                );

                int quantity = rs.getInt("quantity");

                Map<Product, Integer> productEntry = new HashMap<>();
                productEntry.put(product, quantity);

                orderList.add(productEntry);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderList;
    }

}
