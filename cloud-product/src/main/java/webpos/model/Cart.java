package webpos.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class Cart {
    public Cart() {

    }

    private Long cart_id;

    private boolean charged;


    private double total_price;

    private List<Item> items = new ArrayList<>();

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public boolean getCharged() {
        return charged;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<Item> getItems() {
        return items;
    }


    public boolean addItem(Item item) {
        //item.setCart_id(this.cart_id);
        return items.add(item);
    }

    public boolean setItem(int idx, Item item) {
        //item.setCart_id(this.cart_id);
        if (idx >= items.size()) {
            return false;
        }
        items.set(idx, item);
        return true;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : items) {
            total += item.getAmount() * item.getProduct().getPrice();
        }
        this.total_price = total;
        return total;
    }

    @Override
    public String toString() {
        if (items.size() == 0) {
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n");

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getAmount() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n");

        stringBuilder.append("Total...\t\t\t" + total);

        return stringBuilder.toString();
    }

    public boolean removeItem(int idx) {
        items.remove(idx);
        return true;
    }
}
