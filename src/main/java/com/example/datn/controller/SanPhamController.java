
//package com.example.datn.controller;
//
//import com.example.datn.Repository.SanPhamRepository;
//import com.example.datn.entity.SanPhamEntity;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//@Controller
//public class SanPhamRestController {
//    @Autowired
//    SanPhamRepository sanPhamRepository;
//
//    @GetMapping("/sanpham/getAll")
//    public String getAllSanPham(@RequestParam(defaultValue = "0") int page,
//                            @RequestParam(defaultValue = "5") int size,
//                            Model model) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<SanPhamEntity> listSanPham = sanPhamRepository.findAll(pageable);
//        model.addAttribute("listSanPham", listSanPham);
//        model.addAttribute("sanPham", new SanPhamEntity());
//        return "admin/adminWeb/SanPham";
//    }
//    @PostMapping("/sanpham/add")
//    public String addChatLieu(@Valid @ModelAttribute("sanPham") SanPhamEntity sanPham, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
//            model.addAttribute("sanPhamList", sanPhamList);
//            return "admin/adminWeb/SanPham";
//        }
//        try {
//            sanPhamRepository.save(sanPham);
//            return "redirect:/sanpham/getAll";
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    @PostMapping("/sanpham/delete/{id}")
//    public String deleteSanPham(@PathVariable("id") UUID id, Model model) {
//        try {
//            //Kiểm tra xem nó có tồn tại không
//            if (!sanPhamRepository.existsById(id)) {
//                // Nếu không tồn tại
//                model.addAttribute("errorMessage", "Không tìm thấy Chất liệu này");
//                List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
//                model.addAttribute("sanPhamList", sanPhamList);
//                return "admin/adminWeb/sanPham";
//            }
//            //Xóa
//            sanPhamRepository.deleteById(id);
//            // Sau khi xóa thành công quay lại trang getAll
//            return "redirect:/sanpham/getAll";
//        } catch (Exception e) {
//            // Trả ra thông báo nếu có ngoại lệ
//            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
//            List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
//            model.addAttribute("sanPhamList", sanPhamList);
//            return "admin/adminWeb/SanPham";
//        }
//    }
//    @GetMapping("/sanpham/detail/{id}")
//    public String getSanPhamDetail(@PathVariable("id") UUID id, Model model) {
//        Optional<SanPhamEntity> chatLieuOptional = sanPhamRepository.findById(id);
//        if (chatLieuOptional.isPresent()) {
//            SanPhamEntity sanPham = chatLieuOptional.get();
//            model.addAttribute("sanPham", sanPham);
//            return "admin/adminWeb/SanPhamDetail"; //
//        } else {
//            model.addAttribute("errorMessage", "Material not found.");
//            return "admin/adminWeb/SanPham";// Hoặc trả về trang danh sách nếu không tìm thấy đối tượng
//        }
//    }
//    @PostMapping("/sanpham/update/{id}")
//    public String updateChatLieu(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") SanPhamEntity updatedChatLieu, Model model) {
//        try {
//            // Kiểm tra xem chất liệu có tồn tại không
//            if (!sanPhamRepository.existsById(id)) {
//                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
//                List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
//                model.addAttribute("sanPhamList", sanPhamList);
//                return "admin/adminWeb/SanPhamDetail";
//            }
//
//            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
//            SanPhamEntity existingSanPham = sanPhamRepository.findById(id).orElse(null);
//            if (existingSanPham == null) {
//                return "redirect:/chatlieu/getAll";
//            }
//
//            // Cập nhật thông tin chất liệu
//            existingSanPham.setTenSanPham(updatedChatLieu.getTenSanPham());
//            existingSanPham.setTrangThai(updatedChatLieu.getTrangThai());
//            existingSanPham.setUpdateDate(updatedChatLieu.getUpdateDate());
//            sanPhamRepository.save(existingSanPham);
//            System.out.println(existingSanPham);
//            return "redirect:/sanpham/getAll";
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
//            return "redirect:/sanpham/detail";
//        }
//    }
//}
//
package com.example.datn.controller;

