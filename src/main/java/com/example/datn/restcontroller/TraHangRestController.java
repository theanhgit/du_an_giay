package com.example.datn.restcontroller;

import com.example.datn.common.Appcontants;
import com.example.datn.dto.*;
import com.example.datn.service.TraHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tra-hang")
public class TraHangRestController {
    private final TraHangService traHangService;
    @GetMapping()
    public Page<TraHangDTO> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = Appcontants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Appcontants.DEFAULT_TOTAL_NUMBER, required = false) int pageSize
    ) {

        return traHangService.getAllTraHang(pageNo, pageSize);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDoiTra(@RequestBody TraHangCRUD traHangCRUD) {
        TraHangCRUD traHangCRUD1 = traHangService.addTraHang(traHangCRUD);
        return ResponseEntity.ok(traHangCRUD1);

    }
    @PutMapping("/update-status/{idHoaDon}")
    public void updateStatus(@PathVariable("idHoaDon") UUID idHoaDon){
      traHangService.updateStatus(idHoaDon);
    }
}
