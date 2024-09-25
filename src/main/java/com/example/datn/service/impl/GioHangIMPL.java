package com.example.datn.service.impl;



import com.example.datn.Repository.GioHangRepository;
import com.example.datn.Repository.UsersRepository;
import com.example.datn.dto.GioHangDTO;

import com.example.datn.entity.GioHangEntity;

import com.example.datn.entity.UserEntity;
import com.example.datn.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class GioHangIMPL implements GioHangService {
    private final GioHangRepository gioHangRepository;
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    @Override
    public GioHangDTO findByIdUser(UUID  idUser) {
        Optional<UserEntity> user = usersRepository.findById(idUser);
        GioHangEntity gioHangEntity = gioHangRepository.findByUserId(user.get().getId());
        return modelMapper.map(gioHangEntity, GioHangDTO.class);
    }
}
