package com.example.datn.Repository;

import com.example.datn.entity.VouCherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VouCherRepository extends JpaRepository<VouCherEntity, UUID> {
    @Query("SELECT v FROM VouCherEntity v WHERE v.trangThai = 1")
    List<VouCherEntity> findAllActiveVouchers();
}
