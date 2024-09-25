package com.example.datn.dto;

import com.example.datn.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SanPhamChiTietDTO extends SuperDTO {

    private BigDecimal giaSanPham;

    private int soLuong;

    private String trongLuong;

    private int gioiTinh;

    private String moTa;


    private SanPhamDTO sanPham;

    private MauSacDTO mauSac;

    public SanPhamChiTietDTO(SuperDTOBuilder<?, ?> b, BigDecimal giaSanPham, SanPhamDTO sanPham, MauSacDTO mauSac, KichCoDTO kichCo) {
        super(b);
        this.giaSanPham = giaSanPham;
        this.sanPham = sanPham;
        this.mauSac = mauSac;
        this.kichCo = kichCo;
    }

    private KichCoDTO kichCo;

    private NSXDTO nsx;

    private ChatLieuDTO chatLieu;

    private HinhAnhDTO hinhAnh;

    private DanhMucDTO danhMuc;

    private BaoHanhDTO baoHanh;
    private int trangThai;


}
