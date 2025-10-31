package org.example.dao;
import org.example.entity.Product;

import java.sql.SQLException;

public interface ProductDao {
   public boolean addProduct(Product p);
   public boolean deleteProduct(long product_id);

   // Method to return products according to value
   public Product[] fetchProducts(String col, String term);

}
