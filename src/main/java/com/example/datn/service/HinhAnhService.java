package com.example.datn.service;

import com.example.datn.dto.HinhAnhDTO;


import java.util.List;

public interface HinhAnhService {
    List<HinhAnhDTO> getAllHinhAnh();
    HinhAnhDTO addHinhAnh(HinhAnhDTO hinhAnhDTO);
    HinhAnhDTO UpdateHinhAnh(HinhAnhDTO hinhAnhDTO);
}
