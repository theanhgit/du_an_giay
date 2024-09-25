package com.example.datn.restcontroller;

import com.example.datn.dto.DanhMucDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.service.DanhMucService;
import com.example.datn.service.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mausac")
@RequiredArgsConstructor
public class MauSacResController {
    private final MauSacService mauSacService;

    @GetMapping("/getAll")
    public List<MauSacDTO> getAllMauSac() {
        return mauSacService.getAllMauSac();
    }
    @PostMapping("/add")
    public MauSacDTO addDanhMuc(@RequestBody MauSacDTO dto){
        return mauSacService.addMauSac(dto);
    }
    @PutMapping("/update")
    public MauSacDTO updateDanhMuc(@RequestBody MauSacDTO dto){
        return mauSacService.updateMauSac(dto);
    }
}
