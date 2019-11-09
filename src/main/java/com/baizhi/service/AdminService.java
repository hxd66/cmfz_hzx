package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;

public interface AdminService {
    public Admin selectOne(Admin admin, String code, HttpSession session);
}
