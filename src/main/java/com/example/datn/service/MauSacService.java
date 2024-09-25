package com.example.datn.service;

import com.example.datn.dto.DanhMucDTO;
import com.example.datn.dto.MauSacDTO;

import java.util.List;

public interface MauSacService {
    List<MauSacDTO> getAllMauSac();
    MauSacDTO addMauSac(MauSacDTO mauSacDTO);
    MauSacDTO updateMauSac(MauSacDTO mauSacDTO);
}
