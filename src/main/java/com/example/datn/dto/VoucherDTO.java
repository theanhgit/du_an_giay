package com.example.datn.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO extends SuperDTO {

    private String ten;

    private int phanTramGiam;

    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

    private int trangThai;
}
