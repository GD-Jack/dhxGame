package com.jack.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
    @RequestMapping("/blog")
    public String blog() {
        return "blog";
    }
    @RequestMapping("/joinUs")
    public String joinUs() {
        return "joinUs";
    }
    @RequestMapping("/mall")
    public String mail() {
        return "mall";
    }
    @RequestMapping("/changePassword")
    public String changePassword() {
        return "changePassword";
    }
    @RequestMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

}
