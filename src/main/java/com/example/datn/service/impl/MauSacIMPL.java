package com.example.datn.service.impl;

import com.example.datn.Repository.DanhMucRepository;
import com.example.datn.Repository.MauSacRepository;
import com.example.datn.dto.DanhMucDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.entity.DanhMucEntity;
import com.example.datn.entity.MauSacEntity;
import com.example.datn.service.MauSacService;
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
public class MauSacIMPL implements MauSacService {

    private final MauSacRepository mauSacRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MauSacDTO> getAllMauSac() {
        List<MauSacEntity>mauSacEntities = mauSacRepository.findAll();
        return mauSacEntities.stream()
                .map(entity -> modelMapper.map(entity, MauSacDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MauSacDTO addMauSac(MauSacDTO mauSacDTO) {
        MauSacEntity mauSac = MauSacEntity.builder()
                .ten(mauSacDTO.getTen())
                .build();
        mauSac.setCreateDate(LocalDate.now());
        mauSac.setUpdateDate(LocalDateTime.now());
        mauSacRepository.save(mauSac);

        return modelMapper.map(mauSac,MauSacDTO.class);
    }

    @Override
    public MauSacDTO updateMauSac(MauSacDTO mauSacDTO) {
        Optional<MauSacEntity> mauSac = mauSacRepository.findById(mauSacDTO.getId());
        MauSacEntity mauSac1 = new MauSacEntity();
        mauSac1.setId(mauSac.get().getId());
        mauSac1.setTen(mauSacDTO.getTen());
        mauSac1.setCreateDate(LocalDate.now());
        mauSac1.setUpdateDate(LocalDateTime.now());
        mauSacRepository.save(mauSac1);
        return modelMapper.map(mauSac1,MauSacDTO.class);
    }
}
