package com.example.datn.controller;

import com.example.datn.Repository.KichCoRepository;
import com.example.datn.entity.KichCoEntity;
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
public class KichCoController {
    @Autowired
    KichCoRepository kichCoRepository;

    @GetMapping("/kichco/getAll")
    public String getAllKichCo(Model model) {
        List<KichCoEntity> kichCoList = kichCoRepository.findAll();
        model.addAttribute("kichCoList", kichCoList);
        model.addAttribute("kichCo", new KichCoEntity());
        return "admin/adminWeb/KichCo";
    }
    @PostMapping("/kichco/add")
    public String addKichCo(@Valid @ModelAttribute("kichCo") KichCoEntity kichCo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<KichCoEntity> kichCoList = kichCoRepository.findAll();
            model.addAttribute("kichCoList", kichCoList);
            return "admin/adminWeb/KichCo";
        }
//        chatLieu.setId(UUID.randomUUID());
        try {
            kichCoRepository.save(kichCo);
            return "redirect:/kichco/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/kichco/delete/{id}")
    public String deleteKichCo(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!kichCoRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy Kích Cỡ này");
                List<KichCoEntity> kichCoList = kichCoRepository.findAll();
                model.addAttribute("kichCoList", kichCoList);
                return "admin/adminWeb/KichCo";
            }
            //Xóa
            kichCoRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/kichco/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<KichCoEntity> kichCoList = kichCoRepository.findAll();
            model.addAttribute("kichCoList", kichCoList);
            return "admin/adminWeb/KichCo";
        }
    }
    @GetMapping("/kichco/detail/{id}")
    public String getChatLieuDetail(@PathVariable("id") UUID id, Model model) {
        Optional<KichCoEntity> kichCoOptional = kichCoRepository.findById(id);
        if (kichCoOptional.isPresent()) {
            KichCoEntity kichCo = kichCoOptional.get();
            model.addAttribute("kichCo", kichCo);
            return "admin/adminWeb/KichCoDetail"; //
        } else {
            model.addAttribute("errorMessage", "Material not found.");
            return "admin/adminWeb/KichCo";// Hoặc trả về trang danh sách nếu không tìm thấy đối tượng
        }
    }
    @PostMapping("/kichco/update/{id}")
    public String updateKichCo(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") KichCoEntity updateKichCo, Model model) {
        try {
            // Kiểm tra xem chất liệu có tồn tại không
            if (!kichCoRepository.existsById(id)) {
                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
                List<KichCoEntity> kichCoList = kichCoRepository.findAll();
                model.addAttribute("kichCoList", kichCoList);
                return "admin/adminWeb/KichCoDetail";
            }

            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
            KichCoEntity existingkichCo = kichCoRepository.findById(id).orElse(null);
            if (existingkichCo == null) {
                return "redirect:/kichco/getAll";
            }
            // Cập nhật thông tin chất liệu
            existingkichCo.setTenKichCo(updateKichCo.getTenKichCo());
            existingkichCo.setDoDai(updateKichCo.getDoDai());
            existingkichCo.setUpdateDate(updateKichCo.getUpdateDate());
            kichCoRepository.save(existingkichCo);
            return "redirect:/kichco/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
            return "redirect:/kichco/detail";
        }
    }
}
