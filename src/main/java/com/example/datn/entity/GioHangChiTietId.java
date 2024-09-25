package com.example.datn.entity;


import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class GioHangChiTietId implements Serializable {

    private UUID gioHang;
    private UUID sanPhamChiTiet;

    public GioHangChiTietId() {}

    public GioHangChiTietId(UUID gioHang, UUID sanPhamChiTiet) {
        this.gioHang = gioHang;
        this.sanPhamChiTiet = sanPhamChiTiet;
    }

    // Getter và Setter cho gioHang
    public UUID getGioHang() {
        return gioHang;
    }

    public void setGioHang(UUID gioHang) {
        this.gioHang = gioHang;
    }

    // Getter và Setter cho sanPhamChiTiet
    public UUID getSanPhamChiTiet() {
        return sanPhamChiTiet;
    }

    public void setSanPhamChiTiet(UUID sanPhamChiTiet) {
        this.sanPhamChiTiet = sanPhamChiTiet;
    }

    // Override equals và hashCode để so sánh đối tượng GioHangChiTietId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GioHangChiTietId that = (GioHangChiTietId) o;
        return gioHang.equals(that.gioHang) && sanPhamChiTiet.equals(that.sanPhamChiTiet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gioHang, sanPhamChiTiet);
    }
}
