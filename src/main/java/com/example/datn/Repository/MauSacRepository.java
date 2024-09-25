package com.example.datn.Repository;

import com.example.datn.entity.MauSacEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MauSacRepository extends JpaRepository<MauSacEntity, UUID> {
}
