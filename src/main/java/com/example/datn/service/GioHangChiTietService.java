package com.example.datn.service;

import com.example.datn.dto.GioHangChiTietCrud;
import com.example.datn.dto.GioHangChiTietDTO;
import java.util.List;
import java.util.UUID;

public interface GioHangChiTietService {
    List <GioHangChiTietDTO> getALLGioHangChiTiet(UUID idUser);
    GioHangChiTietCrud addGioHangCT(GioHangChiTietCrud gioHangChiTietDTO);
   void deleteGioHangCT(UUID id);
     boolean checkStockAvailability(UUID sanPhamChiTietId, int soLuongYeuCau);
}
