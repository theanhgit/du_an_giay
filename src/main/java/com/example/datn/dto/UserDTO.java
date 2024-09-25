package com.example.datn.dto;

import com.example.datn.entity.DiaChiEntity;
import com.example.datn.entity.VaiTroEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends SuperDTO{

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

    private VaiTroDTO vaiTro;

    private DiaChiDTO diaChi;
}
