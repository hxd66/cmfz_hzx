package com.baizhi.action;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("detail")
public class DetailAction {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("si")
    public Map<String, Object> si(String id, String uidc) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            Article articles = articleService.selectByid(id);
            map.put("code", 200);
            map.put("link", articles.getContent());
            map.put("id", id);
            map.put("ext", "");
        } catch (Exception e) {
            map.put("code", 500);
            map.put("msg", "参数错误");
            e.printStackTrace();
        }
        return map;
    }


    @RequestMapping("wen")
    public Map<String, Object> wen(String id, String uidc) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            Album album = new Album();
            album.setId(id);
            Album album1 = albumService.selectOne(album);
            Chapter chapter = new Chapter();
            chapter.setAlbumId(id);
            List<Chapter> chapters = chapterService.selectAll(chapter);
            map.put("code", 200);
            map.put("introduction", album1);
            map.put("list", chapters);
        } catch (Exception e) {
            map.put("code", 500);
            map.put("msg", "参数错误");
            e.printStackTrace();
        }
        return map;
    }

}
