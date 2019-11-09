package com.baizhi.dao;

import com.baizhi.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDAO extends Mapper<Article> {
    public List<Article> selectOwn(String uid);

    public List<Article> selectNoOwn(String uid);
}
