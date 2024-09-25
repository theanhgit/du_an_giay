    package com.example.datn.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "hoa_don")
    public class HoaDonEntity extends SuperEntity {
        @ManyToOne
        @JoinColumn(name  = "user_id")
        private UserEntity user;
        @Transient
        private boolean conTrongThoiHanDoi;
        public boolean isConTrongThoiHanDoi() {
            return conTrongThoiHanDoi;
        }

        public void setConTrongThoiHanDoi(boolean conTrongThoiHanDoi) {
            this.conTrongThoiHanDoi = conTrongThoiHanDoi;
        }
        @ManyToOne
        @JoinColumn(name  = "trangThaiHD_id")
        private TrangThaiHDEntity trangThaiHD;
        @Column(name = "thanhTien", length = 20, nullable = false)
        private BigDecimal tongTien;
        @Column(name = "ngayThanhToan", length = 120, nullable = true)
            private LocalDate ngayThanhToan;
        @ManyToOne
        @JoinColumn(name  = "vouCher_id")
        private VouCherEntity vouCher;
        @Column(name = "ghiChu", columnDefinition = "NVARCHAR(500)")
        private String ghiChu;
        @JsonIgnore
        @OneToMany(mappedBy = "hoaDon")
        private List<HoaDonChiTietEntity> hoaDonChiTiets = new ArrayList<HoaDonChiTietEntity>();
        @JsonIgnore
        @OneToMany(mappedBy = "hoaDon")
        private List<TraHangEntity> traHangs = new ArrayList<TraHangEntity>();
    }
