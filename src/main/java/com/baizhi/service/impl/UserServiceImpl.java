package com.baizhi.service.impl;

import com.baizhi.api.BaseApiService;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.China;
import com.baizhi.entity.User;
import com.baizhi.redis.RedisCache;
import com.baizhi.service.UserService;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl extends BaseApiService implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public Map<String, Object> addOne(User user) {
        user.setId(UUID.randomUUID().toString());
        Date date = new Date();
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        user.setCreateDate(date1);
        userDAO.insert(user);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-c746f63108c046c7be3f3a8ccc021179");
        goEasy.publish("my_channel", "Hello, GoEasy!");
        return setResultSuccess();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    @RedisCache
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDAO.selectByRowBounds(new User(), rowBounds);
        Integer records = userDAO.selectCount(new User());
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        return setResultSuccessDataByPage(users, page, total, records);
    }


    @Override
    public Map<String, Object> update(User user) {
        userDAO.updateByPrimaryKeySelective(user);
        return setResultSuccess();
    }

    @Override
    public Map<String, Object> del(User user) {
        userDAO.delete(user);
        return setResultSuccess();
    }

    @Override
    public List<Integer> selectByDate(String sex) {
        if (sex.equals("女")) {
            ArrayList<Integer> integers = new ArrayList<>();
            for (Integer j = 1; j <= 3; j++) {
                List<User> users = userDAO.selectByDate(j * 7, "女");
                integers.add(users.size());
            }
            return integers;
        } else {
            ArrayList<Integer> integers = new ArrayList<>();
            for (Integer j = 1; j <= 3; j++) {
                List<User> users = userDAO.selectByDate(j * 7, "男");
                integers.add(users.size());
            }
            return integers;
        }

    }

    @Override
    public List<China> selectBySexAndPro(String sex) {
        List<China> lists = null;
        if (sex.equals("女")) {
            lists = userDAO.selectBySexAndPro("女");

        } else {
            lists = userDAO.selectBySexAndPro("男");
        }
        return lists;
    }

    @Override
    public User selectByPhoneAndpwd(String phone, String pwd) {
        User user1 = new User();
        user1.setPhone(phone);
        user1.setPassowrd(pwd);
        User user = userDAO.selectOne(user1);
        return user;
    }

    @Override
    public User selectByid(User user) {
        User user1 = userDAO.selectByPrimaryKey(user);
        return user1;
    }
}
