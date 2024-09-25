package com.example.datn.service.impl;

import com.example.datn.Repository.KichCoRepository;
import com.example.datn.Repository.MauSacRepository;
import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.entity.KichCoEntity;
import com.example.datn.entity.MauSacEntity;
import com.example.datn.service.KichCoService;
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
public class KichCoIMPL implements KichCoService {
    private final KichCoRepository kichCoRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<KichCoDTO> getAllKichCo() {
        List<KichCoEntity>kichCoEntities = kichCoRepository.findAll();
        return kichCoEntities.stream()
                .map(entity -> modelMapper.map(entity, KichCoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public KichCoDTO addKichCo(KichCoDTO kichCoDTO) {
        KichCoEntity kichCo = KichCoEntity.builder()
                .tenKichCo(kichCoDTO.getTenKichCo())
                .doDai(kichCoDTO.getDoDai())
                .build();
        kichCo.setCreateDate(LocalDate.now());
        kichCo.setUpdateDate(LocalDateTime.now());
        kichCoRepository.save(kichCo);

        return modelMapper.map(kichCo,KichCoDTO.class);

    }

    @Override
    public KichCoDTO updateKichCo(KichCoDTO kichCoDTO) {
        Optional<KichCoEntity> kichCo = kichCoRepository.findById(kichCoDTO.getId());
        KichCoEntity kichCo1 = new KichCoEntity();
        kichCo1.setId(kichCo.get().getId());
        kichCo1.setTenKichCo(kichCoDTO.getTenKichCo());
        kichCo1.setDoDai(kichCoDTO.getDoDai());
        kichCo1.setCreateDate(LocalDate.now());
        kichCo1.setUpdateDate(LocalDateTime.now());
        kichCoRepository.save(kichCo1);
        return modelMapper.map(kichCo1,KichCoDTO.class);
    }
}
