package com.example.datn.restcontroller;

import com.example.datn.common.Appcontants;
import com.example.datn.dto.*;
import com.example.datn.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController  {
    private final UserService userService;
    @GetMapping("/{idUser}")
    public UserDTO getUserById (@PathVariable UUID idUser) {
        return userService.findById(idUser);
    }

    @PostMapping("/add-user")
    public UserCrud addUser(@RequestBody UserCrud userCrud){
        return userService.addUser(userCrud);
    }
    @GetMapping("/all")
    public Page<UserDTO> getAllProducts (
            @RequestParam(value = "pageNo", defaultValue = Appcontants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Appcontants.DEFAULT_TOTAL_NUMBER, required = false) int pageSize
            , @Valid UsersFiterDTO filterForm) {

        return userService.findAll(pageNo, pageSize,filterForm);
    }
    @PutMapping("/mo/{idUser}")
    public void UpdateUsermo(@PathVariable UUID idUser){
        userService.updateUserMo(idUser);
    }
    @PutMapping("/khoa/{idUser}")
    public void UpdateUserKhoa(@PathVariable UUID idUser){
        userService.updateUserKhoa(idUser);

    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            userService.updateUserMatKhau(request);
            return ResponseEntity.ok(new ApiResponse(true, "Đổi mật khẩu thành công!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Mật khẩu cũ không đúng!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Có lỗi xảy ra, vui lòng thử lại sau!"));
        }
    }
}
