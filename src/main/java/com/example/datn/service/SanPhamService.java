package com.example.datn.service;

import com.example.datn.dto.SanPhamChiTietDTO;
import com.example.datn.dto.SanPhamChiTietFiterDTO;
import com.example.datn.dto.SanPhamDTO;
import com.example.datn.dto.SanPhamFiterDTO;
import org.springframework.data.domain.Page;

public interface SanPhamService {
    Page<SanPhamDTO> getAllSanPham(Integer totalPage, Integer totalItem, SanPhamFiterDTO form);
    SanPhamDTO addSanPham(SanPhamDTO sanPhamDTO);
    SanPhamDTO upDateSanPham(SanPhamDTO sanPhamDTO);
}
