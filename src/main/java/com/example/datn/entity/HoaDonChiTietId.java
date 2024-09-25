    package com.example.datn.entity;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import javax.persistence.Embeddable;
    import java.io.Serializable;
    import java.util.Objects;
    import java.util.UUID;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public class HoaDonChiTietId implements Serializable {
        private UUID hoaDon;
        private UUID sanPhamChiTiet;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HoaDonChiTietId that = (HoaDonChiTietId) o;
            return hoaDon.equals(that.hoaDon) && sanPhamChiTiet.equals(that.sanPhamChiTiet);
        }

        @Override
        public int hashCode() {
            return Objects.hash(hoaDon, sanPhamChiTiet);
        }
    }