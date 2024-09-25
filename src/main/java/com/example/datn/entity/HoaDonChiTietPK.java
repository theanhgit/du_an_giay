package com.example.datn.entity;

import java.io.Serializable;
import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HoaDonChiTietPK implements Serializable {
    @Column(name = "hoa_don_id")
    private UUID hoaDonId;

    @Column(name = "san_pham_chitiet_id")
    private UUID sanPhamChiTietId;

    public HoaDonChiTietPK() {
    }

    public HoaDonChiTietPK(UUID hoaDonId, UUID sanPhamChiTietId) {
        this.hoaDonId = hoaDonId;
        this.sanPhamChiTietId = sanPhamChiTietId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HoaDonChiTietPK that = (HoaDonChiTietPK) o;

        if (!hoaDonId.equals(that.hoaDonId)) return false;
        return sanPhamChiTietId.equals(that.sanPhamChiTietId);
    }

    @Override
    public int hashCode() {
        int result = hoaDonId.hashCode();
        result = 31 * result + sanPhamChiTietId.hashCode();
        return result;
    }
}
