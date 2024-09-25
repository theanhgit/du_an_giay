package com.example.datn.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class LoginController {
    @GetMapping("/dang-nhap")
    public String home() {
        return "web/login.html";
    }


}
