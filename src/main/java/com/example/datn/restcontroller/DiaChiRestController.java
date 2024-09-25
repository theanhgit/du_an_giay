package com.example.datn.restcontroller;

import com.example.datn.dto.DiaChiDTO;
import com.example.datn.service.DiaChiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/DiaChi")
public class DiaChiRestController {
    private  final DiaChiService diaChiService;
    @PostMapping("/{userId}")
    public DiaChiDTO upDateDiaChiByIdUser(
            @PathVariable("userId") UUID userId,
            @RequestParam String diaChi,
            @RequestParam String xa,
            @RequestParam String huyen,
            @RequestParam String tinh) {

        return diaChiService.upDateDiaChiByIdUser(userId, diaChi, xa, huyen, tinh);
    }
}
