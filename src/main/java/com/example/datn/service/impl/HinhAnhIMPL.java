package com.example.datn.service.impl;

import com.example.datn.Repository.HinhAnhRepository;
import com.example.datn.dto.HinhAnhDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.entity.HinhAnhEntity;
import com.example.datn.entity.MauSacEntity;
import com.example.datn.service.HinhAnhService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HinhAnhIMPL implements HinhAnhService {
private final HinhAnhRepository hinhAnhRepository;
private final ModelMapper modelMapper;
    @Override
    public List<HinhAnhDTO> getAllHinhAnh() {
        List<HinhAnhEntity>hinhAnhEntities = hinhAnhRepository.findAll();
        return hinhAnhEntities.stream()
                .map(entity -> modelMapper.map(entity, HinhAnhDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HinhAnhDTO addHinhAnh(HinhAnhDTO hinhAnhDTO) {
        HinhAnhEntity hinhAnh = HinhAnhEntity.builder()
                .duongDan(hinhAnhDTO.getDuongDan())
                .build();
        hinhAnh.setTen("Adidas");
        hinhAnh.setCreateDate(LocalDate.now());
        hinhAnh.setUpdateDate(LocalDateTime.now());
        hinhAnh.setTrangThai(1);
        hinhAnhRepository.save(hinhAnh);
        return modelMapper.map(hinhAnh,HinhAnhDTO.class);
    }

    @Override
    public HinhAnhDTO UpdateHinhAnh(HinhAnhDTO hinhAnhDTO) {
        // Tìm kiếm HinhAnhEntity bằng ID
        Optional<HinhAnhEntity> hinhAnhOptional = hinhAnhRepository.findById(hinhAnhDTO.getId());

        if (!hinhAnhOptional.isPresent()) {
            // Xử lý khi không tìm thấy HinhAnhEntity với ID tương ứng
            throw new EntityNotFoundException("Không tìm thấy hình ảnh với ID: " + hinhAnhDTO.getId());
        }

        HinhAnhEntity hinhAnh = hinhAnhOptional.get();

        // Cập nhật thông tin của HinhAnhEntity
        hinhAnh.setId(hinhAnh.getId());
        hinhAnh.setDuongDan(hinhAnhDTO.getDuongDan());
        hinhAnh.setTen("Adidas");
        hinhAnh.setCreateDate(LocalDate.now()); // Có thể không cần cập nhật ngày tạo
        hinhAnh.setUpdateDate(LocalDateTime.now());
        hinhAnh.setTrangThai(1);

        // Lưu HinhAnhEntity đã cập nhật vào cơ sở dữ liệu
        hinhAnhRepository.save(hinhAnh);

        // Map từ HinhAnhEntity sang HinhAnhDTO và trả về kết quả
        return modelMapper.map(hinhAnh, HinhAnhDTO.class);
    }

}
