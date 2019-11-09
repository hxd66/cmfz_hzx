package com.baizhi.action;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/Admin")
public class AdminAction {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public String login(Admin admin, HttpServletRequest request, HttpSession session, String enCode) {
        try {
            Admin admin1 = adminService.selectOne(admin, enCode, session);
            session.setAttribute("login", admin1);
        } catch (Exception e) {
            session.setAttribute("message", e.getMessage());
            return "redirect:/login/login.jsp";
        }
        return "redirect:/index.jsp";

    }

    @RequestMapping("/code")
    public String getcode(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        CreateValidateCode createValidateCode = new CreateValidateCode();
        String string = createValidateCode.getString();
        BufferedImage image = createValidateCode.getImage();
        session.setAttribute("code", string);
        response.setContentType("image/jpeg");
        //打给浏览器
        try {
            ImageIO.write(image, "jpeg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("loginout")
    public String loginout(HttpSession session) {
        session.removeAttribute("login");
        return "redirect:/login/login.jsp";
    }
}
