package com.example.datn.dto;

import com.example.datn.entity.KichCoEntity;
import com.example.datn.entity.MauSacEntity;
import com.example.datn.entity.SanPhamEntity;
import com.example.datn.entity.VouCherEntity;
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
public class HoaDonCTDTO {
    private UUID sanPhamId;
    private String sanPham;
    private String mauSac;
    private String kichCo;
    private int soLuong;
    private BigDecimal giaBan;
    private BigDecimal thanhTien;
    private VouCherEntity vouCher;
    public HoaDonCTDTO(SanPhamEntity sanPham, MauSacEntity mauSac, KichCoEntity kichCo, int soLuong, BigDecimal giaBan, BigDecimal thanhTien, VouCherEntity VouCher) {
        this.sanPham = sanPham.getTenSanPham();  // Assuming SanPhamEntity has a method getTenSanPham() returning String
        this.mauSac = String.valueOf(mauSac);  // Convert MauSacEntity to String as needed
        this.kichCo = String.valueOf(kichCo);  // Convert KichCoEntity to String as needed
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.thanhTien = thanhTien;
        this.vouCher = VouCher;
    }


}
