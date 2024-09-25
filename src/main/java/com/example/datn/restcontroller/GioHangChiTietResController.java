package com.example.datn.restcontroller;

import com.example.datn.dto.GioHangChiTietCrud;
import com.example.datn.dto.GioHangChiTietDTO;
import com.example.datn.service.GioHangChiTietService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/GHCT")
public class GioHangChiTietResController {
    private final GioHangChiTietService gioHangChiTietService;

    @GetMapping("/user/{idUser}")
    public List<GioHangChiTietDTO> getAllProducts(@PathVariable UUID idUser) {
        return gioHangChiTietService.getALLGioHangChiTiet(idUser);
    }

    @PostMapping
    public ResponseEntity<?> addGioHangChiTiet(@RequestBody GioHangChiTietCrud gioHangChiTietDTO) {
        try {
            GioHangChiTietCrud savedGioHangChiTiet = gioHangChiTietService.addGioHangCT(gioHangChiTietDTO);
            return ResponseEntity.ok(savedGioHangChiTiet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi trong quá trình thêm sản phẩm vào giỏ hàng.");
        }
    }

    @DeleteMapping("{id}")
    public void DeleteGioHangCT(@PathVariable UUID id) {
        gioHangChiTietService.deleteGioHangCT(id);
    }
}
