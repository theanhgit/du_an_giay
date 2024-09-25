package com.example.datn.service;

import com.example.datn.dto.*;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
     UserDTO findByTaiKhoan(String taiKhoan);
     UserDTO findById(UUID id);
     UserCrud addUser(UserCrud userCrud);
     void updateUserKhoa(UUID idUser);
     void updateUserMo(UUID idUser);

     void updateUserMatKhau(ChangePasswordRequest passwordRequest);
    Page<UserDTO> findAll(Integer totalPage, Integer totalItem, UsersFiterDTO form);


}
