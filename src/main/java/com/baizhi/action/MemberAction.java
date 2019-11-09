package com.baizhi.action;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberAction {
    @Autowired
    private UserService userService;

    @RequestMapping("member")
    public Map<String, Object> member(String uid) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            User user = new User();
            user.setId(uid);
            User user1 = userService.selectByid(user);
            map.put("user", user1);
        } catch (Exception e) {
            map.put("error", -200);
            map.put("errmsg", "会员列表为空");
            e.printStackTrace();
        }
        return map;

    }
}
