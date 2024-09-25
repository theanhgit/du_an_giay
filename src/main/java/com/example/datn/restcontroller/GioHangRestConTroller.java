package com.example.datn.restcontroller;

import com.example.datn.dto.GioHangDTO;
import com.example.datn.service.GioHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/GH")
public class GioHangRestConTroller {
    private final GioHangService gioHangService;
    @GetMapping("/user/{idUser}")
    public GioHangDTO getAllGH (@PathVariable UUID idUser) {
        return gioHangService.findByIdUser(idUser);
    }
}
