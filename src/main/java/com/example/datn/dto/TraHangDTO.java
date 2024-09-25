package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraHangDTO extends SuperDTO{

    private HoaDonDTO hoaDon;

    private SanPhamChiTietDTO sanPhamChiTiet;


    private String type;


    private String lyDo;
    private int soLuong;


    private int status; // Trạng thái yêu cầu: "pending", "approved", "rejected"

}
