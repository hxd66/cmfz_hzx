package com.baizhi.dao;

import com.baizhi.entity.Img;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ImgDAO extends Mapper<Img> {
    public List<Img> selectNew();
}
