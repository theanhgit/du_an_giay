package com.example.datn.controller;
import com.example.datn.Repository.NSXRepository;
import com.example.datn.entity.NSXEntity;
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
public class NSXController {
    @Autowired
    NSXRepository nsxRepository;

    @GetMapping("/nsx/getAll")
    public String getAllNSX(Model model) {
        List<NSXEntity> listNSX = nsxRepository.findAll();
        model.addAttribute("listNSX", listNSX);
        model.addAttribute("nsx", new NSXEntity());
        return "admin/adminWeb/NSX";
    }
    @PostMapping("/nsx/add")
    public String addNSX(@Valid @ModelAttribute("nsx") NSXEntity nsx, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<NSXEntity> listNSX = nsxRepository.findAll();
            model.addAttribute("listNSX", listNSX);
            return "admin/adminWeb/NSX";
        }
//        chatLieu.setId(UUID.randomUUID());
        try {
            nsxRepository.save(nsx);
            return "redirect:/nsx/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/nsx/delete/{id}")
    public String deleteNSX(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!nsxRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy Chất liệu này");
                List<NSXEntity> listNSX = nsxRepository.findAll();
                model.addAttribute("listNSX", listNSX);
                return "admin/adminWeb/NSX";
            }
            //Xóa
            nsxRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/nsx/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<NSXEntity> listNSX = nsxRepository.findAll();
            model.addAttribute("listNSX", listNSX);
            return "admin/adminWeb/NSX";
        }
    }
    @GetMapping("/nsx/detail/{id}")
    public String getNSXDetail(@PathVariable("id") UUID id, Model model) {
        Optional<NSXEntity> kichCoOptional = nsxRepository.findById(id);
        if (kichCoOptional.isPresent()) {
            NSXEntity nsx = kichCoOptional.get();
            model.addAttribute("nsx", nsx);
            return "admin/adminWeb/NSXDetail"; //
        } else {
            model.addAttribute("errorMessage", "Material not found.");
            return "admin/adminWeb/NSX";// Hoặc trả về trang danh sách nếu không tìm thấy đối tượng
        }
    }
    @PostMapping("/nsx/update/{id}")
    public String updateKichCo(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") NSXEntity updateNSX, Model model) {
        try {
            // Kiểm tra xem chất liệu có tồn tại không
            if (!nsxRepository.existsById(id)) {
                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
                List<NSXEntity> listNSX = nsxRepository.findAll();
                model.addAttribute("listNSX", listNSX);
                return "admin/adminWeb/NSXDetail";
            }

            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
            NSXEntity existingNSX = nsxRepository.findById(id).orElse(null);
            if (existingNSX == null) {
                return "redirect:/nsx/getAll";
            }

            // Cập nhật thông tin chất liệu
            existingNSX.setTen(updateNSX.getTen());
            existingNSX.setTrangThai(updateNSX.getTrangThai());
            nsxRepository.save(existingNSX);
            return "redirect:/nsx/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
            return "redirect:/nsx/detail";
        }
    }
}
