package webpos.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import webpos.db.PosDB;
import webpos.model.Cart;
import webpos.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JD implements PosDB {


    private List<Product> products = null;

    @Override
    public List<Product> getProducts() {
        try {
            if (products == null)
                products = parseJD("Java");
        } catch (IOException e) {
            products = new ArrayList<>();
        }
        return products;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Object readJson() {
        InputStream file = this.getClass().getClassLoader().getResourceAsStream("database.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Map result = null;
        try {
            result = objectMapper.readValue(file, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static List<Product> parseJD(String keyword) throws IOException {
        //获取请求https://search.jd.com/Search?keyword=java
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        //解析网页
        String cookie="__jda=143920055.1227138801.1710311711.1714887788.1717038750.4; mba_muid=1227138801; __jdu=1227138801; 3AB9D23F7A4B3CSS=jdd03MYXRIJCJZPKFF2MAJJVDCGP3SDNOLYNZ2MTLMX35QYYCSSG4JMJLKKKIFFG764MEWKVCHW6MXAIXKXKAVKYPPHXH6AAAAAMPY56CSVYAAAAADOQXEKSWIZOLJEX; TrackID=1ryxCSeLxOSlidAUszxvWPoq_XgEjetpNAlNsdkwiR6P8kY4-8bBTInV7IUqVGDTEx2Vy5DhWAAlT0uSy1IvMeGEjscLVPeLtql0tQ2_NI4GDcvUYBMQsGCPKR3bMdWBM; pinId=cs_1VuUoRT6f_u8k0jtqSA; pin=jd_hPRspIUTGSqo; unick=u_29t65xvbx7h8; _tp=c1llg2cQesC7hcBWcN6l4A%3D%3D; _pst=jd_hPRspIUTGSqo; xapieid=jdd03MYXRIJCJZPKFF2MAJJVDCGP3SDNOLYNZ2MTLMX35QYYCSSG4JMJLKKKIFFG764MEWKVCHW6MXAIXKXKAVKYPPHXH6AAAAAMPY56CSVYAAAAADOQXEKSWIZOLJEX; shshshfpa=aebf6053-6f59-261f-eda3-624978a7e0bf-1714817742; shshshfpx=aebf6053-6f59-261f-eda3-624978a7e0bf-1714817742; shshshfpb=BApXc0C50xOpASc5DnR7Iq19qCz7DYVFlBlbAcG1q9xJ1MnIxK4C2; qrsc=3; __jdb=143920055.3.1227138801|4.1717038750; __jdv=143920055%7Cdirect%7C-%7Cnone%7C-%7C1717038750518; __jdc=143920055; mba_sid=17170387505287548546150375157.1; wlfstk_smdl=txq41y3zl1mlsmx175fbx4dqyjlsn0kh; _gia_d=1; thor=547EF4F8ACB1A25F83231295AC366656999EB84086C43F7D088626414BA28167F9EC3BB43D0A4735141E9D8192B7AE04F2B672B35A3189964CCA8C48FB0CCBE27CD4A90598A1D9BA2C405BA8C873AC01FF7453C40F5A5B3076ADFEAA0019AE2BF38B5041C5954C78C7023739A389E67C40A0E32EB1EF74566EFC9613CA3C5C4D42D8FBAD009730BD8E7E51F5914DA33F631352958A49D05E3A01EFB54E271DE0; flash=2_0qJfKVOt1WtwJnct_r-KjjMB5MQohYaIkD7NTFM1TKRnx_l0a_Dc89_QMJhpGvO6MTiA0ihNf82HjcupROKckYH8WdjsF6bic5koPuXxMD6pvsD7YgLtka8CruR_SZpiG7WwfllGarHuHTViufJX8UvRQQg7y6vt7VlOGRa_GCo*; ceshi3.com=000; ipLoc-djd=12-904-0-0; jsavif=1; jsavif=1; areaId=12; 3AB9D23F7A4B3C9B=MYXRIJCJZPKFF2MAJJVDCGP3SDNOLYNZ2MTLMX35QYYCSSG4JMJLKKKIFFG764MEWKVCHW6MXAIXKXKAVKYPPHXH6A";
        HashMap<String, String> cookies = new HashMap<String, String>();
        String[] items = cookie.trim().split(";");
        for (String item:items)
            cookies.put(item.split("=")[0], item.split("=")[1]);

        Document document = Jsoup.connect(url).cookies(cookies).timeout(10000).get();

//        Document document = Jsoup.parse(new URL(url), 10000);
        //所有js的方法都能用
        Element element = document.getElementById("J_goodsList");
        //获取所有li标签
        Elements elements = element.getElementsByTag("li");
//        System.out.println(element.html());
        List<Product> list = new ArrayList<>();

        //获取元素的内容
        for (Element el : elements
        ) {
            //关于图片特别多的网站，所有图片都是延迟加载的
            String id = el.attr("data-spu");
            String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            String price = el.getElementsByAttribute("data-price").text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            if (title.indexOf("，") >= 0)
                title = title.substring(0, title.indexOf("，"));
            price=price.replace("￥","");
            if(price.trim().length()==0)
                price="0.00";
            Product product = new Product(System.currentTimeMillis()%1000000+id, title, Double.parseDouble(price), img);

            list.add(product);
        }
        return list;
    }

}
