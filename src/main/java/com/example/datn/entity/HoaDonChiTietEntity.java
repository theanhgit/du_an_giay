package com.example.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "HoaDonChiTiet")
@IdClass(HoaDonChiTietId.class)
public class HoaDonChiTietEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "hoaDon_id")
    private HoaDonEntity hoaDon;
    @Id
    @ManyToOne
    @JoinColumn(name = "sanPhamChitiet_id")
    private SanPhamChiTietEntity sanPhamChiTiet;
    @ManyToOne
    @JoinColumn(name  = "user_id")
    private UserEntity user;
    @Column(name = "soLuong", length = 10, nullable = false)
    private int soLuong;
    @Column(name = "thanhTien", length = 70, nullable = false)
    private BigDecimal thanhTien;
    @Column(name = "createDate")
    private LocalDate createDate;

    @Column(name = "updateDate")
    private LocalDate updateDate;
    // Constructors, getters, setters, and other methods
}
