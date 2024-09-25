package com.example.datn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "VaiTro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaiTroEntity extends SuperEntity {


    @Column(name = "tenVaiTro", length = 150, nullable = false,columnDefinition = "NVARCHAR(255)")
    private String tenVaiTro;
    @JsonIgnore
    @OneToMany(mappedBy = "vaiTro")
    private List<UserEntity> users = new ArrayList<UserEntity>();

    @Override
    public String toString() {
        return "VaiTroEntity{" +
                "tenVaiTro='" + tenVaiTro + '\'' +
                ", users=" + users +
                '}';
    }
}
