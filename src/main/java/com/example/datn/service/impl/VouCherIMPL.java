package com.example.datn.service.impl;

import com.example.datn.Repository.SanPhamChiTietRepository;
import com.example.datn.Repository.VouCherRepository;
import com.example.datn.dto.GioHangChiTietDTO;
import com.example.datn.dto.VoucherDTO;
import com.example.datn.entity.GioHangChiTietEntity;
import com.example.datn.entity.VouCherEntity;
import com.example.datn.service.VouCherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VouCherIMPL implements VouCherService {
    private final VouCherRepository vouCherRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<VoucherDTO> getAllVoucher() {
        List<VouCherEntity> entityList = vouCherRepository.findAllActiveVouchers();
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, VoucherDTO.class))
                .collect( Collectors.toList());
    }
}
