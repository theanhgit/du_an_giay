//package com.example.datn.service.impl;
//
//import com.example.datn.Repository.UsersRepository;
//import com.example.datn.entity.UserEntity;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UsersRepository usersRepository;
//
//    @Override
//    @Transactional(readOnly = true) // Đảm bảo phiên làm việc được giữ mở
//    public UserDetails loadUserByUsername(String username) {
//        UserEntity userEntity = usersRepository.findByTaiKhoan(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        // Khởi tạo thuộc tính lazy-loaded nếu cần
//        // Hibernate.initialize(userEntity.getVaiTro()); // Nếu cần thiết và Hibernate được cấu hình
//
//        return new User(
//                userEntity.getTaiKhoan(),
//                userEntity.getMatKhau(), // Mật khẩu đã được mã hóa
//                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getVaiTro().getTenVaiTro()))
//        );
//    }
//}
////
////}
