package com.example.datn.service.impl;

import com.example.datn.Repository.DiaChiRepository;
import com.example.datn.dto.DiaChiDTO;
import com.example.datn.entity.DiaChiEntity;
import com.example.datn.service.DiaChiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiaChiImpl implements DiaChiService {
    private final DiaChiRepository diaChiRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DiaChiDTO upDateDiaChiByIdUser(UUID userId, String diaChi, String xa, String huyen, String tinh) {
        diaChiRepository.updateDiaChiByUserId(userId, diaChi, xa, huyen, tinh);
        DiaChiEntity diaChiEntity = diaChiRepository.findByUserId(userId);
        return modelMapper.map(diaChiEntity, DiaChiDTO.class);
    }
}
