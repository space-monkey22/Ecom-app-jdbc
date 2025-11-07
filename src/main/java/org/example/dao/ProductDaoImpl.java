package org.example.dao;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDaoImpl implements ProductDao{

    private final Connection conn;

    public ProductDaoImpl() {
        try {
            this.conn = DBConnectionUtil.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public boolean addProduct(Product p)  {
        try{
            String sql = "INSERT INTO products (name, price, stock_quantity, category, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, p.getName());
            stmt.setDouble(2, p.getPrice());
            stmt.setInt(3, p.getQuantity());
            stmt.setString(4, p.getCategory());
            stmt.setString(5,p.getDesc());

            stmt.execute();
            stmt.close();

            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteProduct(int product_id) {
        try
        {
            String query = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, product_id);
            int rows = statement.executeUpdate();

            return rows > 0;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    // TODO: update product functionality
    @Override
    public boolean updateProduct(int productId, Product product) {
        return false;
    }

    @Override
    public Product[] fetchProducts(String col, String term, String type) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement stmt = null;

            if(type.equals("search")) {
                String query = "SELECT * FROM products WHERE " + col + " LIKE ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, "%" + term + "%");
            }
            else if(type.equals("peek")) {
                String query = "SELECT * FROM products LIMIT 10";
                stmt = conn.prepareStatement(query);
            }

            assert stmt != null;
            ResultSet ps = stmt.executeQuery();

            while(ps.next()) {
                products.add(new Product(
                        ps.getInt("product_id"),
                        ps.getString("name"),
                        ps.getDouble("price"),
                        ps.getString("description"),
                        ps.getInt("stock_quantity"),
                        ps.getString("category")));
            }

            ps.close();
            stmt.close();

            Product[] productArray = new Product[products.size()];
            for(int i = 0; i < productArray.length; i++) {
                productArray[i] = products.get(i);
            }
            return productArray;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
