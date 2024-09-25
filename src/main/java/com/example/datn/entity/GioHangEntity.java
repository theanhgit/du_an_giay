package com.example.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GioHang")
public class GioHangEntity extends SuperEntity{
    @ManyToOne
    @JoinColumn(name  = "user_id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "gioHang")
    private List<GioHangChiTietEntity> gioHangEntities = new ArrayList<GioHangChiTietEntity>();
}
