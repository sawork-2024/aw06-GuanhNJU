package webpos.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import webpos.biz.PosService;
import webpos.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class PosController {

    private PosService posService;

    @Autowired
    MyFeignClient myFeignClient;

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
        System.out.println(posService.products().toString());
        return posService.products();
    }

    @GetMapping("/productinfo")
    public List productinfo() {
        return myFeignClient.productlist();
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
