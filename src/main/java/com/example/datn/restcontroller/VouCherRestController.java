package com.example.datn.restcontroller;


import com.example.datn.dto.VoucherDTO;

import com.example.datn.service.VouCherService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VouCherRestController {
    private final VouCherService vouCherService;

    @GetMapping()
    public List<VoucherDTO> getAllVouCher () {
        return vouCherService.getAllVoucher();
    }
}
