package com.example.datn.dto;

import com.example.datn.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiaChiDTO extends SuperDTO{
    private String diaChi;
    private String xa;

    private String huyen;

    private String tinh;


    private int trangThai;
}
