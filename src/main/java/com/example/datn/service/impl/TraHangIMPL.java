package com.example.datn.service.impl;

import com.example.datn.Repository.DoiTraRepository;
import com.example.datn.Repository.HoaDonRepository;
import com.example.datn.Repository.SanPhamChiTietRepository;
import com.example.datn.dto.SanPhamChiTietCrud;
import com.example.datn.dto.SanPhamDTO;
import com.example.datn.dto.TraHangCRUD;
import com.example.datn.dto.TraHangDTO;
import com.example.datn.entity.*;
import com.example.datn.service.TraHangService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraHangIMPL implements TraHangService {
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final HoaDonRepository hoaDonRepository;
    private final DoiTraRepository doiTraRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<TraHangDTO> getAllTraHang(Integer totalPage, Integer totalItem) {
        Pageable pageable = PageRequest.of(totalPage, totalItem);
        Page<TraHangEntity> entityPage = doiTraRepository.findAll(pageable);

        List<TraHangDTO> dtos = entityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, TraHangDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public TraHangCRUD addTraHang(TraHangCRUD traHangCRUD) {
        Optional<HoaDonEntity> hoaDon = hoaDonRepository.findById(traHangCRUD.getHoaDon());
        Optional<SanPhamChiTietEntity> sanPhamChiTiet = sanPhamChiTietRepository.findById(traHangCRUD.getSanPhamChiTiet());

        if (!hoaDon.isPresent() || !sanPhamChiTiet.isPresent()) {
            throw new RuntimeException("Hóa đơn hoặc sản phẩm chi tiết không tồn tại");
        }

        // Kiểm tra nếu TraHangEntity đã tồn tại với cùng HoaDon và SanPhamChiTiet
        Optional<TraHangEntity> existingTraHang = doiTraRepository.findByHoaDonAndSanPhamChiTiet(hoaDon.get(), sanPhamChiTiet.get());

        TraHangEntity traHangEntity;

        if (existingTraHang.isPresent()) {
            // Nếu tồn tại, thực hiện cập nhật thông tin
            traHangEntity = existingTraHang.get();
            traHangEntity.setLyDo(traHangCRUD.getLyDo());
            traHangEntity.setType(traHangCRUD.getType());
            traHangEntity.setSoLuong(traHangCRUD.getSoLuong());
            traHangEntity.setStatus(traHangCRUD.getStatus());
            traHangEntity.setUpdateDate(LocalDate.now().atStartOfDay());  // Cập nhật thời gian
        } else {
            // Nếu không tồn tại, tạo mới
            traHangEntity = TraHangEntity.builder()
                    .hoaDon(hoaDon.get())
                    .sanPhamChiTiet(sanPhamChiTiet.get())
                    .lyDo(traHangCRUD.getLyDo())
                    .type(traHangCRUD.getType())
                    .soLuong(traHangCRUD.getSoLuong())
                    .status(traHangCRUD.getStatus())
                    .build();
            traHangEntity.setCreateDate(LocalDate.now());
            traHangEntity.setUpdateDate(LocalDate.now().atStartOfDay());
        }
        TraHangEntity traHangEntitySave = doiTraRepository.save(traHangEntity);
        return modelMapper.map(traHangEntitySave, TraHangCRUD.class);
    }

    @Override
    public void updateStatus(UUID idHoaDon) {
        doiTraRepository.updateTrangThaiTraHang(idHoaDon);
    }

}
