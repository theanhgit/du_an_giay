package com.example.datn.controller;

import com.example.datn.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DangKiController {
    @GetMapping("/dang-ki")
    public String home(Model model, HttpSession session){

        return "web/DangKi";
    }
}
