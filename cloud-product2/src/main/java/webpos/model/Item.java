package webpos.model;

import jakarta.persistence.*;



public class Item {

    private double price;
    private String product_name;
    private Long id;


    private String product_id;


    private int amount;



    private Long cart_id;
    //    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")

    private Product product;

    public Long getId() {
        return id;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Item() {
    }

    public Item(String product_id, String product_name,int amount, double price) {
        this.product_id = product_id;
        this.product_name= product_name;
        this.amount = amount;
        this.price=price;
    }

    public Item(Product product, int amount) {
        this.product_id = product.getId();
        this.product = product;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.product_id + "\t" + amount;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
