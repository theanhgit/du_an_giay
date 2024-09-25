package com.example.datn.service;


import com.example.datn.dto.HoaDonCHiTietCrud;
import com.example.datn.dto.HoaDonChiTietDTO;
import com.example.datn.dto.HoaDonDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {
   HoaDonCHiTietCrud addHoaDonCT(UUID idUser, UUID idVoucher, UUID idTrangThaiHD, BigDecimal tongTien);
   List<HoaDonChiTietDTO> getALlHoaDonCTByIdHoaDon(UUID hoaDon);
   List<HoaDonDTO> getAllHoaDonByIdUser(UUID idUser);
   List<HoaDonDTO> getAllHoaDonByIdUserTC(UUID idUser);
   List<HoaDonDTO> getAllHoaDonByIdUserHuy(UUID idUser);
   void updateTrangThaiHD(UUID idTrangThaiHD);
   boolean checkStockAvailability(UUID sanPhamChiTietId, int soLuongYeuCau);
   void deleteHoaDonCT(UUID id,UUID idHoaDon,BigDecimal tongTra,int soLuong);
   void updateHDCTByidHdSP(UUID newIdSpct,UUID idHoaDOn,UUID idSpCurent,int soLuong);
}
