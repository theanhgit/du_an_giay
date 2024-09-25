package com.example.datn.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class SuperEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        @Column(name = "createDate")
        private LocalDate createDate;

        @Column(name = "updateDate")
        private LocalDateTime updateDate;


        @PreUpdate
        protected void onUpdate() {
                this.updateDate = LocalDate.now().atStartOfDay();
        }

        protected void createdate() {
                this.createDate = LocalDate.from(LocalDate.now());
        }

    }



