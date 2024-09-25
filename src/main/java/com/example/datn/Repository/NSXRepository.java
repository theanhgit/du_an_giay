package com.example.datn.Repository;

import com.example.datn.entity.NSXEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NSXRepository extends JpaRepository<NSXEntity, UUID> {
}
