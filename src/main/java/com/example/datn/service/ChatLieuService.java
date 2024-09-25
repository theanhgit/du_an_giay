package com.example.datn.service;

import com.example.datn.dto.ChatLieuDTO;
import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.MauSacDTO;

import java.util.List;

public interface ChatLieuService {
    List<ChatLieuDTO> getAllChatLieu();
    ChatLieuDTO addChatLieu(ChatLieuDTO chatLieuDTO);
    ChatLieuDTO updateChatLieu(ChatLieuDTO chatLieuDTO);
}
