package org.example.entity;

public class Product {

    private int product_id;
    private String name;
    private String desc;
    private double price;
    private int quantity;
    private String category;

    public Product() {}

    public Product(String name, double price, String desc, int quantity, String category) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.quantity = quantity;
        this.category = category;
    }

    public Product(int product_id, String name, double price, String desc, int quantity, String category){
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.quantity = quantity;
        this.product_id = product_id;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return
                "product_id=" + product_id +
                ", pname='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", quantity=" + quantity ;
    }
}

