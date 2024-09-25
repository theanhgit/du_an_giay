package com.example.datn.controller;

import com.example.datn.Repository.ChiTietSPRepository;
import com.example.datn.entity.SanPhamChiTietEntity;
import com.example.datn.entity.SanPhamEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;
@Controller
public class SanPhamCTController {
    private final ChiTietSPRepository chiTietSPRepository;

    public SanPhamCTController(ChiTietSPRepository chiTietSPRepository) {
        this.chiTietSPRepository = chiTietSPRepository;
    }


}
