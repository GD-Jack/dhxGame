package com.jack.controller;


import com.jack.entity.User;
import com.jack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/login")
    public ModelAndView login(String email, String password) {
        ModelAndView mv = new ModelAndView();
        int result = service.login(email, password);
        if (result == 1) {
            mv.addObject("user", service.selectByEmail(email));
            mv.addObject("msg", "登录成功");
            mv.setViewName("index");
        } else {
            mv.addObject("msg", "邮箱或错误密码");
            mv.setViewName("login");
        }
        return mv;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "index";
    }

    @PostMapping("/signUp")
    public ModelAndView signUp(String name, String email, String code, String password, Model model) {
        ModelAndView mv = new ModelAndView();
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        code = code.toUpperCase();
        String securityCode = service.getSecurityCode(email, "signUp");
        if (securityCode != null && securityCode.equals(code)) {
            int result = service.signUp(user);
            if (result == 1) {
                model.addAttribute("user", user);
                mv.setViewName("index");
            } else {
                mv.addObject("msg", "邮箱已被注册");
                mv.setViewName("signUp");
            }
        } else {
            mv.addObject("msg", "验证码错误");
            mv.setViewName("signUp");
        }
        return mv;
    }

    @RequestMapping("/emailInspection")
    public void emailInspection(String email, HttpServletResponse response) throws IOException {
        int result;
        if (service.selectByEmail(email) != null)
            result = 1;
        else
            result = 2;
        response.setContentType("application/text;charset=utf-8");
        PrintWriter pw  = response.getWriter();
        pw.println(result);
        pw.flush();
        pw.close();
    }

    @RequestMapping("/sendCode")
    public void sendCode(String email, HttpServletResponse response, HttpSession session) {
        session.setAttribute("email", email);
        response.setContentType("application/text;charset=utf-8");
        service.sendSecurityCode(email);
    }

    @RequestMapping("/information")
    public void information(HttpSession session) {
        User user = (User) session.getAttribute("user");

    }

    @PostMapping("/changePassword")
    public ModelAndView changePassword(String email, String code, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        code = code.toUpperCase();
        String securityCode = service.getSecurityCode(email, "resetPassword");
        if (securityCode != null && securityCode.equals(code)) {
            session.setAttribute("email", email);
            mv.addObject("msg", "请输入新密码");
            mv.setViewName("resetPassword");
        } else {
            session.removeAttribute("email");
            mv.addObject("msg", "验证码错误");
            mv.setViewName("changePassword");
        }
        return mv;
    }

    @PostMapping("/resetPassword")
    public String resetPassword(String password, HttpSession session) {
        String email = (String) session.getAttribute("email");
        service.resetPassword(email, password);
        session.setAttribute("user", service.selectByEmail(email));
        return "index";
    }
}
