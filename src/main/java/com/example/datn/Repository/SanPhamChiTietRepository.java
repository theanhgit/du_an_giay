package com.example.datn.Repository;

import com.example.datn.entity.SanPhamChiTietEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTietEntity, UUID>, JpaSpecificationExecutor<SanPhamChiTietEntity> {
    @Query("SELECT spct FROM SanPhamChiTietEntity spct LEFT JOIN spct.sanPham sp WHERE sp.tenSanPham LIKE %:nameProduct% OR :nameProduct IS NULL")
    Page<SanPhamChiTietEntity> findByProductName(@Param("nameProduct") String nameProduct, Pageable pageable);
    ;
    @Query("SELECT s FROM SanPhamChiTietEntity s ORDER BY function('RAND')")
    List<SanPhamChiTietEntity> findTop4SanPhamChiTiet(Pageable pageable);

//    @Query("SELECT spct FROM SanPhamChiTietEntity spct " +
//            "JOIN spct.sanPham sp " +
//            "WHERE sp.tenSanPham = :tenSanPham")
//    List<SanPhamChiTietEntity> findByTenSanPham(@Param("tenSanPham") String tenSanPham);
//@Query("SELECT spct FROM SanPhamChiTietEntity spct " +
//        "JOIN spct.sanPham sp " +
//        "WHERE sp.tenSanPham = :tenSanPham AND spct.id <> :idSanPham")
//List<SanPhamChiTietEntity> findByTenSanPhamAndNotId(@Param("tenSanPham") String tenSanPham, @Param("idSanPham") UUID idSanPham);
@Query("SELECT spct, SUM(hdct.soLuong) AS soLuongDaMua FROM SanPhamChiTietEntity spct " +
        "JOIN spct.sanPham sp " +
        "LEFT JOIN HoaDonChiTietEntity hdct ON hdct.sanPhamChiTiet = spct " +
        "WHERE sp.tenSanPham = :tenSanPham AND spct.id <> :idSanPham " +
        "GROUP BY spct")
List<Object[]> findByTenSanPhamAndNotIdWithSoLuong(@Param("tenSanPham") String tenSanPham, @Param("idSanPham") UUID idSanPham);


    @Modifying
    @Transactional
    @Query("UPDATE SanPhamChiTietEntity sp SET sp.soLuong = sp.soLuong - :soLuong WHERE sp.id = :id")
    void updateSoLuong(UUID id, int soLuong);
    @Modifying
    @Transactional
    @Query("UPDATE SanPhamChiTietEntity sp SET sp.soLuong = sp.soLuong + :soLuong WHERE sp.id = :id")
    void updateSoLuongCong(UUID id, int soLuong);
    @Query("SELECT spct FROM SanPhamChiTietEntity spct LEFT JOIN spct.sanPham sp WHERE sp.id = :idSp AND spct.trangThai = :trangThai")
    Page<SanPhamChiTietEntity> findAllSpChiTietById(@Param("idSp") UUID idSp, @Param("trangThai") Integer trangThai, Pageable pageable);

    @Query("SELECT s FROM SanPhamChiTietEntity s WHERE s.sanPham.id = :idSP")
    List<SanPhamChiTietEntity> findByIdSP(@Param("idSP") UUID idSP);

}