import com.example.datn.Repository.SanPhamRepository;
import com.example.datn.entity.SanPhamEntity;
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
public class SanPhamController {
    @Autowired
    SanPhamRepository sanPhamRepository;

    @GetMapping("/sanpham/getAll")
    public String getAllSanPham(Model model) {
        List<SanPhamEntity> listSanPham = sanPhamRepository.findAll();
        model.addAttribute("listSanPham", listSanPham);
        model.addAttribute("sanPham", new SanPhamEntity());
        return "admin/adminWeb/SanPham";
    }
    @PostMapping("/sanpham/add")
    public String addChatLieu(@Valid @ModelAttribute("sanPham") SanPhamEntity sanPham, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
            model.addAttribute("sanPhamList", sanPhamList);
            return "admin/adminWeb/SanPham";
        }
        try {
            sanPhamRepository.save(sanPham);
            return "redirect:/sanpham/getAll";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/sanpham/delete/{id}")
    public String deleteSanPham(@PathVariable("id") UUID id, Model model) {
        try {
            //Kiểm tra xem nó có tồn tại không
            if (!sanPhamRepository.existsById(id)) {
                // Nếu không tồn tại
                model.addAttribute("errorMessage", "Không tìm thấy Chất liệu này");
                List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
                model.addAttribute("sanPhamList", sanPhamList);
                return "admin/adminWeb/sanPham";
            }
            //Xóa
            sanPhamRepository.deleteById(id);
            // Sau khi xóa thành công quay lại trang getAll
            return "redirect:/chatlieu/getAll";
        } catch (Exception e) {
            // Trả ra thông báo nếu có ngoại lệ
            model.addAttribute("errorMessage", "Đã xảy ra lỗi vui lòng thử lại");
            List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
            model.addAttribute("sanPhamList", sanPhamList);
            return "admin/adminWeb/SanPham";
        }
    }
    @GetMapping("/sanpham/detail/{id}")
    public String getSanPhamDetail(@PathVariable("id") UUID id, Model model) {
        Optional<SanPhamEntity> chatLieuOptional = sanPhamRepository.findById(id);
        if (chatLieuOptional.isPresent()) {
            SanPhamEntity sanPham = chatLieuOptional.get();
            model.addAttribute("sanPham", sanPham);
            return "admin/adminWeb/SanPhamDetail"; //
        } else {
            model.addAttribute("errorMessage", "Material not found.");
            return "admin/adminWeb/SanPham";// Hoặc trả về trang danh sách nếu không tìm thấy đối tượng
        }
    }
    @PostMapping("/sanpham/update/{id}")
    public String updateChatLieu(@PathVariable("id") UUID id, @ModelAttribute("chatLieu") SanPhamEntity updatedChatLieu, Model model) {
        try {
            // Kiểm tra xem chất liệu có tồn tại không
            if (!sanPhamRepository.existsById(id)) {
                model.addAttribute("errorMessage", "Chất liệu không tồn tại.");
                List<SanPhamEntity> sanPhamList = sanPhamRepository.findAll();
                model.addAttribute("sanPhamList", sanPhamList);
                return "admin/adminWeb/SanPhamDetail";
            }

            // Lấy thông tin chất liệu cần cập nhật từ cơ sở dữ liệu
            SanPhamEntity existingSanPham = sanPhamRepository.findById(id).orElse(null);
            if (existingSanPham == null) {
                return "redirect:/chatlieu/getAll";
            }

            // Cập nhật thông tin chất liệu
            existingSanPham.setTenSanPham(updatedChatLieu.getTenSanPham());
            existingSanPham.setTrangThai(updatedChatLieu.getTrangThai());
            existingSanPham.setUpdateDate(updatedChatLieu.getUpdateDate());
            sanPhamRepository.save(existingSanPham);
            System.out.println(existingSanPham);
            return "redirect:/sanpham/getAll";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật chất liệu.");
            return "redirect:/sanpham/detail";
        }
    }
}


