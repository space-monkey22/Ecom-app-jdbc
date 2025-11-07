package org.example;
import org.example.entity.Customer;
import org.example.entity.Product;
import org.example.services.CartServices;
import org.example.services.CustomerServices;
import org.example.services.OrderServices;
import org.example.services.ProductServices;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Main {

    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // Service objects creation
    static CustomerServices customerService = new CustomerServices();
    static ProductServices productServices = new ProductServices();
    static CartServices cartService = new CartServices();
    static OrderServices orderservices= new OrderServices();

    public static void main(String[] args) throws IOException {
        System.out.println("--------------------welcome to zmazona---------------------");

        Customer customer = null;
        String userType = null;

        // The menu
        while(true) {
            System.out.println("""
                    \u001B[1mLogin as:\u001B[0m
                    1. Customer
                    2. Admin
                    3. Exit
                    """);

            int user = sc.nextInt();
            switch (user) {
                case 1:
                    while(true) {
                        System.out.println("1. Login\n2. Register");
                        int n = sc.nextInt();

                        if (n == 1) {
                            customer = customerService.loginCustomer();
                            if (customer == null) {
                                System.out.println("\u001B[31mWrong password or email. Please try again.\u001B[0m");
                                continue;
                            }
                        } else if (n == 2) {
                            customerService.registerCustomer();
                            customer = customerService.loginCustomer();
                        }
                        userType = "customer";
                        break;
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.print("Enter password: ");
                        String pwd = sc.next();

                        if (pwd.equals("admin123")) {
                            System.out.println("\nAdmin Login Successful!\n");
                            userType = "admin";
                            break;
                        } else {
                            System.out.println("Wrong password, try again.");
                        }
                    }
                    break;

                case 3:
                    return;
            }

            if (userType.equals("customer")) {
                customerInterface(customer);
            } else if (userType.equals("admin")) {
                adminInterface();
            }
        }
    }

    static void customerInterface(Customer customer) throws IOException {
        System.out.println("\n\u001B[36m------Welcome " + customer.getCustomer_name() + "!------\u001B[0m" );
        while(true) {
            System.out.println(
                    """
                    
                    1. Browse Products
                    2. Search by name
                    3. Search by category
                    4. View Cart
                    5. Back
                    """);
            int n = sc.nextInt();

            // TODO: View Cart Case for Customer
            switch (n) {
                case 1: {
                    Product[] products = productServices.browseProducts();
                    productServices.listProducts(products,"customer", customer);
                    break;
                }
                case 2: {
                    System.out.print("Search: ");
                    String term = br.readLine();
                    Product[] products = productServices.searchByName(term);
                    productServices.listProducts(products,"customer", customer);
                    break;
                }
                case 3: {
                    System.out.print("Search: ");
                    String term = br.readLine();
                    Product[] products = productServices.searchByCategory(term);

                    productServices.listProducts(products, "customer",customer);
                    break;
                }
                case 4: {
                    cartService.viewCart(customer);
                    break;
                }
                case 5: {
                    return;
                }
            }
        }
    }

    // TODO: Admin Interface implementation
    public static void adminInterface() throws IOException {
        System.out.println("---------- ADMIN PANEL ----------");

        while (true) {
            System.out.println("""
                
                1. Add Product
                2. Delete Product 
                3. Update Product
                4. View Customer Orders
                5. Back
                """);

            System.out.print("Select an option: ");
            int n = sc.nextInt();
            sc.nextLine();
            switch (n) {
                case 1 :
                {
                    System.out.println("\n--- Add New Product ---");

                    System.out.print("Product name: ");
                    String name = br.readLine();

                    System.out.print("Product price: ");
                    double price = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Product description: ");
                    String desc = br.readLine();

                    System.out.print("Product quantity: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Product category: ");
                    String category = br.readLine();
                    productServices.initProduct(name,price,desc,quantity,category);
                    break;
                }
                case 2:
                {
                    productServices.deleteProduct();
                    break;
                }
                case 3:
                {
                    productServices.updateProduct();
                    break;
                }
                case 4:
                {
                    System.out.println("Enter customer id: ");
                    int id=sc.nextInt();
                    orderservices.customerorder(id);
                    break;
                }

                case 5:
                    return;

            }

        }
    }
}