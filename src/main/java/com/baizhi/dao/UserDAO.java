package com.baizhi.dao;

import com.baizhi.entity.China;
import com.baizhi.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDAO extends Mapper<User> {
    public List<User> selectByDate(Integer i, String sex);

    public List<China> selectBySexAndPro(String sex);
}
