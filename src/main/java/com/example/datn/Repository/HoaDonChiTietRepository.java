package com.example.datn.Repository;

import com.example.datn.entity.GioHangChiTietEntity;
import com.example.datn.entity.HoaDonChiTietEntity;
import com.example.datn.entity.HoaDonEntity;
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
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTietEntity, SanPhamChiTietEntity> {
    @Query("SELECT g FROM HoaDonChiTietEntity g WHERE g.hoaDon.id = :idHoaDon")
    List<HoaDonChiTietEntity>  findByHoaDonChiTietByIdHoaDon(@Param("idHoaDon") UUID idHoaDon);
    @Modifying
    @Transactional
    @Query("DELETE FROM HoaDonChiTietEntity c WHERE c.sanPhamChiTiet.id = :id")
    void deleteById(UUID id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE hoa_don_chi_tiet SET san_pham_chitiet_id = :newIdSpct WHERE hoa_don_id = :idHoaDon AND san_pham_chitiet_id = :idSpctCuren", nativeQuery = true)
    void updateHoaDonCT(@Param("newIdSpct") UUID newIdSpct, @Param("idHoaDon") UUID idHoaDon, @Param("idSpctCuren") UUID idSpctCuren);







}
