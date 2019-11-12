package com.baizhi.action;

import com.baizhi.service.AdminService;
import com.baizhi.util.CreateValidateCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
    public String login(String adminName, String password, HttpServletRequest request, HttpSession session, String enCode) {
        String code = (String) session.getAttribute("code");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(adminName, password);
        try {
            subject.login(usernamePasswordToken);
            if (code.equals(enCode)) {
                return "redirect:/index.jsp";
            } else {
                session.setAttribute("message", "验证码错误");
                return "redirect:/login/login.jsp";
            }
        } catch (UnknownAccountException e) {
            session.setAttribute("message", "用户名错误");
            e.printStackTrace();
            return "redirect:/login/login.jsp";
        } catch (IncorrectCredentialsException e) {
            session.setAttribute("message", "密码错误");
            e.printStackTrace();
            return "redirect:/login/login.jsp";
        }


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
