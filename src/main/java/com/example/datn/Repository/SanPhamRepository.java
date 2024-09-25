package com.example.datn.Repository;

import com.example.datn.entity.SanPhamEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface SanPhamRepository extends JpaRepository<SanPhamEntity, UUID>, JpaSpecificationExecutor<SanPhamEntity> {
    @Query("SELECT sp FROM SanPhamEntity sp WHERE sp.trangThai = :trangThai")
    List<SanPhamEntity> findByTrangThai(@Param("trangThai") String trangThai, Pageable pageable);
    @Query("SELECT hd.trangThaiHD.ten FROM HoaDonEntity hd WHERE hd.id = :hoaDonId")
    Integer findTrangThaiById(@PathVariable("hoaDonId") UUID hoaDonId);

    @Query(value = "WITH Months AS (\n" +
            "    SELECT 1 AS MonthNumber, N'Tháng1' AS MonthName\n" +
            "    UNION ALL SELECT 2, N'Tháng2'\n" +
            "    UNION ALL SELECT 3, N'Tháng3'\n" +
            "    UNION ALL SELECT 4, N'Tháng4'\n" +
            "    UNION ALL SELECT 5, N'Tháng5'\n" +
            "    UNION ALL SELECT 6, N'Tháng6'\n" +
            "    UNION ALL SELECT 7, N'Tháng7'\n" +
            "    UNION ALL SELECT 8, N'Tháng8'\n" +
            "    UNION ALL SELECT 9, N'Tháng9'\n" +
            "    UNION ALL SELECT 10, N'Tháng10'\n" +
            "    UNION ALL SELECT 11, N'Tháng11'\n" +
            "    UNION ALL SELECT 12, N'Tháng12'\n" +
            ")\n" +
            "SELECT \n" +
            "    m.MonthName,\n" +
            "    ISNULL(SUM(hd.thanh_tien), 0) AS totalSales\n" +
            "FROM \n" +
            "    Months m\n" +
            "LEFT JOIN \n" +
            "    hoa_don hd ON MONTH(hd.ngay_thanh_toan) = m.MonthNumber\n" +
            "LEFT JOIN \n" +
            "   trang_thaihd tthd ON hd.trang_thaihd_id = tthd.id\n" +
            "WHERE \n" +
            "    YEAR(hd.ngay_thanh_toan) = YEAR(GETDATE()) \n" + // Removed the comment here
            "    AND hd.ngay_thanh_toan >= DATEADD(MONTH, -12, GETDATE()) \n" + // Removed the comment here
            "    AND tthd.trang_thai IN ('1','3') -- Chỉ tính hóa đơn đã hoàn thành và hóa đơn đã thanh toán online\n" + // The comment is fine here
            "GROUP BY \n" +
            "    m.MonthName, \n" +
            "    m.MonthNumber\n" +
            "ORDER BY \n" +
            "    m.MonthNumber;\n", nativeQuery = true)
    List<Object[]> thongKeTheoThang();

//    List<Object[]> thongKeTheoThang();
//@Query(value = "WITH Months AS (\n" +
//        "    SELECT 1 AS MonthNumber, N'Tháng1' AS MonthName\n" +
//        "    UNION ALL SELECT 2, N'Tháng2'\n" +
//        "    UNION ALL SELECT 3,N'Tháng3'\n" +
//        "    UNION ALL SELECT 4, N'Tháng4'\n" +
//        "    UNION ALL SELECT 5, N'Tháng5'\n" +
//        "    UNION ALL SELECT 6, N'Tháng6'\n" +
//        "    UNION ALL SELECT 7, N'Tháng7'\n" +
//        "    UNION ALL SELECT 8, N'Tháng8'\n" +
//        "    UNION ALL SELECT 9,N'Tháng9'\n" +
//        "    UNION ALL SELECT 10, N'Tháng10'\n" +
//        "    UNION ALL SELECT 11, N'Tháng11'\n" +
//        "    UNION ALL SELECT 12, N'Tháng12'\n" +
//        ")\n" +
//        "SELECT \n" +
//        "    m.MonthName,\n" +
//        "    ISNULL(SUM(hd.thanh_tien), 0) AS totalSales\n" +
//        "FROM \n" +
//        "    Months m\n" +
//        "LEFT JOIN \n" +
//        "    hoa_don hd ON MONTH(hd.ngay_thanh_toan) = m.MonthNumber\n" +
//        "LEFT JOIN \n" +
//        "    hoa_don_chi_tiet hdct ON hd.id = hdct.hoa_don_id\n" +
//        "LEFT JOIN \n" +
//        "   trang_thaihd tthd ON hd.trang_thaihd_id = tthd.id\n" +
//        "WHERE \n" +
//        "    YEAR(hd.ngay_thanh_toan) = YEAR(GETDATE()) -- Thay đổi năm nếu cần\n" +
//        "    AND hd.ngay_thanh_toan >= DATEADD(MONTH, -12, GETDATE()) -- Lọc trong 12 tháng gần nhất\n" +
//        "    AND tthd.trang_thai IN ('1','2','3','5') -- Không tính các hóa đơn có trạng thái hủy\n" +
//        "GROUP BY \n" +
//        "    m.MonthName, \n" +
//        "    m.MonthNumber\n" +
//        "ORDER BY \n" +
//        "    m.MonthNumber;\n", nativeQuery = true)
//List<Object[]> thongKeTheoThang();


    @Query(value = """
        SELECT TOP 5
            MIN(ha.duong_dan) AS hinh_anh_duong_dan,
            sp.ten_san_pham,
            SUM(hdct.so_luong) AS soLuongDaBan
        FROM
            san_pham sp
        JOIN
            san_pham_chi_tiet spct ON sp.id = spct.sanpham_id
        JOIN
            hoa_don_chi_tiet hdct ON spct.id = hdct.san_pham_chitiet_id
        JOIN
            hinh_anh ha ON spct.hinh_anh_id = ha.id
        GROUP BY
            sp.ten_san_pham
        ORDER BY
            soLuongDaBan DESC
    """, nativeQuery = true)
    List<Object[]> sanPhamBanChay();

    @Query("SELECT "
            + "sp2.tenSanPham AS tenSanpham, "
            + "kc.tenKichCo AS kichCo, "
            + "ms.ten AS mauSac, "
            + "SUM(hdc.soLuong) AS tongSoLuong, "
            + "CAST(hd.ngayThanhToan AS DATE) AS ngay "
            + "FROM HoaDonChiTietEntity hdc "
            + "JOIN hdc.sanPhamChiTiet sp "
            + "JOIN sp.sanPham sp2 "
            + "JOIN sp.kichCo kc "
            + "JOIN sp.mauSac ms "
            + "JOIN hdc.hoaDon hd "
            + "JOIN hd.trangThaiHD tthd "
            + "WHERE hd.ngayThanhToan BETWEEN :startDate AND :endDate "
            + "AND tthd.trangThai = :trangThai "
            + "GROUP BY sp2.tenSanPham, kc.tenKichCo, ms.ten, CAST(hd.ngayThanhToan AS DATE) "
            + "ORDER BY ngay, tenSanpham")
    List<Object[]> findSalesData(@Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate,
                                 @Param("trangThai") int trangThai);
}
