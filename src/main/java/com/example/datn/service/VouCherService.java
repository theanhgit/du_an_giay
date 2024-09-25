package com.example.datn.service;

import com.example.datn.dto.GioHangChiTietDTO;
import com.example.datn.dto.VoucherDTO;

import java.util.List;
import java.util.UUID;

public interface VouCherService {
    List<VoucherDTO> getAllVoucher();
}
