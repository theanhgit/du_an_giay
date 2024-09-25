package com.example.datn.Repository;

import com.example.datn.entity.ChatLieuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatLieuRepository extends JpaRepository<ChatLieuEntity, UUID> {

}
