package webpos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



public class Product {
    private String id;
    private String _id;
    private String category;
    private int quantity;
    private int stock;
    private String name;

    private double price;

    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String image) {
        this.img = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Product() {
    }

    public Product(String id, String _id, double price, String category, int quantity, String name, int stock, String img) {
        this.id = id;
        this._id = _id;
        this.category = category;
        this.quantity = quantity;
        this.stock = stock;
        this.name = name;
        this.price = price;
        this.img = img;
    }
    public Product(String id, String title, double price, String img){

        this.id = id;
        this._id = id;
        this.category = "JD_Product";
        this.quantity = 10086;
        this.stock = 0;
        this.name = title;
        this.price = price;
        this.img = img;
    }
    @Override
    public String toString() {
        return getId() + "\t"+ getName() + "\t" + getPrice();
    }

}
