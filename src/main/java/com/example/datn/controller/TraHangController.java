package com.example.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraHangController {
    @GetMapping("/tra-hang")
    public String home(){
        return "admin/adminWeb/TraHang";
    }
}
