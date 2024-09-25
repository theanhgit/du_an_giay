package com.example.datn.service;

import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.MauSacDTO;

import java.util.List;

public interface KichCoService {
    List<KichCoDTO> getAllKichCo();
    KichCoDTO addKichCo(KichCoDTO kichCoDTO);
    KichCoDTO updateKichCo(KichCoDTO kichCoDTO);
}
