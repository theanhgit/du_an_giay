package com.example.datn.controller;

import com.example.datn.Repository.UserRepository;
import com.example.datn.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UsersController {


private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/quanly-user")
    public String home(){
        return "admin/adminWeb/Users";
    }
}
