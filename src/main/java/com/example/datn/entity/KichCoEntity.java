package com.example.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "kichCo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KichCoEntity extends SuperEntity {

    @Column(name = "tenKichCo", length = 120, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String tenKichCo;
    @Column(name = "doDai", length = 150, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String doDai;
    @JsonIgnore
    @OneToMany(mappedBy = "kichCo")
    @ToString.Exclude
    private List<SanPhamChiTietEntity> sanPhamChiTiets = new ArrayList<SanPhamChiTietEntity>();
}
