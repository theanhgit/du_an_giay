package com.example.datn.service.impl;

import com.example.datn.Repository.*;
import com.example.datn.dto.*;
import com.example.datn.entity.*;
import com.example.datn.service.HoaDonChiTietService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoaDonChiTietIMPL implements HoaDonChiTietService {
    private final ModelMapper modelMapper;
    private final HoaDonRepository hoaDonRepository;
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final GioHangRepository gioHangRepository;
    private final HoaDonChiTietRepository hoaDonChiTietRepository;
    private final VouCherRepository vouCherRepository;
    private final UsersRepository usersRepository;
    private final TrangThaiHDRepository trangThaiHDRepository;

    @Override
    @Transactional
    public HoaDonCHiTietCrud addHoaDonCT(UUID idUser, UUID idVoucher, UUID idTrangThaiHD, BigDecimal tongtien) {
        try {
            // Kiểm tra và lấy thông tin từ các repository
            Optional<VouCherEntity> vouCher = vouCherRepository.findById(idVoucher);
            Optional<UserEntity> user = usersRepository.findById(idUser);
            Optional<TrangThaiHDEntity> hdEntity = trangThaiHDRepository.findById(idTrangThaiHD);

            if (!vouCher.isPresent() || !user.isPresent() || !hdEntity.isPresent()) {
                throw new IllegalArgumentException("Voucher, User hoặc Trạng thái Hóa đơn không hợp lệ.");
            }

            GioHangEntity gioHangEntity = gioHangRepository.findByUserId(idUser);
            List<GioHangChiTietEntity> gioHangChiTietEntities = gioHangChiTietRepository.findByGioHangId(gioHangEntity.getId());

            // Kiểm tra trước xem có sản phẩm nào trong giỏ hàng vượt quá số lượng tồn kho không
            for (GioHangChiTietEntity gioHang : gioHangChiTietEntities) {
                if (!checkStockAvailability(gioHang.getSanPhamChiTiet().getId(), gioHang.getSoLuong())) {
                    throw new IllegalArgumentException("Số lượng sản phẩm yêu cầu vượt quá số lượng tồn kho: ");
                }
            }

            // Nếu tất cả sản phẩm đều đủ tồn kho, tiến hành tạo hóa đơn
            UUID idHD = UUID.randomUUID();
            HoaDonEntity hoaDonEntity = new HoaDonEntity();
            hoaDonEntity.setId(idHD);
            hoaDonEntity.setUser(user.get());
            hoaDonEntity.setVouCher(vouCher.get());
            hoaDonEntity.setNgayThanhToan(LocalDate.from(LocalDateTime.now()));
            hoaDonEntity.setCreateDate(LocalDate.from(LocalDateTime.now()));
            hoaDonEntity.setTongTien(BigDecimal.valueOf(0));  // Ban đầu để tổng tiền là 0
            hoaDonEntity.setTrangThaiHD(hdEntity.get());

            // Lưu hóa đơn vào cơ sở dữ liệu
            hoaDonEntity = hoaDonRepository.save(hoaDonEntity);

            // Lưu chi tiết hóa đơn và cập nhật số lượng tồn kho
            for (GioHangChiTietEntity gioHang : gioHangChiTietEntities) {
                HoaDonChiTietEntity hoaDonChiTietEntity = new HoaDonChiTietEntity();
                hoaDonChiTietEntity.setSanPhamChiTiet(gioHang.getSanPhamChiTiet());
                hoaDonChiTietEntity.setHoaDon(hoaDonEntity);
                hoaDonChiTietEntity.setSoLuong(gioHang.getSoLuong());
                BigDecimal thanhTien = gioHang.getSanPhamChiTiet().getGiaSanPham().multiply(BigDecimal.valueOf(gioHang.getSoLuong()));
                hoaDonChiTietEntity.setThanhTien(thanhTien);
                hoaDonChiTietEntity.setUser(user.get());

                // Lưu chi tiết hóa đơn
                hoaDonChiTietRepository.save(hoaDonChiTietEntity);

                // Cập nhật số lượng tồn kho sau khi lưu chi tiết hóa đơn
                sanPhamChiTietRepository.updateSoLuong(gioHang.getSanPhamChiTiet().getId(), gioHang.getSoLuong());

                // Xóa chi tiết giỏ hàng đã được xử lý
                gioHangChiTietRepository.deleteByIdGH(gioHangEntity.getId());
            }

            // Cập nhật tổng tiền cho hóa đơn sau khi đã thêm các chi tiết
            hoaDonEntity.setTongTien(tongtien);
            hoaDonEntity = hoaDonRepository.save(hoaDonEntity);

            return modelMapper.map(hoaDonEntity, HoaDonCHiTietCrud.class);

        } catch (IllegalArgumentException e) {
            System.err.println("Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
            throw e;

        } catch (Exception e) {
            System.err.println("Đã xảy ra lỗi trong quá trình xử lý hóa đơn: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }



    @Override
    public List<HoaDonChiTietDTO> getALlHoaDonCTByIdHoaDon(UUID idHoaDon) {
        Optional<HoaDonEntity> hoaDon =  hoaDonRepository.findById(idHoaDon);
        List<HoaDonChiTietEntity> entityList = hoaDonChiTietRepository.findByHoaDonChiTietByIdHoaDon(hoaDon.get().getId());
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, HoaDonChiTietDTO.class))
                .collect( Collectors.toList());
    }

    @Override
    public List<HoaDonDTO> getAllHoaDonByIdUser(UUID idUser) {
        Optional<UserEntity> user =  usersRepository.findById(idUser);
        List<HoaDonEntity> entityList = hoaDonRepository.findByHoaByIdUser(user.get().getId());
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, HoaDonDTO.class))
                .collect( Collectors.toList());
    }

    @Override
    public List<HoaDonDTO> getAllHoaDonByIdUserTC(UUID idUser) {
        Optional<UserEntity> user =  usersRepository.findById(idUser);
        List<HoaDonEntity> entityList = hoaDonRepository.findByHoaByIdUserTC(user.get().getId());
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, HoaDonDTO.class))
                .collect( Collectors.toList());
    }

    @Override
    public List<HoaDonDTO> getAllHoaDonByIdUserHuy(UUID idUser) {
        Optional<UserEntity> user =  usersRepository.findById(idUser);
        List<HoaDonEntity> entityList = hoaDonRepository.findByHoaByIdUserHuy(user.get().getId());
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, HoaDonDTO.class))
                .collect( Collectors.toList());
    }

    @Override
    @Transactional
    public void updateTrangThaiHD(UUID idTrangThaiHD) {
        List<HoaDonChiTietEntity> hoaDonChiTietEntity =  hoaDonChiTietRepository.findByHoaDonChiTietByIdHoaDon(idTrangThaiHD);
         hoaDonRepository.updateTrangThaiHd(idTrangThaiHD);
         for (HoaDonChiTietEntity hd :hoaDonChiTietEntity){
            sanPhamChiTietRepository.updateSoLuongCong(hd.getSanPhamChiTiet().getId(),hd.getSoLuong());
         }
    }

    @Override
    public boolean checkStockAvailability(UUID sanPhamChiTietId, int soLuongYeuCau) {
        Optional<SanPhamChiTietEntity> sanPhamChiTiet = sanPhamChiTietRepository.findById(sanPhamChiTietId);
        if (sanPhamChiTiet.isEmpty()) {
            throw new EntityNotFoundException("Không tìm thấy sản phẩm chi tiết trong cơ sở dữ liệu");
        }
        return sanPhamChiTiet.get().getSoLuong() >= soLuongYeuCau;
    }

    @Override
    public void deleteHoaDonCT(UUID id,UUID idHoaDon,BigDecimal tongTra,int soLuong) {
        hoaDonChiTietRepository.deleteById(id);
        hoaDonRepository.updateTongTien(idHoaDon,tongTra);
        sanPhamChiTietRepository.updateSoLuongCong(id,soLuong);

    }

    @Override
    public void updateHDCTByidHdSP(UUID newIdSpct, UUID idHoaDon,UUID idSpctCuren,int soLuong) {
        System.out.println("newIdSpct: " + newIdSpct);
        System.out.println("idHoaDon: " + idHoaDon);
        System.out.println("idSpctCuren: " + idSpctCuren);
        System.out.println("soLuong: " + soLuong);
        try {
            hoaDonChiTietRepository.updateHoaDonCT(newIdSpct, idHoaDon, idSpctCuren);
            sanPhamChiTietRepository.updateSoLuongCong(idSpctCuren, soLuong);
            sanPhamChiTietRepository.updateSoLuong(newIdSpct, soLuong);
        } catch (Exception e) {
            System.out.println("General Error: " + e.getMessage());
        }

    }

}
