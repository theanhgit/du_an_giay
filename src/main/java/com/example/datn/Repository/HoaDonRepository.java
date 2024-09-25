package com.example.datn.Repository;

import com.example.datn.dto.TrangThaiHoaDonDTO;
import com.example.datn.entity.HoaDonChiTietEntity;
import com.example.datn.entity.HoaDonEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HoaDonRepository extends JpaRepository<HoaDonEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE HoaDonEntity h SET h.tongTien = h.tongTien - :tongTra WHERE h.id = :id")
    void updateTongTien(@Param("id") UUID id, @Param("tongTra") BigDecimal tongTra);
    @Query("SELECT g FROM HoaDonEntity g WHERE g.user.id = :userId AND g.trangThaiHD.trangThai IN ('2','3','5') ORDER BY g.createDate DESC")
    List<HoaDonEntity> findByHoaByIdUser(@Param("userId") UUID userId);
    @Query("SELECT g FROM HoaDonEntity g WHERE g.user.id = :userId AND g.trangThaiHD.trangThai IN ('1') ORDER BY g.createDate DESC")
    List<HoaDonEntity> findByHoaByIdUserTC(@Param("userId") UUID userId);
    @Query("SELECT g FROM HoaDonEntity g WHERE g.user.id = :userId AND g.trangThaiHD.trangThai IN ('4') ORDER BY g.createDate DESC")
    List<HoaDonEntity> findByHoaByIdUserHuy(@Param("userId") UUID userId);

    @Query("SELECT new com.example.datn.dto.TrangThaiHoaDonDTO(hd.id, tt.trangThai) " +
            "FROM HoaDonEntity hd " +
            "JOIN hd.trangThaiHD tt " +
            "WHERE hd.id = :hoaDonId")
    TrangThaiHoaDonDTO findTrangThaiByHoaDonId(@Param("hoaDonId") UUID hoaDonId);
    @Query("SELECT h FROM HoaDonEntity h JOIN h.trangThaiHD t WHERE t.trangThai IN ('2', '3') ORDER BY h.ngayThanhToan DESC")
    Page<HoaDonEntity> findHoaDonsByTrangThai(Pageable pageable);

    @Query("SELECT h FROM HoaDonEntity h JOIN h.trangThaiHD t WHERE t.trangThai = '4' ORDER BY h.ngayThanhToan DESC")
    Page<HoaDonEntity> findTTByHoaDon(Pageable pageable);

    @Query("SELECT h FROM HoaDonEntity h JOIN h.trangThaiHD t WHERE t.trangThai = '1' ORDER BY h.ngayThanhToan DESC")
    Page<HoaDonEntity> findHDBYTT(Pageable pageable);

    @Query("SELECT h FROM HoaDonEntity h JOIN h.trangThaiHD t WHERE t.trangThai = '0' ORDER BY h.ngayThanhToan DESC")
    Page<HoaDonEntity> findHDChuaThanhToan(Pageable pageable);

    @Query("SELECT h FROM HoaDonEntity h JOIN h.trangThaiHD t WHERE t.trangThai = '5' ORDER BY h.ngayThanhToan DESC")
    Page<HoaDonEntity> findDangGiao(Pageable pageable);

    @Modifying
    @Query("UPDATE HoaDonEntity h SET h.trangThaiHD = (SELECT t FROM TrangThaiHDEntity t WHERE t.trangThai = '4') WHERE h.id = :idHoaDon")
    void updateTrangThaiHd(@Param("idHoaDon") UUID idHoaDon);

    }


