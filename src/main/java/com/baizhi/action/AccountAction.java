package com.baizhi.action;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("account")
public class AccountAction {
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public Map<String, Object> login(String phone, String password, String code) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (!"".equals(password)) {
                User user = userService.selectByPhoneAndpwd(phone, password);
                map.put("password", user.getPassowrd());
                map.put("farmington", user.getDharma());
                map.put("uid", user.getId());
                map.put("nickname", user.getUsername());
                map.put("gender", user.getSex());
                map.put("photo", user.getHeadImg());
                map.put("location", user.getProvince() + user.getCity());
                map.put("province", user.getProvince());
                map.put("city", user.getCity());
                map.put("description", user.getSign());
                map.put("phone", user.getPhone());
                return map;
            } else {
                User user = userService.selectByPhoneAndpwd(phone, code);
                map.put("password", user.getPassowrd());
                map.put("farmington", user.getDharma());
                map.put("uid", user.getId());
                map.put("nickname", user.getUsername());
                map.put("gender", user.getSex());
                map.put("photo", user.getHeadImg());
                map.put("location", user.getProvince() + user.getCity());
                map.put("province", user.getProvince());
                map.put("city", user.getCity());
                map.put("description", user.getSign());
                map.put("phone", user.getPhone());
                return map;
            }
        } catch (Exception e) {
            map.put("error", -200);
            map.put("errmsg", "密码错误");
            e.printStackTrace();
            return map;
        }
    }

    @RequestMapping("regist")
    public Map<String, Object> regist(String phone, String password) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            User user1 = new User();
            String id = UUID.randomUUID().toString();
            user1.setId(id);
            user1.setPhone(phone);
            user1.setPassowrd(password);
            java.util.Date date = new java.util.Date();
            user1.setCreateDate(new Date(date.getTime()));
            userService.addOne(user1);
            User user = userService.selectByid(user1);
            map.put("password", user.getPassowrd());
            map.put("uid", user.getId());
            map.put("phone", user.getPhone());
        } catch (Exception e) {
            map.put("error", -200);
            map.put("errmsg", "该手机号已经存在");
            e.printStackTrace();
        }

        return map;
    }

    @RequestMapping("update")
    public Map<String, Object> update(User user1) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            Map<String, Object> update = userService.update(user1);
            User user = userService.selectByid(user1);
            map.put("password", user.getPassowrd());
            map.put("farmington", user.getDharma());
            map.put("uid", user.getId());
            map.put("nickname", user.getUsername());
            map.put("gender", user.getSex());
            map.put("photo", user.getHeadImg());
            map.put("location", user.getProvince() + user.getCity());
            map.put("province", user.getProvince());
            map.put("city", user.getCity());
            map.put("description", user.getSign());
            map.put("phone", user.getPhone());
        } catch (Exception e) {
            map.put("error", -200);
            map.put("errmsg", "该手机号已经存在");
            e.printStackTrace();
        }

        return map;
    }
}
