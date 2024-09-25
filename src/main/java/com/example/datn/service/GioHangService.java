package com.example.datn.service;

import com.example.datn.dto.GioHangDTO;
import com.example.datn.dto.SanPhamChiTietDTO;

import java.util.UUID;

public interface GioHangService {
    GioHangDTO findByIdUser(UUID idUser);

}
