    package com.example.datn.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.*;

    import java.math.BigDecimal;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.UUID;
    @Data
    @Entity
    @Table(name = "SanPhamChiTiet")
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public class SanPhamChiTietEntity extends SuperEntity {

        @Column(name = "giaSanPham", length = 150, nullable = false)
        private BigDecimal giaSanPham;
        @Column(name = "soLuong", length = 50, nullable = false)
        private int soLuong;

        @Column(name = "trongLuong", length = 150, nullable = false, columnDefinition = "NVARCHAR(255)")
        private String trongLuong;
        @Column(name = "gioiTinh", length = 10, nullable = false, columnDefinition = "NVARCHAR(255)")
        private int gioiTinh;
        @Column(name = "moTa", length = 150, nullable = false, columnDefinition = "NVARCHAR(255)")
        private String moTa;
        @Transient
        private boolean daDoi = false;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sanpham_id")
        private SanPhamEntity sanPham;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "mauSac_id")
        private MauSacEntity mauSac;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "kichCo_id")
        private KichCoEntity kichCo;

        @ManyToOne
        @JoinColumn(name = "NXS_id")
        private NSXEntity nsx;
        @ManyToOne
        @JoinColumn(name = "chatLieu_id")
        private ChatLieuEntity chatLieu;
        @ManyToOne
        @JoinColumn(name = "hinhAnh_id")
        private HinhAnhEntity hinhAnh;
        @ManyToOne
        @JoinColumn(name = "danhMuc_id")
        private DanhMucEntity danhMuc;
        @ManyToOne
        @JoinColumn(name = "baoHanh_id")
        private BaoHanhEntity baoHanh;
        @Column(name = "trangThai", length = 10, nullable = true)
        private int trangThai;
        @JsonIgnore
        @OneToMany(mappedBy = "sanPhamChiTiet")
        private List<GioHangChiTietEntity> gioHangChiTietEntities = new ArrayList<GioHangChiTietEntity>();
        @JsonIgnore
        @OneToMany(mappedBy = "sanPhamChiTiet")
        private List<HoaDonChiTietEntity> hoaDonChiTiets = new ArrayList<HoaDonChiTietEntity>();
        @JsonIgnore
        @OneToMany(mappedBy = "sanPhamChiTiet")
        private List<TraHangEntity> traHangs = new ArrayList<TraHangEntity>();


        @Override
        public String toString() {
            return "SanPhamChiTietEntity{" +
                    "giaSanPham=" + giaSanPham +
                    ", soLuong=" + soLuong +
                    ", trongLuong='" + trongLuong + '\'' +
                    ", gioiTinh=" + gioiTinh +
                    ", moTa='" + moTa + '\'' +
                    ", sanPham=" + sanPham +
                    ", mauSac=" + mauSac +
                    ", kichCo=" + kichCo +
                    ", nsx=" + nsx +
                    ", chatLieu=" + chatLieu +
                    ", hinhAnh=" + hinhAnh +
                    ", danhMuc=" + danhMuc +
                    ", baoHanh=" + baoHanh +
                    ", trangThai=" + trangThai +
                    ", gioHangChiTietEntities=" + gioHangChiTietEntities +
                    ", hoaDonChiTiets=" + hoaDonChiTiets +
                    ", traHangs=" + traHangs +
                    '}';
        }
    }
