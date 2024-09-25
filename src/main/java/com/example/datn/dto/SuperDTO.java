package com.example.datn.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SuperDTO {
    private UUID id;
    private LocalDate createDate;
    private LocalDate modifyDate;

    @PreUpdate
    protected void onUpdate() {
        this.modifyDate = LocalDate.now();
    }
    @PreUpdate
    protected void CreateUpdate() {
        this.createDate = LocalDate.now();
    }

}
