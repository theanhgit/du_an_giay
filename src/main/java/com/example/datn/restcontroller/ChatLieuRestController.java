package com.example.datn.restcontroller;

import com.example.datn.dto.ChatLieuDTO;
import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.service.ChatLieuService;
import com.example.datn.service.KichCoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatlieu")
@RequiredArgsConstructor
public class ChatLieuRestController {
    private final ChatLieuService chatLieuService;

    @GetMapping("/getAll")
    public List<ChatLieuDTO> getAllMauSac() {
        return chatLieuService.getAllChatLieu();
    }
    @PostMapping("/add")
    public ChatLieuDTO addChatLieu(@RequestBody ChatLieuDTO dto){
        return chatLieuService.addChatLieu(dto);
    }
    @PutMapping("/update")
    public ChatLieuDTO updateChatLieu(@RequestBody ChatLieuDTO dto){
        return chatLieuService.updateChatLieu(dto);
    }
}
