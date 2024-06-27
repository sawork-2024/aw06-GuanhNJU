package webpos.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Primary
@FeignClient(name = "product1", fallback = MyHystrix.class)
public interface MyFeignClient {
    @GetMapping("/product")
    public List productlist();
}
