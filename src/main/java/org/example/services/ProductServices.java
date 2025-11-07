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
              System.out.print("\u001B[1m100 Back\u001B[0m\n\nSelect product: ");
              int n = sc.nextInt();

              if(n == 100) return;

              productDetails(products[n - 1], customer);

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
        p.setName(name);
        p.setPrice(price);
        p.setDesc(desc);
        p.setQuantity(quantity);
        p.setCategory(category);
        boolean status=productDao.addProduct(p);
        System.out.println("\n adding....\n");
        System.out.println(status ? "Product added successfully!" : " Product failed to be added :(");

    }
    public void deleteProduct() {

        Product[] products = browseProducts();

        if (products.length == 0) {
            System.out.println("\n------No results were found------");
            return;
        }

        System.out.println("\n\u001B[36m------ Products List ------\u001B[0m");
        System.out.println();

        for (Product p : products) {
            System.out.printf("ID: %-4d %-25s | ₹ %6.2f%n",
                    p.getProduct_id(),
                    p.getName(),
                    p.getPrice()
            );
        }

        System.out.print("\n\u001B[1mEnter Product ID to Delete (100 to cancel): \u001B[0m");
        int id = sc.nextInt();

        if (id == 100) return;
        Product selected = null;
        for (Product p : products) {
            if (p.getProduct_id() == id) {
                selected = p;
                break;
            }
        }

        if (selected == null) {
            System.out.println("\u001B[31mInvalid Product ID.\u001B[0m");
            return;
        }

        System.out.println("\n\u001B[33mAre you sure you want to delete:\u001B[0m "
                + selected.getName() + " ?");
        System.out.println("1. Yes\n2. No");
        int confirm = sc.nextInt();

        if (confirm == 1) {
            boolean status = productDao.deleteProduct(selected.getProduct_id());
            System.out.println(status
                    ? "\u001B[32mProduct deleted successfully!\u001B[0m"
                    : "\u001B[31mDelete failed.\u001B[0m");
        }
    }

    public void updateProduct()
    {
        System.out.println("\n\u001B[36m------ Update Product ------\u001B[0m");

        // Show brief product list
        Product[] products = browseProducts();
        if (products.length == 0) {
            System.out.println("\u001B[31mNo products available.\u001B[0m");
            return;
        }

        System.out.println();
        for (Product p : products) {
            System.out.printf("ID: %-4d %-25s ₹%.2f%n", p.getProduct_id(), p.getName(), p.getPrice());
        }

        System.out.print("\nEnter Product ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline

        // find the product in the list
        Product target = null;
        for (Product p : products) {
            if (p.getProduct_id() == id) {
                target = p;
                break;
            }
        }

        if (target == null) {
            System.out.println("\u001B[31mInvalid Product ID.\u001B[0m");
            return;
        }

        System.out.println("\nLeave blank to keep the same value.\n");

        System.out.print("Name (" + target.getName() + "): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) target.setName(name);

        System.out.print("Price (" + target.getPrice() + "): ");
        String priceInput = sc.nextLine();
        if (!priceInput.isEmpty()) target.setPrice(Double.parseDouble(priceInput));

        System.out.print("Quantity (" + target.getQuantity() + "): ");
        String qtyInput = sc.nextLine();
        if (!qtyInput.isEmpty()) target.setQuantity(Integer.parseInt(qtyInput));

        System.out.print("Category (" + target.getCategory() + "): ");
        String category = sc.nextLine();
        if (!category.isEmpty()) target.setCategory(category);

        System.out.print("Description (" + target.getDesc() + "): ");
        String desc = sc.nextLine();
        if (!desc.isEmpty()) target.setDesc(desc);

        boolean status = productDao.updateProduct(id, target);

        System.out.println(status
                ? "\n\u001B[32mProduct updated successfully!\u001B[0m"
                : "\n\u001B[31mFailed to update product.\u001B[0m");
    }




}
