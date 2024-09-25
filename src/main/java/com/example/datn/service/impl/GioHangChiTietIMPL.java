package com.example.datn.service.impl;

import com.example.datn.Repository.GioHangChiTietRepository;
import com.example.datn.Repository.GioHangRepository;
import com.example.datn.Repository.SanPhamChiTietRepository;
import com.example.datn.dto.GioHangChiTietCrud;
import com.example.datn.dto.GioHangChiTietDTO;
import com.example.datn.dto.GioHangDTO;
import com.example.datn.entity.GioHangChiTietEntity;
import com.example.datn.entity.GioHangChiTietId;
import com.example.datn.entity.GioHangEntity;
import com.example.datn.entity.SanPhamChiTietEntity;
import com.example.datn.service.GioHangChiTietService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GioHangChiTietIMPL implements GioHangChiTietService {
    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final GioHangRepository gioHangRepository;
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<GioHangChiTietDTO> getALLGioHangChiTiet(UUID idUser) {
     GioHangEntity gioHangEntity =  gioHangRepository.findByUserId(idUser);
        List<GioHangChiTietEntity> entityList = gioHangChiTietRepository.findByGioHangId(gioHangEntity.getId());
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, GioHangChiTietDTO.class))
                .collect( Collectors.toList());
    }

    @Override
    public GioHangChiTietCrud addGioHangCT(GioHangChiTietCrud gioHangChiTietDTO) {
        if (gioHangChiTietDTO.getGioHang() == null || gioHangChiTietDTO.getSanPhamChiTiet() == null) {
            throw new IllegalArgumentException("gioHang và sanPhamChiTiet không được là null");
        }
        Optional<GioHangEntity> gioHang = gioHangRepository.findById(gioHangChiTietDTO.getGioHang());
        Optional<SanPhamChiTietEntity> sanPhamChiTiet = sanPhamChiTietRepository.findById(gioHangChiTietDTO.getSanPhamChiTiet());
        if (gioHang.isEmpty() || sanPhamChiTiet.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy gioHang hoặc sanPhamChiTiet trong cơ sở dữ liệu");
        }
        if (!checkStockAvailability(gioHangChiTietDTO.getSanPhamChiTiet(), gioHangChiTietDTO.getSoLuong())) {
            throw new IllegalArgumentException("Số lượng sản phẩm yêu cầu vượt quá số lượng tồn kho");

        }
        GioHangChiTietEntity gioHangChiTietEntity = GioHangChiTietEntity.builder()
                .gioHang(gioHang.get())
                .sanPhamChiTiet(sanPhamChiTiet.get())
                .soLuong(gioHangChiTietDTO.getSoLuong())
                .createDate(gioHangChiTietDTO.getCreateDate())
                .updateDate(gioHangChiTietDTO.getUpdateDate())
                .trangThai(gioHangChiTietDTO.getTrangThai())
                .build();
        GioHangChiTietEntity savedEntity = gioHangChiTietRepository.save(gioHangChiTietEntity);

        return modelMapper.map(savedEntity, GioHangChiTietCrud.class);
    }

    @Override
    public void deleteGioHangCT(UUID id) {
        gioHangChiTietRepository.deleteById(id);
    }

    @Override
    public boolean checkStockAvailability(UUID sanPhamChiTietId, int soLuongYeuCau) {
        Optional<SanPhamChiTietEntity> sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId);
        if (sanPhamChiTiet.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy sản phẩm chi tiết trong cơ sở dữ liệu");
        }
        return sanPhamChiTiet.get().getSoLuong() >= soLuongYeuCau;
    }

}
