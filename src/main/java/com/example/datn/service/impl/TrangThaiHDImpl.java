package com.example.datn.service.impl;

import com.example.datn.Repository.TrangThaiHDRepository;
import com.example.datn.dto.TrangThaiHoaDonDTO;
import com.example.datn.entity.TrangThaiHDEntity;
import com.example.datn.service.TrangThaiHdService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrangThaiHDImpl implements TrangThaiHdService {
    private final TrangThaiHDRepository trangThaiHDRepository;
    private final ModelMapper modelMapper;
    @Override
    public TrangThaiHoaDonDTO findByID(UUID idTrangThaiHd) {

        return null;
    }

    @Override
    public List<TrangThaiHoaDonDTO> FinByShipCod() {
         List<TrangThaiHDEntity>finByShipCod = trangThaiHDRepository.FinByShipCode();
        return finByShipCod.stream()
                .map(entity -> modelMapper.map(entity, TrangThaiHoaDonDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TrangThaiHoaDonDTO> FinByOnline() {

        List<TrangThaiHDEntity>finByOnline = trangThaiHDRepository.FinByOnline();
        return finByOnline.stream()
                .map(entity -> modelMapper.map(entity, TrangThaiHoaDonDTO.class))
                .collect(Collectors.toList());
    }
}
