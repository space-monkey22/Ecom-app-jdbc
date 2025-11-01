package org.example.dao;
import org.example.entity.Product;

import java.sql.SQLException;

public interface ProductDao {
   public boolean addProduct(Product p);
   public boolean deleteProduct(long productId);
   public boolean updateProduct(long productId, Product product);

   /** Method to return products according to the column that you're searching from
   and the respective value, third argument (type) can have two values: search, peek
    search - returns matched products
    peek - returns first 10 products from the table **/
   public Product[] fetchProducts(String col, String term, String type);

}
