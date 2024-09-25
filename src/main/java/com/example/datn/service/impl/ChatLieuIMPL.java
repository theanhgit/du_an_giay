package com.example.datn.service.impl;

import com.example.datn.Repository.ChatLieuRepository;
import com.example.datn.Repository.KichCoRepository;
import com.example.datn.dto.ChatLieuDTO;
import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.entity.ChatLieuEntity;
import com.example.datn.entity.KichCoEntity;
import com.example.datn.entity.MauSacEntity;
import com.example.datn.service.ChatLieuService;
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
public class ChatLieuIMPL implements ChatLieuService {
    private final ChatLieuRepository chatLieuRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ChatLieuDTO> getAllChatLieu() {
        List<ChatLieuEntity>chatLieuEntities = chatLieuRepository.findAll();
        return chatLieuEntities.stream()
                .map(entity -> modelMapper.map(entity, ChatLieuDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ChatLieuDTO addChatLieu(ChatLieuDTO chatLieuDTO) {
        ChatLieuEntity chatLieu = ChatLieuEntity.builder()
                .ten(chatLieuDTO.getTen())
                .build();
        chatLieu.setCreateDate(LocalDate.now());
        chatLieu.setUpdateDate(LocalDateTime.now());
        chatLieuRepository.save(chatLieu);

        return modelMapper.map(chatLieu,ChatLieuDTO.class);
    }

    @Override
    public ChatLieuDTO updateChatLieu(ChatLieuDTO chatLieuDTO) {
        Optional<ChatLieuEntity> chatLieu = chatLieuRepository.findById(chatLieuDTO.getId());
        ChatLieuEntity chatLieu1 = new ChatLieuEntity();
        chatLieu1.setId(chatLieu.get().getId());
        chatLieu1.setTen(chatLieuDTO.getTen());
        chatLieu1.setCreateDate(LocalDate.now());
        chatLieu1.setUpdateDate(LocalDateTime.now());
        chatLieuRepository.save(chatLieu1);
        return modelMapper.map(chatLieu1,ChatLieuDTO.class);
    }
}
