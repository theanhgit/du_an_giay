package com.example.datn.restcontroller;

import com.example.datn.Repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/thongke")
public class ThongKeController {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping("/theothang")
    public List<Object[]> thongKeTheoThang() {
        return sanPhamRepository.thongKeTheoThang();
    }

    @GetMapping("/sanphambanchay")
    public List<Map<String, Object>> sanPhamBanChay() {
        List<Object[]> results = sanPhamRepository.sanPhamBanChay();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("hinhAnh", result[0]);
            map.put("tenSanpham", result[1]);
            map.put("tongSoLuong", result[2]);
            response.add(map);
        }
        return response;
    }

    @GetMapping("/timkiem")
    public List<Map<String, Object>> timHoaDonTheoNgay(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate) {

        List<Object[]> results = sanPhamRepository.findSalesData(startDate, endDate, 1);

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("tenSanpham", result[0]);
            map.put("kichCo", result[1]);
            map.put("mauSac", result[2]);
            map.put("tongSoLuong", result[3]);
            map.put("ngay", result[4]);
            response.add(map);
        }
        return response;
    }
}
