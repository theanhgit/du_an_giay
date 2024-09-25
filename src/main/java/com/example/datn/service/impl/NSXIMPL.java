package com.example.datn.service.impl;

import com.example.datn.Repository.KichCoRepository;
import com.example.datn.Repository.NSXRepository;
import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.NSXDTO;
import com.example.datn.entity.KichCoEntity;
import com.example.datn.entity.NSXEntity;
import com.example.datn.service.NSXService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NSXIMPL implements NSXService {
    private final NSXRepository nsxRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<NSXDTO> getAllNSX() {
        List<NSXEntity>nsxEntities = nsxRepository.findAll();
        return nsxEntities.stream()
                .map(entity -> modelMapper.map(entity, NSXDTO.class))
                .collect(Collectors.toList());
    }
}
