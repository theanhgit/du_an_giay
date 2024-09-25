package com.example.datn.controller;

import com.example.datn.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DetailSPController {
    @GetMapping("/DetailSP/{id}")
    public String home(Model model, HttpSession session){
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "web/DetailSP.html";
    }
}
