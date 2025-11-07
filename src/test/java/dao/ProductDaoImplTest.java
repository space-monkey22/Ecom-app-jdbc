package dao;

import org.example.dao.ProductDaoImpl;
import org.example.entity.Product;
import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDaoImplTest {

    private Connection conn;
    private ProductDaoImpl productDao;

    @BeforeAll
    void setupDatabase() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE products (" +
                "product_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "price DOUBLE, " +
                "stock_quantity INT, " +
                "category VARCHAR(100), " +
                "description VARCHAR(255))");
        stmt.close();
    }

    @AfterAll
    void tearDown() throws Exception {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @BeforeEach
    void setup() {
        productDao = new ProductDaoImpl(conn);
    }

    @Test
    void testAddProduct() {
        Product p = new Product(0, "Laptop", 75000.0, "High-end gaming laptop", 10, "Electronics");
        boolean result = productDao.addProduct(p);
        assertTrue(result, "Product should be added successfully");
    }

    @Test
    void testFetchProducts() {
        productDao.addProduct(new Product(0, "Phone", 50000.0, "Smartphone", 15, "Electronics"));
        Product[] products = productDao.fetchProducts("name", "Phone", "search");
        assertTrue(products.length > 0, "Should fetch at least one prod");
    }

    @Test
    void testDeleteProduct() throws SQLException {
        // Insert a product manually
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO products (name, price, stock_quantity, category, description) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, "Mouse");
        ps.setDouble(2, 1200.0);
        ps.setInt(3, 25);
        ps.setString(4, "Accessories");
        ps.setString(5, "Wireless mouse");
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int productId = rs.getInt(1);
        rs.close();
        ps.close();

        boolean deleted = productDao.deleteProduct(productId);
        assertTrue(deleted, "Product should be deleted successfully");

        // Verify deletion
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT COUNT(*) FROM products WHERE product_id=" + productId);
        result.next();
        assertEquals(0, result.getInt(1), "Product should not exist after deletion");
        stmt.close();
    }
}