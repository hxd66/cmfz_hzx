package com.baizhi.service.impl;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDAO adminDAO;

    @Override
    public Admin selectOne(Admin admin, String code, HttpSession session) {
        String code1 = (String) session.getAttribute("code");
        Admin admin1 = adminDAO.selectOne(admin);
        if (admin1 == null) throw new RuntimeException("用户名或密码错误");
        if (!code.equals(code1) || code == null) throw new RuntimeException("验证码错误");
        return admin1;
    }
}
