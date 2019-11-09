package com.baizhi.service;

import com.baizhi.entity.Img;

import java.util.List;

public interface ImgService {
    public void addOne(Img img);

    public List<Img> selectAll(Integer page, Integer rows);

    public Integer getCount();

    public void upload(Img img);

    public void del(Img img);

    public List<Img> selectNew();
}
