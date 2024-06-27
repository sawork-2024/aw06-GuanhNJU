package webpos.db;

import webpos.model.Product;

import java.util.List;

public interface PosDB {

    List<Product> getProducts();

    Product getProduct(String productId);

    Object readJson();
}
