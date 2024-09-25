package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HDCTDTO {
    private UUID sanPhamId;
    private String sanPham;
    private String mauSac;
    private String kichCo;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal thanhTien;
    private String tenVoucher;
    private int phanTramGiam;
    private BigDecimal giaSauKhiGiam;

    // Method to calculate giaSauKhiGiam (Price after discount)
    public BigDecimal getGiaSauKhiGiam() {
        if (phanTramGiam > 0 && thanhTien != null) {
            BigDecimal discount = thanhTien.multiply(BigDecimal.valueOf(phanTramGiam)).divide(BigDecimal.valueOf(100));
            return thanhTien.subtract(discount);
        } else {
            return thanhTien; // Return thanhTien if no discount is applied
        }
    }

    // Override setter for soLuong to update thanhTien
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        calculateThanhTien(); // Recalculate thanhTien when soLuong is updated
    }

    // Override setter for giaBan to update thanhTien
    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
        calculateThanhTien(); // Recalculate thanhTien when giaBan is updated
    }

    // Method to calculate thanhTien based on giaBan and soLuong
    private void calculateThanhTien() {
        if (giaBan != null && soLuong > 0) {
            this.thanhTien = giaBan.multiply(BigDecimal.valueOf(soLuong));
        } else {
            this.thanhTien = BigDecimal.ZERO; // Default to 0 if conditions aren't met
        }
    }
}
