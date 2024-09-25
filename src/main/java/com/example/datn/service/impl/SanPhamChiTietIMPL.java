package com.example.datn.service.impl;

import com.example.datn.Repository.*;
import com.example.datn.dto.*;
import com.example.datn.entity.*;

import com.example.datn.service.SanPhamChiTietService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
@Builder
public class SanPhamChiTietIMPL implements SanPhamChiTietService {
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final ChatLieuRepository chatLieuRepository;
    private final SanPhamRepository sanPhamRepository;
    private final HinhAnhRepository hinhAnhRepository;
    private final KichCoRepository kichCoRepository;
    private final MauSacRepository mauSacRepository;
    private final NSXRepository nsxRepository;
    private final DanhMucRepository danhMucRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<SanPhamChiTietDTO> getAllSanPhamChiTiet(Integer totalPage, Integer totalItem, SanPhamChiTietFiterDTO form) {
        Pageable pageable = PageRequest.of(totalPage, totalItem);

        // Xây dựng Specification từ form (SanPhamChiTietFiterDTO)
        Specification<SanPhamChiTietEntity> specification = SpecificationProduct.buildWhere(form);

        // Kiểm tra nếu không có Specification hoặc Specification là null
        if (specification == null) {
            List<SanPhamChiTietDTO> emptyList = Collections.emptyList();
            return new PageImpl<>(emptyList, pageable, 0);
        }

        // Sử dụng Specification trong phương thức findByProductName
        Page<SanPhamChiTietEntity> entityPage = sanPhamChiTietRepository.findAll(specification, pageable);

        // Convert Page<SanPhamChiTietEntity> sang Page<SanPhamChiTietDTO>
        List<SanPhamChiTietDTO> dtos = entityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, SanPhamChiTietDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public Page<SanPhamChiTietDTO> getAllSanPhamChiTietBYidSP(UUID idSP, Integer totalPage,
                                                              Integer totalItem, SanPhamCtFiterDTO fiterDTO) {
        // Tạo Specification để lọc theo idSP
        Specification<SanPhamChiTietEntity> specByIdSP = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("sanPham").get("id"), idSP);


        Specification<SanPhamChiTietEntity> specByFilter = SpectificationSpct.buildWhereCT(fiterDTO);

        // Kết hợp các Specifications
        Specification<SanPhamChiTietEntity> combinedSpec = Specification.where(specByIdSP).and(specByFilter);

        Pageable pageable = PageRequest.of(totalPage, totalItem);

        // Truy vấn với combined Specification và Pageable
        Page<SanPhamChiTietEntity> entityPage = sanPhamChiTietRepository.findAll(combinedSpec, pageable);

        // Chuyển đổi từ entity sang DTO
        List<SanPhamChiTietDTO> dtos = entityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, SanPhamChiTietDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, entityPage.getTotalElements());
    }

    @Override
    public SanPhamChiTietCrud addSanPhamChiTiet(SanPhamChiTietCrud sanPhamChiTietCrud) {
        Optional<DanhMucEntity> danhMuc =danhMucRepository.findById(sanPhamChiTietCrud.getDanhMuc());
        Optional<ChatLieuEntity>chatLieu =chatLieuRepository.findById(sanPhamChiTietCrud.getChatLieu());
        Optional<NSXEntity>nsx =nsxRepository.findById(sanPhamChiTietCrud.getNsx());
        Optional<HinhAnhEntity>hinhAnh =hinhAnhRepository.findById(sanPhamChiTietCrud.getHinhAnh());
        Optional<KichCoEntity>kichCo =kichCoRepository.findById(sanPhamChiTietCrud.getKichCo());
        Optional<MauSacEntity>mauSac =mauSacRepository.findById(sanPhamChiTietCrud.getMauSac());
        Optional<SanPhamEntity>sanPham =sanPhamRepository.findById(sanPhamChiTietCrud.getSanPham());
        SanPhamChiTietEntity sanPhamChiTiet = SanPhamChiTietEntity.builder()
                .sanPham(sanPham.get())
                .soLuong(sanPhamChiTietCrud.getSoLuong())
                .kichCo(kichCo.get())
                .mauSac(mauSac.get())
                .chatLieu(chatLieu.get())
                .danhMuc(danhMuc.get())
                .hinhAnh(hinhAnh.get())
                .nsx(nsx.get())
                .giaSanPham(sanPhamChiTietCrud.getGiaSanPham())
                .moTa(sanPhamChiTietCrud.getMoTa())
                .gioiTinh(sanPhamChiTietCrud.getGioiTinh())
                .trongLuong(sanPhamChiTietCrud.getTrongLuong())
                .trangThai(sanPhamChiTietCrud.getTrangThai())
                .build();
        sanPhamChiTiet.setCreateDate(LocalDate.now());
        sanPhamChiTiet.setUpdateDate(LocalDate.now().atStartOfDay());
        SanPhamChiTietEntity sanPhamChiTietSave = sanPhamChiTietRepository.save(sanPhamChiTiet);
        return modelMapper.map(sanPhamChiTietSave, SanPhamChiTietCrud.class);
    }

    @Override
    public SanPhamChiTietCrud upDateSanPhamChiTiet(SanPhamChiTietCrud sanPhamChiTietCrud) {
        Optional<DanhMucEntity> danhMuc =danhMucRepository.findById(sanPhamChiTietCrud.getDanhMuc());
        Optional<ChatLieuEntity>chatLieu =chatLieuRepository.findById(sanPhamChiTietCrud.getChatLieu());
        Optional<NSXEntity>nsx =nsxRepository.findById(sanPhamChiTietCrud.getNsx());
        Optional<HinhAnhEntity>hinhAnh =hinhAnhRepository.findById(sanPhamChiTietCrud.getHinhAnh());
        Optional<KichCoEntity>kichCo =kichCoRepository.findById(sanPhamChiTietCrud.getKichCo());
        Optional<MauSacEntity>mauSac =mauSacRepository.findById(sanPhamChiTietCrud.getMauSac());
        Optional<SanPhamEntity>sanPham =sanPhamRepository.findById(sanPhamChiTietCrud.getSanPham());
        SanPhamChiTietEntity sanPhamChiTiet = SanPhamChiTietEntity.builder()
                .sanPham(sanPham.get())
                .soLuong(sanPhamChiTietCrud.getSoLuong())
                .kichCo(kichCo.get())
                .mauSac(mauSac.get())
                .chatLieu(chatLieu.get())
                .danhMuc(danhMuc.get())
                .hinhAnh(hinhAnh.get())
                .nsx(nsx.get())
                .giaSanPham(sanPhamChiTietCrud.getGiaSanPham())
                .moTa(sanPhamChiTietCrud.getMoTa())
                .gioiTinh(sanPhamChiTietCrud.getGioiTinh())
                .trongLuong(sanPhamChiTietCrud.getTrongLuong())
                .trangThai(sanPhamChiTietCrud.getTrangThai())
                .build();
        sanPhamChiTiet.setCreateDate(LocalDate.now());
        sanPhamChiTiet.setUpdateDate(LocalDate.now().atStartOfDay());
        sanPhamChiTiet.setId(sanPhamChiTietCrud.getId());
        SanPhamChiTietEntity sanPhamChiTietSave = sanPhamChiTietRepository.save(sanPhamChiTiet);
        return modelMapper.map(sanPhamChiTietSave, SanPhamChiTietCrud.class);
    }


    @Override
    public SanPhamChiTietDTO findById(UUID id) {
        SanPhamChiTietEntity sanPhamChiTietEntity = sanPhamChiTietRepository.findById(id).orElseThrow();

        return modelMapper.map(sanPhamChiTietEntity, SanPhamChiTietDTO.class);
    }

    @Override
    public List<SanPhamChiTietDTO> GetForSP(Pageable pageable) {
        List<SanPhamChiTietEntity> entities = sanPhamChiTietRepository.findTop4SanPhamChiTiet(pageable);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, SanPhamChiTietDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamChiTietDTO> AllSanPhamChiTietByidSP(UUID idSP) {
        List<SanPhamChiTietEntity> entities = sanPhamChiTietRepository.findByIdSP(idSP);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, SanPhamChiTietDTO.class))
                .collect(Collectors.toList());
    }
}
