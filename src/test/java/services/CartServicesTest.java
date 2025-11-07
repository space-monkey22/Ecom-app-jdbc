package services;

import org.example.dao.*;
import org.example.entity.*;
import org.example.services.CartServices;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.io.*;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CartServicesTest {

    @Mock
    private CartDao cartDao;

    @Mock
    private OrderProcessRepository orderRepo;

    @InjectMocks
    private CartServices cartServices;

    private Customer customer;
    private Product product;
    private Cart cart;
    private ByteArrayInputStream input;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer("Alice", 1, "alice@mail.com");
        product = new Product(1, "Phone", 50000, "Smartphone", 2, "Electronics");
        cart = new Cart(10, customer.getCustomerId());
    }

    @Test
    void testAddToCart_CreatesNewCartAndAddsProduct() {
        when(cartDao.fetchCart(customer)).thenReturn(null).thenReturn(cart);
        when(cartDao.addProductToCart(cart.getCartId(), product)).thenReturn(true);

        boolean result = cartServices.addToCart(product, customer);

        verify(cartDao).createCart(customer);
        verify(cartDao, times(2)).fetchCart(customer);
        verify(cartDao).addProductToCart(cart.getCartId(), product);
        assertFalse(result); // since addToCart always returns false
    }

    @Test
    void testClearCart_DeletesAllProductsAndCart() {
        Product[] products = { product };
        when(cartDao.fetchCart(customer)).thenReturn(cart);
        when(cartDao.fetchCartProducts(cart.getCartId())).thenReturn(products);

        cartServices.clearCart(customer);

        verify(cartDao).deleteProductFromCart(cart.getCartId(), product);
        verify(cartDao).deleteCart(cart.getCartId());
    }

    @Test
    void testViewCart_PlaceOrderOption() throws IOException {
        Product[] products = { product };
        when(cartDao.fetchCart(customer)).thenReturn(cart);
        when(cartDao.fetchCartProducts(cart.getCartId())).thenReturn(products);

        // Simulate input for "1" (place order) and shipping address
        String userInput = "1\n123 Main Street\n";
        input = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(input);

        CartServices service = new CartServices();
//        service.cartDao = cartDao;
//        service.orderProcessRepository = orderRepo;
//        service.br = new BufferedReader(new InputStreamReader(System.in));

        service.viewCart(customer);

        verify(orderRepo).placeOrder(eq(customer), anyMap(), eq("123 Main Street"));
    }

    @AfterEach
    void tearDown() {
        System.setIn(System.in);
    }
}

