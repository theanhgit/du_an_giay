package com.example.datn.restcontroller;

import com.example.datn.service.impl.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/api/provinces")
    public List<Map<String, Object>> getProvinces() {
        return addressService.getProvinces();
    }

    @GetMapping("/api/districts/{provinceId}")
    public List<Map<String, Object>> getDistricts(@PathVariable String provinceId) {
        return addressService.getDistricts(provinceId);
    }

    @GetMapping("/api/wards/{districtId}")
    public List<Map<String, Object>> getWards(@PathVariable String districtId) {
        return addressService.getWards(districtId);
    }
}
