package webpos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webpos.biz.PosService;
import webpos.model.Item;

import java.util.List;
@CrossOrigin
@RestController
public class PosController {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/settings")
    public Object posSettings(){
        System.out.println("settings");
        return posService.getSettings();
    }

    @GetMapping("/categories")
    public Object posCategories(){
        System.out.println("categories");
        return posService.getCategories();
    }

    @GetMapping("/product")
    public List productlist() {
        //posService.add("PD1", 2);
        try{
            System.out.println("product1");
            //睡5秒，网关Hystrix3秒超时，会触发熔断降级操作
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(posService.products().toString());
        return posService.products();
    }

    @GetMapping("/product_id")
    @ResponseBody
    public Object productById(@RequestParam("productId") String productId) {
        System.out.println("productById:"+productId);
        Object product=posService.getProductById(productId);
        System.out.println(product.toString());
        return product;
    }



}
