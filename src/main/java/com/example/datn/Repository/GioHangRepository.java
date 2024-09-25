package com.example.datn.Repository;

import com.example.datn.entity.GioHangChiTietEntity;
import com.example.datn.entity.GioHangEntity;
import com.example.datn.entity.SanPhamChiTietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GioHangRepository extends JpaRepository<GioHangEntity, UUID> {
   GioHangEntity findByUserId(UUID userId);
}
