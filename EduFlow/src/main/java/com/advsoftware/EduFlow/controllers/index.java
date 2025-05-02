package com.advsoftware.EduFlow.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class index {

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/new-login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String indexPage() {
        return "register";
    }

    @GetMapping("/index-page")
    public String showIndexPage() {
        return "success";
    }
}
