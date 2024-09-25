package com.example.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bao_hanh")
public class
BaoHanhEntity extends SuperEntity {


    @Column(name = "ten", length = 150, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String ten;

    @Column(name = "thoiHan", length = 150, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String thoiHan;
    @Column(name = "trangThai", length = 10, nullable = false)
    private int trangThai;
    @JsonIgnore
    @OneToMany(mappedBy = "baoHanh")
    @ToString.Exclude
    private List<SanPhamChiTietEntity> sanPhamChiTiets = new ArrayList<SanPhamChiTietEntity>();
}
