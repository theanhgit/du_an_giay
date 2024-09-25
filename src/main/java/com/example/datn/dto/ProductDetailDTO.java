package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@Data
public  class ProductDetailDTO {
    private UUID sanPhamId;
    private String tenSanPham;
    private String mauSac;
    private String kichCo;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal thanhTien;

    // Constructor
    public ProductDetailDTO() {
        this.sanPhamId = sanPhamId;
        this.tenSanPham = tenSanPham;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
    }

    // Getters and Setters
    public UUID getSanPhamChiTietId() {
        return sanPhamId;
    }

    public void setSanPhamChiTietId(UUID sanPhamChiTietId) {
        this.sanPhamId = sanPhamChiTietId;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getKichCo() {
        return kichCo;
    }

    public void setKichCo(String kichCo) {
        this.kichCo = kichCo;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }
}