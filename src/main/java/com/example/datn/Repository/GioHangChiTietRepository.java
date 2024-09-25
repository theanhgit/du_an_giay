package com.example.datn.Repository;

import com.example.datn.entity.GioHangChiTietEntity;
import com.example.datn.entity.SanPhamChiTietEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTietEntity, SanPhamChiTietEntity> {
    @Query("SELECT g FROM GioHangChiTietEntity g WHERE g.gioHang.id = :gioHangId")
    List<GioHangChiTietEntity> findByGioHangId(@Param("gioHangId") UUID gioHangId);
    @Modifying
    @Transactional
    @Query("DELETE FROM GioHangChiTietEntity c WHERE c.sanPhamChiTiet.id = :id")
    void deleteById(UUID id);

    @Modifying
    @Transactional
    @Query("DELETE FROM GioHangChiTietEntity c WHERE c.gioHang.id = :id")
    void deleteByIdGH(UUID id);
}
