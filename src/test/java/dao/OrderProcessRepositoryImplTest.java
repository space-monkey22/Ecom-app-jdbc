package dao;

import org.example.dao.OrderProcessRepositoryImpl;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderProcessRepositoryImplTest {

    private Connection conn;
    private OrderProcessRepositoryImpl orderRepo;

    @BeforeAll
    void setupDatabase() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        Statement stmt = conn.createStatement();

        stmt.execute("""
            CREATE TABLE customers (
                customer_id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100),
                email VARCHAR(100)
            )
        """);

        stmt.execute("""
            CREATE TABLE products (
                product_id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100),
                price DOUBLE
            )
        """);

        stmt.execute("""
            CREATE TABLE orders (
                order_id INT AUTO_INCREMENT PRIMARY KEY,
                customer_id INT,
                amount DOUBLE,
                shipping_address VARCHAR(255)
            )
        """);

        stmt.execute("""
            CREATE TABLE order_items (
                item_id INT AUTO_INCREMENT PRIMARY KEY,
                order_id INT,
                product_id INT,
                quantity INT,
                price DOUBLE
            )
        """);

        stmt.close();
    }

    @AfterAll
    void teardown() throws SQLException {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @BeforeEach
    void setup() {
        orderRepo = new OrderProcessRepositoryImpl(conn);
    }

    @Test
    void testPlaceOrder() throws SQLException {
        // Insert customer
        PreparedStatement ps1 = conn.prepareStatement(
                "INSERT INTO customers (name, email) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps1.setString(1, "Alice");
        ps1.setString(2, "alice@mail.com");
        ps1.executeUpdate();
        ResultSet rs1 = ps1.getGeneratedKeys();
        rs1.next();
        int customerId = rs1.getInt(1);
        rs1.close(); ps1.close();

        Customer customer = new Customer("Alice", customerId, "alice@mail.com");

        // Insert products
        PreparedStatement ps2 = conn.prepareStatement(
                "INSERT INTO products (name, price) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ps2.setString(1, "Phone");
        ps2.setDouble(2, 50000);
        ps2.executeUpdate();
        ResultSet rs2 = ps2.getGeneratedKeys();
        rs2.next();
        int productId = rs2.getInt(1);
        rs2.close(); ps2.close();

        Product product = new Product(productId, "Phone", 50000, "Smartphone", 10, "Electronics");

        // Prepare order items
        Map<Product, Integer> orderItems = new HashMap<>();
        orderItems.put(product, 2);

        // Place order
        orderRepo.placeOrder(customer, orderItems, "123 Main Street");

        // Verify order
        Statement stmt = conn.createStatement();
        ResultSet orderRs = stmt.executeQuery("SELECT * FROM orders");
        assertTrue(orderRs.next(), "Order should be created");
        double expectedAmount = product.getPrice() * 2;
        assertEquals(expectedAmount, orderRs.getDouble("amount"), 0.001);
        orderRs.close();

        // Verify order items
        ResultSet itemsRs = stmt.executeQuery("SELECT * FROM order_items");
        assertTrue(itemsRs.next(), "Order item should be created");
        assertEquals(productId, itemsRs.getInt("product_id"));
        assertEquals(2, itemsRs.getInt("quantity"));
        itemsRs.close();
        stmt.close();
    }

    @Test
    void testCalculateTotal() {
        Product p1 = new Product(1, "Book", 300, "Novel", 5, "Books");
        Product p2 = new Product(2, "Pen", 50, "Blue pen", 10, "Stationery");

        Map<Product, Integer> orderItems = new HashMap<>();
        orderItems.put(p1, 2);
        orderItems.put(p2, 3);

        double total = orderRepo.calculateTotal(orderItems);
        assertEquals(2 * 300 + 3 * 50, total, 0.001);
    }
}

