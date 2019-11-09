package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Map<String, Object> addOne(Article article);

    public Map<String, Object> selectAll(Integer page, Integer rows);

    public Map<String, Object> update(Article article);

    public Map<String, Object> del(Article article);

    public List<Article> selectAll();

    public List<Article> selectOwn(String uid);

    public List<Article> selectByUid(String uid);

    public Article selectByid(String uid);

    public List<Article> selectNoUid(String uid);
}
