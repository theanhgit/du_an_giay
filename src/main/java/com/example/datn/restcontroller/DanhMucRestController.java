package com.example.datn.restcontroller;

import com.example.datn.dto.DanhMucDTO;
import com.example.datn.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhmuc")
@RequiredArgsConstructor
public class DanhMucRestController {


    private final DanhMucService danhMucService;

    @GetMapping("/getAll")
    public List<DanhMucDTO> getAllDanhMuc() {
        return danhMucService.getAllDanhMuc();
    }

    @PostMapping("/add")
   public DanhMucDTO addDanhMuc(@RequestBody DanhMucDTO dto){
        return danhMucService.addDanhMuc(dto);
    }
    @PutMapping("/update")
    public DanhMucDTO updateDanhMuc(@RequestBody DanhMucDTO dto){
        return danhMucService.upDateDanhMuc(dto);
    }
}
