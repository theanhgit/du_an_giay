package com.example.datn.controller;

import com.example.datn.Repository.ChatLieuRepository;
import com.example.datn.entity.ChatLieuEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ChatLieuController {
    @Autowired
    ChatLieuRepository chatLieuRepository;

    @GetMapping("/chatlieu/getAll")
    public String getAllChatLieu(Model model) {
        List<ChatLieuEntity> chatLieuList = chatLieuRepository.findAll();
        model.addAttribute("chatLieuList", chatLieuList);
        model.addAttribute("chatLieu", new ChatLieuEntity());
        return "admin/adminWeb/ChatLieu";
    }
    @PostMapping("/chatlieu/add")
    public String addChatLieu(@Valid @ModelAttribute("chatLieu") ChatLieuEntity chatLieu, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ChatLieuEntity> chatLieuList = chatLieuRepository.findAll();
            model.addAttribute("chatLieuList", chatLieuList);
            return "admin/adminWeb/ChatLieu";
        }
//        chatLieu.setId(UUID.randomUUID());
        try {
            chatLieuRepository.save(chatLieu);
            return "redirect:/chatlieu/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/chatlieu/delete/{id}")
    public String deleteChatLieu(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!chatLieuRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy Chất liệu này");
                List<ChatLieuEntity> chatLieuList = chatLieuRepository.findAll();
                model.addAttribute("chatLieuList", chatLieuList);
                return "admin/adminWeb/ChatLieu";
            }
            //Xóa
            chatLieuRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/chatlieu/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<ChatLieuEntity> chatLieuList = chatLieuRepository.findAll();
            model.addAttribute("chatLieuList", chatLieuList);
            return "admin/adminWeb/ChatLieu";
        }
    }
    @GetMapping("/chatlieu/detail/{id}")
    public String getChatLieuDetail(@PathVariable("id") UUID id, Model model) {
        Optional<ChatLieuEntity> chatLieuOptional = chatLieuRepository.findById(id);
        if (chatLieuOptional.isPresent()) {
            ChatLieuEntity chatLieu = chatLieuOptional.get();
            model.addAttribute("chatLieu", chatLieu);
            return "admin/adminWeb/ChatLieuDetail"; //
        } else {
            model.addAttribute("errorMessage", "Material not found.");
            return "admin/adminWeb/ChatLieu";
        }
    }
    @PostMapping("/chatlieu/update/{id}")
    public String updateChatLieu(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") ChatLieuEntity updatedChatLieu, Model model) {
        try {
            // Kiểm tra xem chất liệu có tồn tại không
            if (!chatLieuRepository.existsById(id)) {
                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
                List<ChatLieuEntity> chatLieuList = chatLieuRepository.findAll();
                model.addAttribute("chatLieuList", chatLieuList);
                return "admin/adminWeb/ChatLieuDetail";
            }

            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
            ChatLieuEntity existingChatLieu = chatLieuRepository.findById(id).orElse(null);
            if (existingChatLieu == null) {
                return "redirect:/chatlieu/getAll";
            }

            // Cập nhật thông tin chất liệu
            existingChatLieu.setId(updatedChatLieu.getId());
            existingChatLieu.setTen(updatedChatLieu.getTen());
//            existingChatLieu.setMoTa(updatedChatLieu.getMoTa());
            existingChatLieu.setUpdateDate(updatedChatLieu.getUpdateDate());
            existingChatLieu.setTrangThai(updatedChatLieu.getTrangThai());
            chatLieuRepository.save(existingChatLieu);
            System.out.println(existingChatLieu);
            return "redirect:/chatlieu/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
            return "redirect:/chatlieu/detail";
        }
    }

}
