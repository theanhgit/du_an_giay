package com.example.datn.Repository;

import com.example.datn.entity.KichCoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KichCoRepository extends JpaRepository<KichCoEntity, UUID> {
}
