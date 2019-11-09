package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.dao.ChapterDAO;
import com.baizhi.entity.Chapter;
import com.baizhi.redis.RedisCache;
import com.baizhi.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDAO chapterDAO;
    @Autowired
    private AlbumDAO albumDAO;

    @Override
    public void addOne(Chapter chapter) {
        chapterDAO.insert(chapter);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    @RedisCache
    public List<Chapter> selectAll(String id, Integer page, Integer rows) {
       /* Example example = new Example(Chapter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("albumId",id);*/
        Chapter chapter = new Chapter();
        chapter.setAlbumId(id);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Chapter> chapters = chapterDAO.selectByRowBounds(chapter, rowBounds);
        return chapters;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Integer getCount(String id) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(id);
        chapter.setCreateDate(new Date());
        List<Chapter> chapters = chapterDAO.select(chapter);
        return chapters.size();
    }

    @Override
    public void update(Chapter chapter) {
        chapterDAO.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public void del(Chapter chapter) {
        chapterDAO.delete(chapter);
    }

    @Override
    public List<Chapter> selectAll(Chapter chapter) {
        List<Chapter> chapters = chapterDAO.select(chapter);
        return chapters;
    }
}
