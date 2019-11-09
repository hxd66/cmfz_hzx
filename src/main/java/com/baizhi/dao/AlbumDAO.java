package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AlbumDAO extends Mapper<Album> {
    void select(Chapter chapter);

    public List<Album> selectNew();
}
