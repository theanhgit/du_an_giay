package com.example.datn.Repository;

import com.example.datn.entity.HinhAnhEntity;
import com.example.datn.entity.HoaDonChiTietEntity;
import com.example.datn.entity.SanPhamChiTietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HinhAnhRepository extends JpaRepository<HinhAnhEntity,UUID> {
}
