package com.example.datn.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrangThaiHoaDonDTO extends SuperDTO{
    private UUID hoaDonId;

    private String ten;

    private String trangThai;

    public TrangThaiHoaDonDTO(UUID hoaDonId, String trangThai) {
        this.hoaDonId = hoaDonId;
        this.trangThai = trangThai;
    }
}
