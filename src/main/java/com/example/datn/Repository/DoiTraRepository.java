package com.example.datn.Repository;

import com.example.datn.entity.HoaDonEntity;
import com.example.datn.entity.SanPhamChiTietEntity;
import com.example.datn.entity.TraHangEntity;
import com.example.datn.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface DoiTraRepository  extends JpaRepository<TraHangEntity, UUID> {
    Optional<TraHangEntity> findByHoaDonAndSanPhamChiTiet(HoaDonEntity hoaDon, SanPhamChiTietEntity sanPhamChiTiet);

    @Modifying
    @Transactional
    @Query("UPDATE TraHangEntity sp SET sp.status = 1 WHERE sp.hoaDon.id = :id")
    void updateTrangThaiTraHang(@Param("id") UUID id);

}
