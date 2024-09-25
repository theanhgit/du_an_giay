package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonCHiTietCrud {
    private UUID hoaDon;

    private UUID sanPhamChiTiet;

    private int soLuong;

    private BigDecimal thanhTien;

    private LocalDate createDate;


    private LocalDate updateDate;

}
