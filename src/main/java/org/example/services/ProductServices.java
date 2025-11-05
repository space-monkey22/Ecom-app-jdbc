package org.example.services;
import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.entity.Customer;
import org.example.entity.Product;
import java.util.Scanner;

public class ProductServices {

    ProductDao productDao = new ProductDaoImpl();
    CartServices cartServices = new CartServices();

    Scanner sc = new Scanner(System.in);

    public Product[] searchByName(String name) {
        String lowerName = name.toLowerCase();
        Product[] products = productDao.fetchProducts("name", lowerName, "search");
        return products;
    }

    public Product[] searchByCategory(String category) {
        String lowerCategory = category.toLowerCase();
        Product[] products = productDao.fetchProducts("category", lowerCategory, "search");
        return products;
    }

    public Product[] browseProducts() {
        Product[] products = productDao.fetchProducts("", "", "peek");
        return products;
    }

    public void listProducts(Product[] products, String mode, Customer customer) {

        if(products.length == 0) {
            System.out.println("\n------No results were found------");
            return;
        }

        System.out.println("\n\u001B[36m------" + products.length + " result(s) were found------\u001B[0m");
        while(true) {
            System.out.println();
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%-3d %-25s | %6.2f%n",
                        (i + 1),
                        products[i].getName(),
                        products[i].getPrice()
                );
            }
             
            if(mode.equals("customer")) {
              System.out.print("\u001B[1m100 Back\u001B[0m\n\nSelect product: ");
              int n = sc.nextInt();

              if(n == 100) return;

              productDetails(products[n - 1], customer);
            }
            else return;
        }
    }

    public void productDetails(Product product, Customer customer) {
        System.out.println("------------------------");
        System.out.println("Product id: " + product.getProduct_id());
        System.out.println("Name: " + product.getName());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Description: " + product.getDesc());
        System.out.println("------------------------");

        if(product.getQuantity() < 5) {
            System.out.println("\u001B[31m Only " + product.getQuantity() + " left in stock!\u001B[0m");
        }

        System.out.println("\n1. Add to Cart\n2. Back");
        int n = sc.nextInt();

        switch (n) {
            case 1:
                cartServices.addToCart(product, customer);
                break;
            case 2:
                return;
        }
    }
    public void initProduct(String name, double price, String desc, int quantity, String category) {
        Product p = new Product();
        p.setPname(name);
        p.setPrice(price);
        p.setDesc(desc);
        p.setQuantity(quantity);
        p.setCategory(category);
        boolean status=productDao.addProduct(p);
        System.out.println("\n adding....\n");
        System.out.println(status ? "Product added successfully!" : " Product failed to be added :(");

    }
    public void deleteProduct(int id){
        boolean status=productDao.deleteProduct(id);
        System.out.println("\ndeleting....\n");
        System.out.println(status ? "Product has been deleted" : " Product failed to be deleted");
    }
}
