package com.example.datn.controller;

import com.example.datn.Repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/thongke")
public class ThongKe1Controller {
  @Autowired
  private SanPhamRepository sanPhamRepository;
  @GetMapping("/sanPhamBanChay")
  public String sanPhamBanChay() {
    return "admin/adminWeb/SanPhamBanChay";
  }
  @GetMapping("/theothang")
  public String timKiemTheoNgay() {
    return "admin/adminWeb/ThongKeTheoThang";
  }
}
