package com.example.datn.Repository;

import com.example.datn.dto.DiaChiDTO;
import com.example.datn.entity.DiaChiEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface DiaChiRepository extends JpaRepository<DiaChiEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE DiaChiEntity d SET d.diaChi = :diaChi, d.xa = :xa, d.huyen = :huyen, d.tinh = :tinh WHERE d.id = (SELECT u.diaChi.id FROM UserEntity u WHERE u.id = :userId)")
    void updateDiaChiByUserId(
            @PathVariable("userId") UUID userId,
            @Param("diaChi") String diaChi,
            @Param("xa") String xa,
            @Param("huyen") String huyen,
            @Param("tinh") String tinh
    );

    @Query("SELECT d FROM DiaChiEntity d WHERE d.id = (SELECT u.diaChi.id FROM UserEntity u WHERE u.id = :userId)")
    DiaChiEntity findByUserId(@Param("userId") UUID userId);
}
