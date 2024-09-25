package com.example.datn.controller;
import com.example.datn.Repository.DanhMucRepository;
import com.example.datn.entity.DanhMucEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Controller
public class DanhMucController {
    @Autowired
    DanhMucRepository danhMucRepository;

    @GetMapping("/danhmuc/getAll")
    public String getAllDanhMuc(Model model) {
        List<DanhMucEntity> danhMucList = danhMucRepository.findAll();
        model.addAttribute("danhMucList", danhMucList);
        model.addAttribute("danhMuc", new DanhMucEntity());
        return "admin/adminWeb/DanhMuc";
    }
    @PostMapping("/danhmuc/add")
    public String addChatLieu(@Valid @ModelAttribute("danhMuc") DanhMucEntity danhMuc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<DanhMucEntity> danhMucList = danhMucRepository.findAll();
            model.addAttribute("danhMucList", danhMucList);
            return "admin/adminWeb/DanhMuc";
        }
//        chatLieu.setId(UUID.randomUUID());
        try {
            danhMucRepository.save(danhMuc);
            return "redirect:/danhmuc/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/danhmuc/delete/{id}")
    public String deleteDanhMuc(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!danhMucRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy Chất liệu này");
                List<DanhMucEntity> danhMucList = danhMucRepository.findAll();
                model.addAttribute("danhMucList", danhMucList);
                return "admin/adminWeb/DanhMuc";
            }
            //Xóa
            danhMucRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/danhmuc/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<DanhMucEntity> danhMucList = danhMucRepository.findAll();
            model.addAttribute("danhMucList", danhMucList);
            return "admin/adminWeb/DanhMuc";
        }
    }
    @GetMapping("/danhmuc/detail/{id}")
    public String getChatLieuDetail(@PathVariable("id") UUID id, Model model) {
        Optional<DanhMucEntity> kichCoOptional = danhMucRepository.findById(id);
        if (kichCoOptional.isPresent()) {
            DanhMucEntity danhMuc = kichCoOptional.get();
            model.addAttribute("danhMuc", danhMuc);
            return "admin/adminWeb/DanhMucDetail"; //
        } else {
            model.addAttribute("errorMessage", "Material not found.");
            return "admin/adminWeb/DanhMuc";
        }
    }
    @PostMapping("/danhmuc/update/{id}")
    public String updateKichCo(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") DanhMucEntity updateDanhMuc, Model model) {
        try {
            // Kiểm tra xem chất liệu có tồn tại không
            if (!danhMucRepository.existsById(id)) {
                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
                List<DanhMucEntity> danhMucList = danhMucRepository.findAll();
                model.addAttribute("danhMucList", danhMucList);
                return "admin/adminWeb/DanhMucDetail";
            }

            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
            DanhMucEntity existingdanhMuc = danhMucRepository.findById(id).orElse(null);
            if (existingdanhMuc == null) {
                return "redirect:/danhmuc/getAll";
            }

            // Cập nhật thông tin chất liệu
            existingdanhMuc.setTenDanhMuc(updateDanhMuc.getTenDanhMuc());
            existingdanhMuc.setUpdateDate(updateDanhMuc.getUpdateDate());
            danhMucRepository.save(existingdanhMuc);
            return "redirect:/danhmuc/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
            return "redirect:/danhmuc/detail";
        }
    }
}
