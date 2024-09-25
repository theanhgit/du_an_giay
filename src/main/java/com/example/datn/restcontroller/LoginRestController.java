package com.example.datn.restcontroller;

import com.example.datn.dto.UserDTO;
import com.example.datn.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginRestController {
    private final UserService userService;
//    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        UserDTO user = userService.findByTaiKhoan(loginRequest.getTaiKhoan());
        if (user != null && user.getMatKhau().equals(loginRequest.getMatKhau())) {
            session.setAttribute("user", user);
            String role = user.getVaiTro().getTenVaiTro();  // Assuming getVaiTro returns the role name
//            System.out.println("QuangS :"+role.toString());
            return ResponseEntity.ok(new LoginResponse("Logged in", role));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai Tài Khoản hoặc mật khẩu");
        }
    }



    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();  // Xóa bỏ session hiện tại
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You need to login first");
        }
        UserDTO user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}

@Data
class LoginRequest {
    private String taiKhoan;
    private String matKhau;
}

@Data
class LoginResponse {
    private String message;
    private String role;

    public LoginResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }
}
