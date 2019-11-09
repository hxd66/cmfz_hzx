package com.baizhi.service.impl;

import com.baizhi.dao.AlbumDAO;
import com.baizhi.entity.Album;
import com.baizhi.redis.RedisCache;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDAO albumDAO;

    @Override
    public void addOne(Album album) {
        albumDAO.insert(album);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<Album> selectAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Album> albums = albumDAO.selectByRowBounds(new Album(), rowBounds);
        return albums;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public Integer getCount() {
        int i = albumDAO.selectCount(new Album());
        return i;
    }

    @Override
    public void upload(Album album) {
        albumDAO.updateByPrimaryKeySelective(album);
    }

    @Override
    public void del(Album album) {
        albumDAO.delete(album);
    }

    @Override
    public Album selectOne(Album album) {
        Album album1 = albumDAO.selectByPrimaryKey(album);
        return album1;
    }

    @Override
    @RedisCache
    public List<Album> selectall() {
        List<Album> albums = albumDAO.selectAll();
        return albums;
    }

    @Override
    public List<Album> selectNew() {
        List<Album> albums = albumDAO.selectNew();
        return albums;
    }
}
