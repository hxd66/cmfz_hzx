package com.baizhi.action;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Img;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FirstPageAction {
    @Autowired
    private ImgService imgService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("first_page")
    @ResponseBody
    public Map<String, Object> firstPage(String uid, String type, String sub_type) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        if ("all".equals(type)) {
            try {
                List<Img> imgs = imgService.selectNew();
                List<Album> albums = albumService.selectNew();
                List<Article> articles = articleService.selectOwn(uid);
                map2.put("albums", albums);
                map2.put("articles", articles);
                map.put("code", 200);
                map.put("header", imgs);

                map.put("body", map2);
            } catch (Exception e) {
                map.put("code", 500);
                map.put("msg", "参数错误");
                e.printStackTrace();
            }
        }
        if ("wen".equals(type)) {
            try {
                List<Album> albums = albumService.selectall();
                map.put("code", 200);
                map.put("body", albums);
            } catch (Exception e) {
                map.put("code", 500);
                map.put("msg", "参数错误");
                e.printStackTrace();
            }
        }
        if ("si".equals(type)) {
            if ("ssyj".equals(sub_type)) {
                try {
                    List<Article> articles = articleService.selectByUid(uid);
                    map.put("code", 200);
                    map.put("body", articles);
                } catch (Exception e) {
                    map.put("code", 500);
                    map.put("msg", "参数错误");
                    e.printStackTrace();
                }
            } else {
                try {
                    List<Article> articles = articleService.selectNoUid(uid);
                    map.put("code", 200);
                    map.put("body", articles);
                } catch (Exception e) {
                    map.put("code", 500);
                    map.put("msg", "参数错误");
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}

