package com.example.datn.controller;

import com.example.datn.Repository.ChiTietSPRepository;
import com.example.datn.Repository.HoaDonRepository;
import com.example.datn.entity.ChatLieuEntity;
import com.example.datn.entity.HoaDonEntity;
import com.example.datn.entity.SanPhamChiTietEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping()
public class BanHangOff {

    @Autowired
    private ChiTietSPRepository chiTietSPRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/BanHangOff")
    public String DSSanPham(Model model) {
        List<SanPhamChiTietEntity> sanPhamChiTiet = chiTietSPRepository.findAll();
        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        model.addAttribute("SanPham", new SanPhamChiTietEntity());
        List<HoaDonEntity> danhSachHoaDon = hoaDonRepository.findAll();
        model.addAttribute("danhSachHoaDon", danhSachHoaDon);
        return "/admin/adminWeb/BanHangOff";
    }
    @GetMapping("/thongke")
    public String thongKe() {
        return "/admin/adminWeb/ThongKeTheoThang";
    }
    @GetMapping("/sanpham")
    public String sanPham() {
        return "/admin/adminWeb/SanPhamBanChay";
    }
}
