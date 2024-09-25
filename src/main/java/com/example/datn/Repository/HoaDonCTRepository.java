package com.example.datn.Repository;

import com.example.datn.entity.HoaDonChiTietEntity;
import com.example.datn.entity.HoaDonChiTietId;
import com.example.datn.entity.HoaDonChiTietPK;
import com.example.datn.entity.HoaDonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HoaDonCTRepository extends JpaRepository<HoaDonChiTietEntity, HoaDonChiTietId> {
    List<HoaDonChiTietEntity> findByHoaDon_Id(UUID hoaDonId);
    @Query("SELECT COALESCE(SUM(ct.soLuong), 0) FROM HoaDonChiTietEntity ct WHERE ct.hoaDon.id = :hoaDonId AND ct.sanPhamChiTiet.id = :sanPhamChiTietId")
    int calculateTotalSoLuongByHoaDonIdAndSanPhamChiTietId(@Param("hoaDonId") UUID hoaDonId, @Param("sanPhamChiTietId") UUID sanPhamChiTietId);

    Optional<HoaDonChiTietEntity> findByHoaDonIdAndSanPhamChiTietId(UUID hoaDonId, UUID sanPhamCTid);

    List<HoaDonChiTietEntity> findByHoaDonId(UUID hoaDonId);

    void deleteByHoaDonId(UUID hoaDonId);

}
