package org.example.entity;

public class Product {
    private long product_id;
    private String pname;
    private String desc;
    private double price;
    private int quantity;

    public Product(){
    }
    public Product(String pname,double price,String desc,int quantity){
        this.pname=pname;
        this.price=price;
        this.desc=desc;
        this.quantity=quantity;
    }
    public Product(long product_id,String pname,double price,String desc,int quantity){
        this.pname=pname;
        this.price=price;
        this.desc=desc;
        this.quantity=quantity;
        this.product_id= product_id;
    }

    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
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

    @Override
    public String toString() {
        return
                "product_id=" + product_id +
                ", pname='" + pname + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", quantity=" + quantity ;
    }
}

