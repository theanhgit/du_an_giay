package com.example.datn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DoiTra")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraHangEntity extends SuperEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HoaDon_id", nullable = false)
    private HoaDonEntity hoaDon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SanPhamCT_id", nullable = false)
    private SanPhamChiTietEntity sanPhamChiTiet;

    @Column(name = "type", length = 15, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String type; // "doi" hoặc "tra"

    @Column(name = "lyDo", length = 150, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String lyDo; // Lý do đổi/trả hàng
    @Column(name = "soLuong", length = 10, nullable = false)
    private int soLuong; // Lý do đổi/trả hàng

    @Column(name = "status", length = 10, nullable = false)
    private int status; // Trạng thái yêu cầu: "pending", "approved", "rejected"


}
