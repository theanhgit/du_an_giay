package com.example.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UpdateMatKhauController {
    @GetMapping("/update-matkhau")
    public String home(){
        return "web/UpdateMatKhau";
    }
}
