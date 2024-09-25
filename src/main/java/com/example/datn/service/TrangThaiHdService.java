package com.example.datn.service;

import com.example.datn.dto.TrangThaiHoaDonDTO;

import java.util.List;
import java.util.UUID;

public interface TrangThaiHdService {
    TrangThaiHoaDonDTO findByID(UUID idTrangThaiHd);

    List<TrangThaiHoaDonDTO> FinByShipCod();
    List<TrangThaiHoaDonDTO> FinByOnline();
}
