package com.example.datn.restcontroller;

import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.MauSacDTO;
import com.example.datn.service.KichCoService;
import com.example.datn.service.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kichco")
@RequiredArgsConstructor
public class KichCoResController {
    private final KichCoService kichCoService;

    @GetMapping("/getAll")
    public List<KichCoDTO> getAllMauSac() {
        return kichCoService.getAllKichCo();
    }

    @PostMapping("/add")
    public KichCoDTO addKichCo(@RequestBody KichCoDTO dto){
        return kichCoService.addKichCo(dto);
    }
    @PutMapping("/update")
    public KichCoDTO updateKichCo(@RequestBody KichCoDTO dto){
        return kichCoService.updateKichCo( dto);
    }

}
