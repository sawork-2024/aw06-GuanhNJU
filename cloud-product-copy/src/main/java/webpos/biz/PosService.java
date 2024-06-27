package webpos.biz;

import webpos.model.Cart;
import webpos.model.Item;
import webpos.model.Product;

import java.util.List;

public interface PosService {



    List<Product> products();

    Object getSettings();
    Object getCategories();
    Object getProductById(String productId);
    String submitCart(List<Item> items);
}
