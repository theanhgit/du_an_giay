package com.example.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraHangCRUD {
    private UUID hoaDon;

    private UUID sanPhamChiTiet;


    private String type;


    private String lyDo;
    private int soLuong;


    private int status; // Trạng thái yêu cầu: "pending", "approved", "rejected"

}
