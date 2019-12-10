package com.baizhi.action;

import com.baizhi.api.BaseApiService;
import com.baizhi.entity.China;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseApiService {
    @Autowired
    private UserService userService;

    @RequestMapping("/showall")
    @ResponseBody
    public Map<String, Object> showAll(Integer page, Integer rows) {
        return userService.selectAll(page, rows);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> insert(String oper, User user, HttpSession session) {

        if ("add".equals(oper)) {
            user.setId(UUID.randomUUID().toString());
            return userService.addOne(user);
        } else if ("edit".equals(oper)) {
            return userService.update(user);
        } else if ("del".equals(oper)) {
            return userService.del(user);
        }
        return null;
    }

    @RequestMapping("/showDate")
    @ResponseBody
    public Map<String, Object> showDate() {
        Map<String, Object> map = new HashMap<>();
        List<Integer> integers = userService.selectByDate("女");
        List<Integer> integer2 = userService.selectByDate("男");
        map.put("men", integers);
        map.put("man", integer2);
        return map;
    }

    @RequestMapping("/showMap")
    @ResponseBody
    public Map<String, Object> showMap() {
        Map<String, Object> map = new HashMap<>();
        List<China> men = userService.selectBySexAndPro("女");
        List<China> man = userService.selectBySexAndPro("男");
        map.put("men", men);
        map.put("man", man);
        return map;
    }

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/66")
    public void sendMsg(String msg) {
        ActiveMQQueue hzx = new ActiveMQQueue("hzx");
        jmsTemplate.convertAndSend(hzx, msg);
    }
}
