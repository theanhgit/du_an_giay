package com.example.datn.service;

import com.example.datn.dto.DiaChiDTO;
import org.hibernate.sql.Update;

import java.util.UUID;

public interface DiaChiService {
    DiaChiDTO upDateDiaChiByIdUser (UUID idUser,String diaChi, String xa,String huyen,String tinh);
}
