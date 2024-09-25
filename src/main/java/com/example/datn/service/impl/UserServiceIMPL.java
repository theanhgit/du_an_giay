package com.example.datn.service.impl;

import com.example.datn.Repository.DiaChiRepository;
import com.example.datn.Repository.GioHangRepository;
import com.example.datn.Repository.UsersRepository;
import com.example.datn.Repository.VaiTroRepository;
import com.example.datn.dto.*;
import com.example.datn.entity.*;

import com.example.datn.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private UsersRepository usersRepository;
    private final VaiTroRepository vaiTroRepository;
    private final GioHangRepository gioHangRepository;
    private final DiaChiRepository diaChiRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public void CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDTO findByTaiKhoan(String taiKhoan) {
        Optional<UserEntity> user = usersRepository.findByTaiKhoan(taiKhoan);
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDTO.class);
        } else {
            return null;  // hoặc ném ngoại lệ tùy thuộc vào logic của bạn
        }
    }

    @Override
    public UserDTO findById(UUID id) {
        Optional<UserEntity> user = usersRepository.findById(id);
        return user.map(value -> modelMapper.map(value, UserDTO.class)).orElse(null);
    }

    @Override
    public UserCrud addUser(UserCrud userCrud) {
        if (usersRepository.findByTaiKhoan(userCrud.getTaiKhoan()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tài khoản hoặc số điện thoại đã tồn tại");

        }

        // Kiểm tra số điện thoại đã tồn tại hay chưa
        if (usersRepository.findBySdt(userCrud.getSdt()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tài khoản hoặc số điện thoại đã tồn tại");

        }
        UUID idDiaChi = UUID.randomUUID();
        UUID idGioHang = UUID.randomUUID();
        DiaChiEntity diaChiEntity = new DiaChiEntity();
        diaChiEntity.setId(idDiaChi);
        diaChiEntity.setTinh("0");
        diaChiEntity.setHuyen("0");
        diaChiEntity.setXa("0");
        diaChiEntity.setDiaChi("0");
        diaChiEntity.setTrangThai(1);
        diaChiEntity.setCreateDate(LocalDate.now());
        diaChiEntity.setUpdateDate(LocalDateTime.now());
        diaChiRepository.save(diaChiEntity);
        VaiTroEntity vaiTroEntity = vaiTroRepository.findByTenVaiTroUser();
//        PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder(10);
        UserEntity userEntity = UserEntity.builder()
                .diaChi(diaChiEntity)
                .vaiTro(vaiTroEntity)
                .taiKhoan(userCrud.getTaiKhoan())
                .matKhau(userCrud.getMatKhau())
                .ho(userCrud.getHo())
                .tenDem(userCrud.getTenDem())
                .ten(userCrud.getTen())
                .sdt(userCrud.getSdt())
                .trangThai(1)
                .email(userCrud.getEmail())
                .ngaySinh(userCrud.getNgaySinh())
                .build();
        userEntity.setCreateDate(LocalDate.now());
        usersRepository.save(userEntity);
        GioHangEntity gioHangEntity = new GioHangEntity();
        gioHangEntity.setId(idGioHang);
        gioHangEntity.setCreateDate(LocalDate.now());
        gioHangEntity.setUpdateDate(LocalDateTime.now());
        gioHangEntity.setUser(userEntity);
        gioHangRepository.save(gioHangEntity);
        return modelMapper.map(userEntity, UserCrud.class);
    }

    @Override
    @Transactional
    public void updateUserKhoa(UUID idUser) {
        usersRepository.updateUserKhoa(idUser);

    }

    @Override
    @Transactional
    public void updateUserMo(UUID idUser) {
        usersRepository.updateUserMo(idUser);
    }

    @Override
    @Transactional
    public void updateUserMatKhau(ChangePasswordRequest changePasswordRequest) {
        // Chuyển đổi từ DTO sang Entity
        UserEntity userEntity = usersRepository.findByTaiKhoan(changePasswordRequest.getTaiKhoan())
                .orElseThrow(() -> new IllegalArgumentException("Người dùng không tồn tại"));

        // Kiểm tra mật khẩu cũ
        if (userEntity.getMatKhau().equals(changePasswordRequest.getMatKhauCu())) {
            // Cập nhật mật khẩu mới
            userEntity.setMatKhau(changePasswordRequest.getMatKhauMoi());
            usersRepository.save(userEntity);
        } else {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng!");
        }
    }


    @Override
    public Page<UserDTO> findAll(Integer totalPage, Integer totalItem, UsersFiterDTO form) {
        Pageable pageable = PageRequest.of(totalPage, totalItem);

        // Xây dựng Specification với điều kiện từ form
        Specification<UserEntity> specification = SpectifileCationUser.buildWhereCT(form);

        // Thêm điều kiện vai trò vào Specification nếu cần
        if (specification == null) {
            specification = Specification.where((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("vaiTro"), "User")
            );
        } else {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("vaiTro").get("tenVaiTro"), "USER")
            );
        }

        // Sử dụng Specification trong phương thức findAll
        Page<UserEntity> entityPage = usersRepository.findAll(specification, pageable);

        List<UserDTO> dtos = entityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

}
