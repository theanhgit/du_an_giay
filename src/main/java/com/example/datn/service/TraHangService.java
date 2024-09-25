package com.example.datn.service;

import com.example.datn.dto.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TraHangService {
    Page<TraHangDTO> getAllTraHang(Integer totalPage, Integer totalItem);
    TraHangCRUD addTraHang(TraHangCRUD traHangCRUD);
    void updateStatus(UUID idHoaDon);
}
