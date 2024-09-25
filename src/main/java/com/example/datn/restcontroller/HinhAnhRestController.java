package com.example.datn.restcontroller;

import com.example.datn.dto.DanhMucDTO;
import com.example.datn.dto.HinhAnhDTO;
import com.example.datn.service.DanhMucService;
import com.example.datn.service.HinhAnhService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hinhanh")
@RequiredArgsConstructor
public class HinhAnhRestController {
    private final HinhAnhService hinhAnhService;

    @GetMapping("/getAll")
    public List<HinhAnhDTO> getAllDanhMuc() {
        return hinhAnhService.getAllHinhAnh();
    }

    @PostMapping("/add")
    public HinhAnhDTO addHinhANh (@RequestBody HinhAnhDTO hinhAnhDTO){
        return hinhAnhService.addHinhAnh(hinhAnhDTO);
    }
    @PutMapping("/update")
    public HinhAnhDTO updateHinhAnh (@RequestBody HinhAnhDTO hinhAnhDTO){
        return hinhAnhService.UpdateHinhAnh(hinhAnhDTO);
    }
}
