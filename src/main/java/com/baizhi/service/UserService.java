package com.baizhi.service;

import com.baizhi.entity.China;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, Object> addOne(User user);

    public Map<String, Object> selectAll(Integer page, Integer rows);

    public Map<String, Object> update(User user);

    public Map<String, Object> del(User user);

    public List<Integer> selectByDate(String sex);

    public List<China> selectBySexAndPro(String sex);

    public User selectByPhoneAndpwd(String phone, String pwd);

    public User selectByid(User user);
}
