package org.example.dao;
import org.example.entity.Product;
import org.example.util.DBConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ProductDaoImpl implements ProductDao{
    public boolean addProduct(Product p)  {

        try{
            Connection connection = DBConnectionUtil.getConnection();
            String sql = "INSERT INTO products (name, price, stock_quantity,category,description) VALUES (?, ?, ?,?,?)";
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
}
