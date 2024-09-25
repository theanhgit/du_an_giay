package com.example.datn.Repository;

import com.example.datn.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,UUID> {
    Optional<UserEntity> findByTaiKhoan(String taiKhoan);
    List<UserEntity> findByTenAndSdt(String ten , String sdt);
    Optional<UserEntity> findByTen(String ten);


}
