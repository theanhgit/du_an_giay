package com.example.datn.service.impl;

import com.example.datn.Repository.SanPhamChiTietRepository;
import com.example.datn.Repository.SanPhamRepository;
import com.example.datn.dto.SanPhamChiTietDTO;
import com.example.datn.dto.SanPhamDTO;
import com.example.datn.dto.SanPhamFiterDTO;
import com.example.datn.entity.SanPhamChiTietEntity;
import com.example.datn.entity.SanPhamEntity;
import com.example.datn.service.SanPhamService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class SanPhamIMPL implements SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<SanPhamDTO> getAllSanPham(Integer totalPage, Integer totalItem, SanPhamFiterDTO form) {
        Pageable pageable = PageRequest.of(totalPage, totalItem);

        // Xây dựng Specification từ form (SanPhamChiTietFiterDTO)
        Specification<SanPhamEntity> specification = SpectificationSanPham.buildWhere(form);

        // Kiểm tra nếu không có Specification hoặc Specification là null
        if (specification == null) {
            List<SanPhamDTO> emptyList = Collections.emptyList();
            return new PageImpl<>(emptyList, pageable, 0);
        }


        Page<SanPhamEntity> entityPage = sanPhamRepository.findAll(specification,pageable);

        // Convert Page<SanPhamChiTietEntity> sang Page<SanPhamChiTietDTO>
        List<SanPhamDTO> dtos = entityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, SanPhamDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public SanPhamDTO addSanPham(SanPhamDTO sanPhamDTO) {
        SanPhamEntity sanPhamEntity = SanPhamEntity.builder()
                .tenSanPham(sanPhamDTO.getTenSanPham())
                .trangThai(sanPhamDTO.getTrangThai()).build();
        sanPhamEntity.setCreateDate(LocalDate.now());
        sanPhamEntity.setUpdateDate(LocalDateTime.now());
        sanPhamRepository.save(sanPhamEntity);
        return modelMapper.map(sanPhamEntity, SanPhamDTO.class);

    }

    @Override
    public SanPhamDTO upDateSanPham(SanPhamDTO sanPhamDTO) {
        Optional<SanPhamEntity> optionalSanPham = sanPhamRepository.findById(sanPhamDTO.getId());
        if (optionalSanPham.isPresent()) {
            SanPhamEntity sanPhamEntity = optionalSanPham.get();
            sanPhamEntity.setTenSanPham(sanPhamDTO.getTenSanPham());
            sanPhamEntity.setTrangThai(sanPhamDTO.getTrangThai());
            sanPhamRepository.save(sanPhamEntity);

            return modelMapper.map(sanPhamEntity, SanPhamDTO.class);
        } else {
            throw new EntityNotFoundException("Không tìm thấy sản phẩm với id: " + sanPhamDTO.getId());
        }
    }

}
