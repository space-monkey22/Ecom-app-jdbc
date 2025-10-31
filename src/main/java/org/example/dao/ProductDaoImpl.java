package org.example.dao;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDaoImpl implements ProductDao{

    public boolean addProduct(Product p)  {

        try{
            Connection connection = DBConnectionUtil.getConnection();
            String sql = "INSERT INTO products (name, price, stock_quantity, category, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, p.getPname());
            statement.setDouble(2, p.getPrice());
            statement.setInt(3, p.getQuantity());
            statement.setString(4, p.getCategory());
            statement.setString(5,p.getDesc());

            statement.execute();
            connection.close();
            return true;
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }

    }

    public boolean deleteProduct(long product_id) {
        return false;
    }

    @Override
    public Product[] fetchProducts(String col, String term) {
        List<Product> products = new ArrayList<>();

        try {
            Connection conn = DBConnectionUtil.getConnection();
            String query = "SELECT * FROM products WHERE " + col + " LIKE ?";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, "%" + term + "%");

            ResultSet ps = statement.executeQuery();

            while(ps.next()) {

                products.add(new Product(ps.getString("name"),
                        ps.getDouble("price"),
                        ps.getString("description"),
                        ps.getInt("stock_quantity"),
                        ps.getString("category")));
            }

            conn.close();
            statement.close();
            ps.close();

            Product[] productArray = new Product[products.size()];
            for(int i = 0; i < productArray.length; i++) {
                productArray[i] = products.get(i);
            }
            return productArray;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
