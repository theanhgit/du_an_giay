package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCrud {
    private String taiKhoan;

    private String ten;

    private String tenDem;

    private String ho;

    private String sdt;

    private String matKhau;

    private LocalDate ngaySinh;

    private int gioiTinh;

    private int trangThai;
    private String email;

    private UUID vaiTro;
    private UUID diaChi;

}
