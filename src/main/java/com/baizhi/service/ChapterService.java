package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;

public interface ChapterService {
    public void addOne(Chapter chapter);

    public List<Chapter> selectAll(String id, Integer page, Integer rows);

    public Integer getCount(String id);

    public void update(Chapter chapter);

    public void del(Chapter chapter);

    public List<Chapter> selectAll(Chapter chapter);
}
