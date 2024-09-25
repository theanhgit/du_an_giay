package com.example.datn.restcontroller;

import com.example.datn.dto.TrangThaiHoaDonDTO;
import com.example.datn.service.TrangThaiHdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/trang-thai-hd")
public class TrangThaiHDRestController {

    private final TrangThaiHdService trangThaiHdService;

    @GetMapping("/ship-cod")
    public List<TrangThaiHoaDonDTO>FinByShipCod(){
      return trangThaiHdService.FinByShipCod();
    }
    @GetMapping("/ship-Online")
    public List<TrangThaiHoaDonDTO>FinByOnline(){
        return trangThaiHdService.FinByOnline();
    }
}
