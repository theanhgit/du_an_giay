package com.example.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "GioHangChiTiet")
@IdClass(GioHangChiTietId.class)
public class GioHangChiTietEntity implements Serializable {

    @ManyToOne
    @Id
    @JoinColumn(name = "gioHang_id")
    private GioHangEntity gioHang;

    @ManyToOne
    @Id  
    @JoinColumn(name = "sanPhamChitiet_id")
    private SanPhamChiTietEntity sanPhamChiTiet;
    @Column(name = "soLuong", length = 10, nullable = false)
    private int soLuong;

    @Column(name = "createDate")
    private LocalDate createDate;

    @Column(name = "updateDate")
    private LocalDate updateDate;
    @Column(name = "trangThai", length = 50, nullable = false, unique = true)
    private int trangThai;


}
