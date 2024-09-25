package com.example.datn.restcontroller;

import com.example.datn.service.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-forword")
public class ForWordUser {
    private final EmailService emailService;

    @PostMapping("/pass")
    public ResponseEntity<String> forWordPass(@RequestParam String taiKhoan, @RequestParam String SDT) {
        try {
            emailService.sendPasswordResetEmail(taiKhoan, SDT);
            return ResponseEntity.ok("Email đã được gửi!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}



