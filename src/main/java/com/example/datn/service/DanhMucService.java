package com.example.datn.service;

import com.example.datn.dto.DanhMucDTO;

import java.util.List;

public interface DanhMucService {
    List<DanhMucDTO> getAllDanhMuc();
     DanhMucDTO addDanhMuc(DanhMucDTO dto);
     DanhMucDTO upDateDanhMuc(DanhMucDTO dto);
}
