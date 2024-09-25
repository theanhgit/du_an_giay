package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamChiTietCrud extends SuperDTO {
    private BigDecimal giaSanPham;

    private int soLuong;

    private String trongLuong;

    private int gioiTinh;

    private String moTa;


    private UUID sanPham;

    private UUID mauSac;

    private UUID kichCo;

    private UUID nsx;

    private UUID chatLieu;

    private UUID hinhAnh;

    private UUID danhMuc;

    private UUID baoHanh;
    private int trangThai;
}
