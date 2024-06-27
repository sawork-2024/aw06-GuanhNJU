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

    @GetMapping("/submit_cart")
    @ResponseBody
    public String submitCart(@RequestParam("items") String items_str) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items= null;
        try {
            items = objectMapper.readValue(items_str, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("items:"+items);
        String cart=posService.submitCart(items);
        System.out.println(cart);
        return cart;
    }


}
