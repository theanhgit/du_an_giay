package com.example.datn.Repository;

import com.example.datn.entity.VaiTroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTroEntity, UUID> {
    @Query("SELECT v FROM VaiTroEntity v WHERE v.tenVaiTro = 'User'")
    VaiTroEntity findByTenVaiTroUser();

}
