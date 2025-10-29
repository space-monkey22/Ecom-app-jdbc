package org.example.dao;
import org.example.entity.Product;

public interface ProductDao {
   public boolean addProduct(Product p);
   public boolean deleteProduct(long product_id);
}
