package com.example.datn.controller;

import com.example.datn.Repository.DiaChiRepository;
import com.example.datn.entity.DiaChiEntity;
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
public class DiaChiController {
    @Autowired
    DiaChiRepository diaChiRepository;

    @PostMapping("/diachi/add")
    public String addDiaChi(@Valid @ModelAttribute("diachi") DiaChiEntity diachi, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<DiaChiEntity> diaChiList = diaChiRepository.findAll();
            model.addAttribute("diaChiList", diaChiList);
            return "admin/adminWeb/DiaChi";
        }
        try {
            diaChiRepository.save(diachi);
            System.out.println(diachi);
            return "redirect:/diachi/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //
    @PostMapping("/diachi/delete/{id}")
    public String deleteChatLieu(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!diaChiRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy Chất liệu này");
                List<DiaChiEntity> diaChiList = diaChiRepository.findAll();
                model.addAttribute("diaChiList", diaChiList);
                return "admin/adminWeb/DiaChi";
            }
            //Xóa
            diaChiRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/diachi/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<DiaChiEntity> diaChiList = diaChiRepository.findAll();
            model.addAttribute("diaChiList", diaChiList);
            return "admin/adminWeb/DiaChi";
        }
    }

    @GetMapping("/diachi/detail/{id}")
    public String getDiaChiDetail(@PathVariable("id") UUID id, Model model) {
        Optional<DiaChiEntity> diaChiOptional = diaChiRepository.findById(id);
        if (diaChiOptional.isPresent()) {
            DiaChiEntity diaChi = diaChiOptional.get();
            model.addAttribute("diaChi", diaChi);
            return "admin/adminWeb/DiaChiDetail";
        } else {
            model.addAttribute("errorMessage", "Địa chỉ không tồn tại.");
            return "admin/adminWeb/DiaChi";
        }
    }

    @PostMapping("/diachi/update/{id}")
    public String updateChatLieu(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") DiaChiEntity updateDiaChi, Model model) {
        try {
            // Kiểm tra xem chất liệu có tồn tại không
            if (!diaChiRepository.existsById(id)) {
                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
                List<DiaChiEntity> diaChiList = diaChiRepository.findAll();
                model.addAttribute("diaChiList", diaChiList);
                return "admin/adminWeb/DiaChi";
            }

            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
            DiaChiEntity existingdiaChi = diaChiRepository.findById(id).orElse(null);
            if (existingdiaChi == null) {
                return "redirect:/diachi/getAll";
            }


            // Cập nhật thông tin chất liệu
            existingdiaChi.setId(updateDiaChi.getId());

            existingdiaChi.setDiaChi(updateDiaChi.getDiaChi());
            existingdiaChi.setTinh(updateDiaChi.getTinh());
            existingdiaChi.setDiaChi(updateDiaChi.getDiaChi());
            existingdiaChi.setTrangThai(updateDiaChi.getTrangThai());

            diaChiRepository.save(existingdiaChi);

           diaChiRepository.save(existingdiaChi);
            System.out.println(existingdiaChi);
            return "redirect:/diachi/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
            return "redirect:/diachi/detail";
        }
    }

}

