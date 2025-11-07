package dao;

import org.example.dao.CustomerDaoImpl;
import org.example.entity.Customer;
import org.example.util.TestDBConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerDaoImplTest {

    private CustomerDaoImpl customerDao;
    private Connection conn;

    @BeforeAll
    void setupDatabase() throws Exception {
        conn = TestDBConnectionUtil.getConnection();
        customerDao = new CustomerDaoImpl(conn);

        Statement stmt = conn.createStatement();
        stmt.execute("""
            CREATE TABLE customers (
                customer_id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50),
                email VARCHAR(50),
                phone VARCHAR(20),
                password VARCHAR(50)
            )
        """);
        stmt.close();
    }

    @AfterAll
    void tearDown() throws Exception {
        conn.createStatement().execute("DROP ALL OBJECTS");
        conn.close();
    }

    @Test
    void testAddCustomer() {
        boolean result = customerDao.addCustomer("Alice", "alice@mail.com", "1234567890", "pass123");
        assertTrue(result, "Customer should be added successfully");
    }

    @Test
    void testAuthorizeUser() {
        customerDao.addCustomer("Bob", "bob@mail.com", "9876543210", "bobpass");

        Customer customer = customerDao.authorizeUser("bob@mail.com", "bobpass");
        assertNotNull(customer, "Customer should be authorized");
        assertEquals("Bob", customer.getCustomerName());
    }

    @Test
    void testFetchCustomer() {
        customerDao.addCustomer("Charlie", "charlie@mail.com", "1122334455", "charpass");

        Customer fetched = customerDao.fetchCustomer(2); // Assuming auto-increment IDs
        assertNotNull(fetched);
        assertEquals("Charlie", fetched.getCustomerName());
    }

    @Test
    void testDeleteCustomer() {
        customerDao.addCustomer("David", "david@mail.com", "9988776655", "david123");

        boolean deleted = customerDao.deleteCustomer(4);
        assertTrue(deleted, "Customer should be deleted successfully");
    }
}

