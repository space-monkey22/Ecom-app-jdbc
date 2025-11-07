package dao;

import org.example.entity.*;
import org.example.dao.CartDaoImpl;
import org.junit.jupiter.api.*;
import org.example.util.TestDBConnectionUtil;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartDaoImplTest {

    private CartDaoImpl cartDao;
    private Connection conn;

    @BeforeAll
    void setupDatabase() throws Exception {
        conn = TestDBConnectionUtil.getConnection();
        cartDao = new CartDaoImpl(conn);

        // Create mock tables in H2
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE customers (customer_id INT PRIMARY KEY, name VARCHAR(50))");
        stmt.execute("CREATE TABLE carts (cart_id INT AUTO_INCREMENT PRIMARY KEY, customer_id INT, total_amount INT DEFAULT 0, purchased BOOLEAN DEFAULT FALSE)");
        stmt.execute("CREATE TABLE products (product_id INT PRIMARY KEY, name VARCHAR(50), price DOUBLE, stock_quantity INT, category VARCHAR(50), description VARCHAR(100))");
        stmt.execute("CREATE TABLE cart_items (cart_id INT, product_id INT, quantity INT, price DOUBLE)");
        stmt.execute("INSERT INTO customers VALUES (1, 'TestUser')");
        stmt.execute("INSERT INTO products VALUES (101, 'Phone', 999.99, 10, 'Electronics', 'Smartphone')");
        stmt.close();
    }

    @AfterAll
    void tearDown() throws Exception {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @Test
    void testCreateCart() {
        Customer customer = new Customer();
        customer.setCustomerId(1);

        boolean result = cartDao.createCart(customer);
        assertTrue(result, "Cart should be created successfully");
    }

    @Test
    void testFetchCart() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        cartDao.createCart(customer);

        Cart cart = cartDao.fetchCart(customer);
        assertNotNull(cart, "Cart should not be null");
        assertEquals(1, cart.getCustomerId());
    }

    @Test
    void testAddProductToCart() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        cartDao.createCart(customer);

        Cart cart = cartDao.fetchCart(customer);
        Product product = new Product(101, "Phone", 999.99, "Smartphone", 1, "Electronics");

        boolean added = cartDao.addProductToCart(cart.getCartId(), product);
        assertTrue(added, "Product should be added to cart");
    }

    @Test
    void testDeleteCart() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        cartDao.createCart(customer);
        Cart cart = cartDao.fetchCart(customer);

        boolean deleted = cartDao.deleteCart(cart.getCartId());
        assertTrue(deleted, "Cart should be deleted successfully");
    }
}

