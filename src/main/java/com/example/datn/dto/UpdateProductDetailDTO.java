package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDetailDTO {
    private UUID id ;
    private String tenSanPham;
    private MauSacDTO mauSac;
    private KichCoDTO kichCo;
    private int soLuong;
}
