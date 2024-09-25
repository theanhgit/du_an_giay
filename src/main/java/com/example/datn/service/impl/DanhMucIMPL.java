package com.example.datn.service.impl;

import com.example.datn.Repository.DanhMucRepository;
import com.example.datn.dto.DanhMucDTO;
import com.example.datn.entity.DanhMucEntity;
import com.example.datn.service.DanhMucService;
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
public class DanhMucIMPL implements DanhMucService {
    private final DanhMucRepository danhMucRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DanhMucDTO> getAllDanhMuc() {
        List<DanhMucEntity>danhMucEntities = danhMucRepository.findAll();
        return danhMucEntities.stream()
                .map(entity -> modelMapper.map(entity, DanhMucDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DanhMucDTO addDanhMuc(DanhMucDTO dto) {
        DanhMucEntity danhMuc = DanhMucEntity.builder()
                .tenDanhMuc(dto.getTenDanhMuc())
                .build();
        danhMuc.setCreateDate(LocalDate.now());
        danhMuc.setUpdateDate(LocalDateTime.now());
        danhMucRepository.save(danhMuc);

        return modelMapper.map(danhMuc,DanhMucDTO.class);
    }

    @Override
    public DanhMucDTO upDateDanhMuc(DanhMucDTO dto) {
        Optional<DanhMucEntity> danhMuc = danhMucRepository.findById(dto.getId());
        DanhMucEntity danhMuc1 = new DanhMucEntity();
        danhMuc1.setId(danhMuc.get().getId());
        danhMuc1.setTenDanhMuc(dto.getTenDanhMuc());
        danhMuc1.setCreateDate(LocalDate.now());
        danhMuc1.setUpdateDate(LocalDateTime.now());
        danhMucRepository.save(danhMuc1);
        return modelMapper.map(danhMuc1,DanhMucDTO.class);
    }
}
