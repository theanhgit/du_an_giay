package com.example.datn.restcontroller;

import com.example.datn.service.HoaDonChiTietService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hd")
public class HoaDonRestController {
    private final HoaDonChiTietService hoaDonChiTietService;

    @PutMapping("/{idTrangThaiHD}")
    public void updateTrangThaiHD (@PathVariable UUID idTrangThaiHD) {
         hoaDonChiTietService.updateTrangThaiHD(idTrangThaiHD);
    }
}
