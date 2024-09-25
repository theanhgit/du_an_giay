package com.example.datn.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SanPhamChiTietPK implements Serializable {

    private UUID sanPhamId;

    private UUID sanPhamChiTietId;

    // Constructors, getters, setters
}
