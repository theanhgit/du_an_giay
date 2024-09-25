package com.example.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForWordController {
    @GetMapping("/forwordpass")
    public String home(){
        return "web/ForgotPassWord";

    }
}
