package com.baizhi.service.impl;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.ArticleDAO;
import com.baizhi.entity.Article;
import com.baizhi.redis.RedisCache;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl extends BaseApiService implements ArticleService {
    @Autowired
    private ArticleDAO articleDAO;

    @Override
    public Map<String, Object> addOne(Article article) {
        article.setId(UUID.randomUUID().toString());
        java.util.Date date = new java.util.Date();
        article.setCreateDate(new Date(date.getTime()));
        articleDAO.insert(article);
        return setResultSuccess();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Article> articles = articleDAO.selectByRowBounds(article, rowBounds);
        Integer records = articleDAO.selectCount(article);
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        return setResultSuccessDataByPage(articles, page, total, records);
    }


    @Override
    public Map<String, Object> update(Article article) {
        if (" ".equals(article.getContent())) {
            article.setContent(null);
        }
        articleDAO.updateByPrimaryKeySelective(article);
        return setResultSuccess();
    }

    @Override
    public Map<String, Object> del(Article article) {
        articleDAO.delete(article);
        return setResultSuccess();
    }

    @Override
    @RedisCache
    public List<Article> selectAll() {
        List<Article> articles = articleDAO.selectAll();
        return articles;
    }

    @Override
    public List<Article> selectOwn(String uid) {
        List<Article> articles = articleDAO.selectOwn(uid);
        return articles;
    }

    @Override
    public List<Article> selectByUid(String uid) {
        List<Article> select = articleDAO.select(new Article());
        return select;
    }

    @Override
    public Article selectByid(String uid) {
        Article article = new Article();
        article.setId(uid);
        Article i = articleDAO.selectByPrimaryKey(article);
        return i;
    }

    @Override
    public List<Article> selectNoUid(String uid) {
        List<Article> articles = articleDAO.selectNoOwn(uid);
        return articles;
    }
}
