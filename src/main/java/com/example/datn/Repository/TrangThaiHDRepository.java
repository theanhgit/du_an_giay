package com.example.datn.Repository;

import com.example.datn.entity.TrangThaiHDEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface TrangThaiHDRepository extends JpaRepository<TrangThaiHDEntity, UUID> {
    @Query("SELECT t FROM TrangThaiHDEntity t WHERE t.ten = 'Ship Cod'")
    List<TrangThaiHDEntity> FinByShipCode();

    @Query("SELECT t FROM TrangThaiHDEntity t WHERE t.ten = 'Thanh Toán Online'")
    List<TrangThaiHDEntity> FinByOnline();
    TrangThaiHDEntity findByTen(String ten);
    TrangThaiHDEntity findByTrangThai(String trangThai);
}
