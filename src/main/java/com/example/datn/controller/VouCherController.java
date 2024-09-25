package com.example.datn.controller;

import com.example.datn.Repository.VouCherRepository;
import com.example.datn.entity.VouCherEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VouCherController {
    @Autowired
    VouCherRepository vouCherRepository;

    private void checkAndUpdateVoucherStatus(VouCherEntity vouCher) {
        LocalDate ngayKetThuc = LocalDate.now();
        LocalDate ngayBatDau = LocalDate.now();
        if (vouCher.getNgayKetThuc().isBefore(ngayKetThuc)) {
            vouCher.setTrangThai(0);
            vouCherRepository.save(vouCher);
        }
        if (vouCher.getNgayBatDau().isAfter(ngayBatDau)) {
            vouCher.setTrangThai(0);
            vouCherRepository.save(vouCher);
        }
    }

    @GetMapping("/voucher/getAll")
    public String getAllVouCher(Model model) {
        List<VouCherEntity> listVoucher = vouCherRepository.findAll();
        listVoucher.forEach(this::checkAndUpdateVoucherStatus);
        model.addAttribute("listVoucher", listVoucher);
        model.addAttribute("vouCher", new VouCherEntity());
        return "admin/adminWeb/VouCher";
    }

    @PostMapping("/voucher/add")
    public String addVouCher(@Valid @ModelAttribute("vouCher") VouCherEntity vouCher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<VouCherEntity> listVoucher = vouCherRepository.findAll();
            model.addAttribute("listVoucher", listVoucher);
            return "admin/adminWeb/VouCher";
        }
        try {
            vouCher.setCreateDate(LocalDate.now());
            vouCher.setTrangThai(1);
            vouCherRepository.save(vouCher);
            System.out.println(vouCher);
            return "redirect:/voucher/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    //
    @PostMapping("/voucher/delete/{id}")
    public String deleteVouCher(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!vouCherRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy VouCher này");
                List<VouCherEntity> listVoucher = vouCherRepository.findAll();
                model.addAttribute("listVoucher", listVoucher);
                return "admin/adminWeb/VouCher";
            }
            //Xóa
            vouCherRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/voucher/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<VouCherEntity> listVoucher = vouCherRepository.findAll();
            model.addAttribute("listVoucher", listVoucher);
            return "admin/adminWeb/VouCher";
        }
    }
    @GetMapping("/voucher/detail/{id}")
    public String getVouCherDetail(@PathVariable("id") UUID id, Model model) {
        Optional<VouCherEntity> vouCherOptional = vouCherRepository.findById(id);
        if (vouCherOptional.isPresent()) {
            VouCherEntity vouCher = vouCherOptional.get();
            model.addAttribute("vouCher", vouCher);
            return "admin/adminWeb/VouCherDetail";
        } else {
            model.addAttribute("errorMessage", "VouCher không tồn tại.");
            return "admin/adminWeb/VouCher";
        }
    }

    @PostMapping("/voucher/update/{id}")
    public String updateVouCher(@PathVariable("id") UUID id, @ModelAttribute("vouCher") VouCherEntity updateVouCher, Model model) {
        try {
            // Kiểm tra xem voucher có tồn tại không
            if (!vouCherRepository.existsById(id)) {
                model.addAttribute("errorMessage", "VouCher không tồn tại.");
                List<VouCherEntity> listVoucher = vouCherRepository.findAll();
                model.addAttribute("listVoucher", listVoucher);
                return "admin/adminWeb/VouCher";
            }

            // Lấy thông tin voucher cần cập nhật từ cơ sở dữ liệu
            VouCherEntity existingVouCher = vouCherRepository.findById(id).orElse(null);
            if (existingVouCher == null) {
                return "redirect:/voucher/getAll";
            }

            // Kiểm tra trạng thái ngày bắt đầu và ngày kết thúc
            LocalDate ngayHienTai = LocalDate.now();

            if (updateVouCher.getNgayKetThuc().isBefore(ngayHienTai) || updateVouCher.getNgayBatDau().isAfter(ngayHienTai)) {
                model.addAttribute("errorMessage", "Ngày bắt đầu hoặc kết thúc không hợp lệ. Voucher không thể cập nhật.");
                return "redirect:/voucher/getAll";
            }

            // Cập nhật thông tin voucher
            existingVouCher.setId(updateVouCher.getId());
            existingVouCher.setTen(updateVouCher.getTen());
            existingVouCher.setPhanTramGiam(updateVouCher.getPhanTramGiam());
            existingVouCher.setNgayBatDau(updateVouCher.getNgayBatDau());
            existingVouCher.setNgayKetThuc(updateVouCher.getNgayKetThuc());
            existingVouCher.setTrangThai(updateVouCher.getTrangThai());
            existingVouCher.setUpdateDate(updateVouCher.getUpdateDate());

            // Lưu voucher đã được cập nhật
            vouCherRepository.save(existingVouCher);

            return "redirect:/voucher/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật VouCher.");
            return "redirect:/voucher/detail";
        }
    }

}
