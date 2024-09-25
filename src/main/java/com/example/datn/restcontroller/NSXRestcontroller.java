package com.example.datn.restcontroller;

import com.example.datn.dto.KichCoDTO;
import com.example.datn.dto.NSXDTO;
import com.example.datn.service.KichCoService;
import com.example.datn.service.NSXService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nsx")
@RequiredArgsConstructor
public class NSXRestcontroller {
    private final NSXService nsxService;

    @GetMapping("/getAll")
    public List<NSXDTO> getAllNSX() {
        return nsxService.getAllNSX();
    }
}
