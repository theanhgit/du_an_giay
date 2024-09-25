package com.example.datn.controller;

import com.example.datn.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ThanhToanController {
    @GetMapping("/payment-infor")
    public String home(Model model, HttpSession session){
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "web/paymentSuccess"; // Tên của file HTML bạn sẽ tạo
    }
}
