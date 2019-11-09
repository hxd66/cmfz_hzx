package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;

public interface AlbumService {
    public void addOne(Album album);

    public List<Album> selectAll(Integer page, Integer rows);

    public Integer getCount();

    public void upload(Album album);

    public void del(Album album);

    public Album selectOne(Album album);

    public List<Album> selectall();

    public List<Album> selectNew();
}
