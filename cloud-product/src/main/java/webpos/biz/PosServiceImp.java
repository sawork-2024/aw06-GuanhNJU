package webpos.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webpos.db.PosDB;
import webpos.db.RedisUtils;
import webpos.model.Item;
import webpos.model.Product;

import java.util.List;
import java.util.Map;


@Service
@Transactional
public class PosServiceImp implements PosService {

    private PosDB posDB;
    private RedisUtils redisUtils;
    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }
    @Autowired
    public void setRedisUtils(RedisUtils redisUtils)
    {
        this.redisUtils = redisUtils;
    }

    @Override
    public Object getSettings() {
        Object settings;
        System.out.println("redissettings==========------");
        if (redisUtils.haskey("settings")){
            settings = redisUtils.get("settings");
        }else{

            Map result = (Map) posDB.readJson();
            settings = result.get("settings");

            redisUtils.set("settings", settings);
        }
        return settings;
    }
    @Override
    public Object getCategories() {
        Object categories;
        if (redisUtils.haskey("categories")){
            categories = redisUtils.get("categories");
        }else{
            Map result = (Map) posDB.readJson();
            categories = result.get("categories");
            redisUtils.set("categories", categories);
        }
        return categories;
    }
    @Override
    public List<Product> products() {
        List<Product> products;
        if(redisUtils.haskey("products")){
            products=(List<Product>) redisUtils.get("products");
        }else{
            products=posDB.getProducts();
//            for(int idx=0;idx<products.size();idx++){
//                String old_id=products.get(idx).getId();
//                String new_id="product" + System.currentTimeMillis() +old_id;
//                Product product=products.get(idx);
//                product.setId(new_id);
//                product.set_id(new_id);
//                products.set(idx,product);
//                redisUtils.set(new_id,product);
//            }
            redisUtils.set("products",products);
        }

        return products;
    }

    @Override
    public Object getProductById(String productId){
        Product product;
        if(redisUtils.haskey(productId)){
            product=(Product) redisUtils.get(productId);
        }
        else{
            product=posDB.getProduct(productId);
            redisUtils.set(productId,product);
        }
        return product;
    }

    @Override
    public String submitCart(List<Item> items) {
        String cart_id="cart_"+System.currentTimeMillis()%1000000;
        redisUtils.set(cart_id,items);
        return cart_id;
    }
}
