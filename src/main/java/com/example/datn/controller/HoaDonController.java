package com.example.datn.controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import com.example.datn.dto.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.datn.Repository.*;
import com.example.datn.entity.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller

public class HoaDonController {
    @GetMapping("/hoa-don")
    public String home(Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "web/hoaDon";
    }

    private final HoaDonRepository hoaDonRepository;
    final UserRepository userRepository;
    final UsersRepository usersRepository;
    final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final ChiTietSPRepository chiTietSPRepository;
    private final HoaDonCTRepository hoaDonCTRepository;
    private final TrangThaiHDRepository trangThaiHDRepository;
    private final VouCherRepository vouCherRepository;

    public HoaDonController(HoaDonRepository hoaDonRepository, UsersRepository usersRepository, UserRepository userRepository, ChiTietSPRepository chiTietSPRepository, HoaDonCTRepository hoaDonCTRepository, TrangThaiHDRepository trangThaiHDRepository, SanPhamChiTietRepository sanPhamChiTietRepository, VouCherRepository vouCherRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.userRepository = userRepository;
        this.usersRepository = usersRepository;
        this.chiTietSPRepository = chiTietSPRepository;
        this.hoaDonCTRepository = hoaDonCTRepository;

        this.trangThaiHDRepository = trangThaiHDRepository;
        this.sanPhamChiTietRepository = sanPhamChiTietRepository;
        this.vouCherRepository = vouCherRepository;
    }

    @GetMapping("/getAll")
    public String getAllHoaDon(Model model,
                               @RequestParam(defaultValue = "0") int pageHDCtt) {
        if (pageHDCtt < 0) {
            pageHDCtt = 0;
        }
        // Phân trang cho từng danh sách hóa đơn
        Pageable pageablectt = PageRequest.of(pageHDCtt, 5); // 5 là số bản ghi mỗi trang
        Page<HoaDonEntity> danhSachHoaDonChuaThanhToan = hoaDonRepository.findHDChuaThanhToan(pageablectt);
        model.addAttribute("danhSachHoaDonChuaThanhToan", danhSachHoaDonChuaThanhToan);
        model.addAttribute("currentPage", pageHDCtt);
        model.addAttribute("totalPages", danhSachHoaDonChuaThanhToan.getTotalPages());
        return "admin/adminWeb/HoaDon";
    }

    @GetMapping("/hoadon/huy")
    public String showHD(Model model, @RequestParam(defaultValue = "0") int pageCancelled) {
        if (pageCancelled < 0) {
            pageCancelled = 0;
        }
        Pageable pageableCancelled = PageRequest.of(pageCancelled, 5); // 5 items per page
        Page<HoaDonEntity> hoaDonHuy = hoaDonRepository.findTTByHoaDon(pageableCancelled);
        model.addAttribute("hoaDonHuy", hoaDonHuy);
        model.addAttribute("currentPage", pageCancelled);
        model.addAttribute("totalPages", hoaDonHuy.getTotalPages());
        return "admin/adminWeb/hoaDonHuy";
    }

    @GetMapping("/hoadon/hoan-thanh")
    public String showHDHT(Model model, @RequestParam(defaultValue = "0") int pageHT) {
        if (pageHT < 0) {
            pageHT = 0;
        }

        Pageable pageableHT = PageRequest.of(pageHT, 5); // 5 items per page
        Page<HoaDonEntity> hoaDonHoanThanh = hoaDonRepository.findHDBYTT(pageableHT);
        hoaDonHoanThanh.forEach(hoaDon -> {
            LocalDate ngayThanhToan = hoaDon.getNgayThanhToan();
            long soNgayChenhLech = ChronoUnit.DAYS.between(ngayThanhToan, LocalDate.now());
            boolean conTrongThoiHanDoi = soNgayChenhLech <= 5;
            hoaDon.setConTrongThoiHanDoi(conTrongThoiHanDoi); // Gán giá trị vào thuộc tính mới
        });
        model.addAttribute("hoaDonHoanThanh", hoaDonHoanThanh);
        model.addAttribute("currentPage", pageHT);
        model.addAttribute("totalPages", hoaDonHoanThanh.getTotalPages());
        return "admin/adminWeb/hoaDonHoanThanh";
    }

    @GetMapping("/hoadon/dang-giao")
    public String showHDDG(Model model, @RequestParam(defaultValue = "0") int pageDG) {
        if (pageDG < 0) {
            pageDG = 0;
        }
        Pageable pageableDG = PageRequest.of(pageDG, 5); // 5 items per page
        Page<HoaDonEntity> hoaDonDangGiao = hoaDonRepository.findDangGiao(pageableDG);
        model.addAttribute("hoaDonDangGiao", hoaDonDangGiao);
        model.addAttribute("currentPage", pageDG);
        model.addAttribute("totalPages", hoaDonDangGiao.getTotalPages());
        return "admin/adminWeb/hoaDonDangGiao";
    }

    @GetMapping("/hoadon/cho")
    public String showHDC(Model model, @RequestParam(defaultValue = "0") int pageC) {
        if (pageC < 0) {
            pageC = 0;
        }
        Pageable pageableC = PageRequest.of(pageC, 5); // 5 items per page

        Page<HoaDonEntity> danhSachHoaDonCho = hoaDonRepository.findHoaDonsByTrangThai(pageableC);

        // Correctly assign the current page
        model.addAttribute("danhSachHoaDonCho", danhSachHoaDonCho);
        model.addAttribute("currentPage", pageC); // Use pageC instead of pageableC
        model.addAttribute("totalPages", danhSachHoaDonCho.getTotalPages());

        return "admin/adminWeb/hoaDonCho";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("hoaDon", new HoaDonEntity());
        return "admin/adminWeb/HoaDon";
    }

    @PostMapping("/create")
    public String createHoaDon(@ModelAttribute("hoaDon") HoaDonEntity hoaDon, RedirectAttributes redirectAttributes) {
        try {
            hoaDon.setCreateDate(LocalDate.now());
            if (hoaDon.getTongTien() == null) {
                hoaDon.setTongTien(BigDecimal.ZERO);
            }
            TrangThaiHDEntity trangThaiChuaThanhToan = trangThaiHDRepository.findByTrangThai("0");
            if (trangThaiChuaThanhToan != null) {
                hoaDon.setTrangThaiHD(trangThaiChuaThanhToan);
            } else {
                redirectAttributes.addFlashAttribute("error", "Trạng thái hóa đơn không hợp lệ.");
                return "redirect:/getAll";
            }

            hoaDonRepository.save(hoaDon);
            redirectAttributes.addFlashAttribute("success", "Tạo hóa đơn thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi tạo hóa đơn: " + e.getMessage());
        }
        return "redirect:/getAll";
    }

    @GetMapping("/hoadon/detail/{id}")
    public String getDiaChiDetail(@PathVariable("id") UUID id, Model model) {
        Optional<HoaDonEntity> hoaDonOptional = hoaDonRepository.findById(id);
        HoaDonEntity hoaDonEntity = hoaDonRepository.findById(id).orElse(null);
        String trangThaiHienTai = hoaDonEntity.getTrangThaiHD().getTrangThai();
        System.out.println("Trang Thai hien tai1: " + trangThaiHienTai);
        if (hoaDonOptional.isPresent()) {
            HoaDonEntity hoaDon = hoaDonOptional.get();
            model.addAttribute("hoaDon", hoaDon);
            return "admin/adminWeb/HoaDonDetail";
        } else {
            model.addAttribute("errorMessage", "Hóa đơn không tồn tại.");
            return "admin/adminWeb/HoaDonDetail";
        }
    }


    @PostMapping("/hoadon/update/{id}")
    public String updateHoaDon(@PathVariable("id") UUID id,
                               @RequestParam("trangThai") String trangThai,
                               RedirectAttributes redirectAttributes) {
        try {
            // Check if the invoice exists
            if (!hoaDonRepository.existsById(id)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Hóa đơn không tồn tại.");
                return "redirect:/hoadon/detail/" + id;
            }

            // Fetch the invoice entity
            HoaDonEntity hoaDonEntity = hoaDonRepository.findById(id).orElse(null);
            if (hoaDonEntity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Hóa đơn không tồn tại.");
                return "redirect:/getAll";
            }
            String trangThaiHienTai = hoaDonEntity.getTrangThaiHD().getTrangThai();
            System.out.println("Trang Thai hien tai: " + trangThaiHienTai);
            if ("4".equals(trangThai)) {
                for (HoaDonChiTietEntity chiTiet : hoaDonEntity.getHoaDonChiTiets()) {
                    SanPhamChiTietEntity sanPhamChiTiet = chiTiet.getSanPhamChiTiet();
                    int soLuong = chiTiet.getSoLuong();
                    System.out.println("Old So Luong: " + sanPhamChiTiet.getSoLuong());
                    sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + soLuong);
                    System.out.println("New So Luong: " + sanPhamChiTiet.getSoLuong());
                    chiTietSPRepository.save(sanPhamChiTiet);
                }
            }

            // Update the invoice status
            TrangThaiHDEntity trangThaiHDEntity = trangThaiHDRepository.findByTrangThai(trangThai);
            if (trangThaiHDEntity == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Trạng thái không hợp lệ.");
                return "redirect:/hoadon/detail/" + id;
            }
            hoaDonEntity.setTrangThaiHD(trangThaiHDEntity);
            hoaDonRepository.save(hoaDonEntity);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái hóa đơn thành công.");
            return "redirect:/getAll";

        } catch (Exception e) {
            // Add error message and redirect in case of an exception
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật hóa đơn.");
            return "redirect:/hoadon/detail/" + id; // Redirect to the detail page with the error message
        }
    }


    @GetMapping("/addToCart/{hoaDonId}")
    public String showAddToCartForm(@PathVariable UUID hoaDonId, Model model) {
        Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
        List<SanPhamChiTietEntity> sanPhamChiTiet = chiTietSPRepository.findAll();
        model.addAttribute("sanPhamChiTiet", sanPhamChiTiet);
        if (!optionalHoaDon.isPresent()) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + hoaDonId);
        }
        List<HoaDonEntity> danhSachHoaDon = hoaDonRepository.findAll();
        model.addAttribute("danhSachHoaDon", danhSachHoaDon);
        return "admin/adminWeb/BanHangOff";
    }

    @PostMapping("/updateDetail")
    public ResponseEntity<String> updateDetail(@RequestParam UUID hoaDonId, @RequestParam UUID sanPhamChiTietId, @RequestParam int soLuong) {
        // Create an instance of HoaDonChiTietId
        HoaDonChiTietId id = new HoaDonChiTietId(hoaDonId, sanPhamChiTietId);
        // Find the detail by id
        Optional<HoaDonChiTietEntity> optionalChiTiet = hoaDonCTRepository.findById(id);
        if (optionalChiTiet.isPresent()) {
            // Update the quantity
            HoaDonChiTietEntity chiTiet = optionalChiTiet.get();
            chiTiet.setSoLuong(soLuong);
            hoaDonCTRepository.save(chiTiet);
            return ResponseEntity.ok("Số lượng đã được cập nhật.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chi tiết hóa đơn không tồn tại.");
        }
    }


    @PostMapping("/deleteDetail/{hoaDonId}/{sanPhamChiTietId}")
    public ResponseEntity<String> deleteDetail(@PathVariable UUID hoaDonId, @PathVariable UUID sanPhamChiTietId) {
        // Create an instance of HoaDonChiTietId
        HoaDonChiTietId id = new HoaDonChiTietId(hoaDonId, sanPhamChiTietId);
        boolean exists = hoaDonCTRepository.existsById(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chi tiết hóa đơn không tồn tại.");
        }

        // Delete the detail
        hoaDonCTRepository.deleteById(id);
        return ResponseEntity.ok("Chi tiết hóa đơn đã được xóa.");
    }


    @GetMapping("/trangThai/{hoaDonId}")
    public ResponseEntity<TrangThaiHoaDonDTO> getInvoiceStatus(@PathVariable UUID hoaDonId) {
        TrangThaiHoaDonDTO trangThaiDTO = hoaDonRepository.findTrangThaiByHoaDonId(hoaDonId);
        if (trangThaiDTO != null) {
            return ResponseEntity.ok(trangThaiDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/sanphamCT/{sanPhamId}")
    public ResponseEntity<Map<String, Object>> layThongTinSanPhamChiTiet(@PathVariable("sanPhamId") UUID sanPhamChiTietId) {
        Map<String, Object> thongTinSanPham = chiTietSPRepository.findByNameSP(sanPhamChiTietId);
        if (!thongTinSanPham.isEmpty()) {
            return ResponseEntity.ok(thongTinSanPham);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getDetails/{hoaDonId}")
    public ResponseEntity<String> GetMapping(@PathVariable("hoaDonId") String hoaDonIdStr) {
        UUID hoaDonId;
        try {
            hoaDonId = UUID.fromString(hoaDonIdStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Định dạng UUID không hợp lệ");
        }

        // Tìm danh sách chi tiết hóa đơn theo ID
        List<HoaDonChiTietEntity> chiTietEntities = hoaDonCTRepository.findByHoaDon_Id(hoaDonId);

        if (chiTietEntities == null || chiTietEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Tạo danh sách DTO
        List<HDCTDTO> chiTietDTOs = chiTietEntities.stream()
                .map(entity -> {
                    // Kiểm tra và lấy thông tin voucher nếu có
                    String tenVoucher = entity.getHoaDon().getVouCher() != null ? entity.getHoaDon().getVouCher().getTen() : "Không có voucher";
                    int phanTramGiam = entity.getHoaDon().getVouCher() != null ? entity.getHoaDon().getVouCher().getPhanTramGiam() : 0;

                    // Tạo DTO và tính toán giá trị daGiam
                    HDCTDTO dto = new HDCTDTO(
                            entity.getSanPhamChiTiet().getId(),
                            entity.getSanPhamChiTiet().getSanPham().getTenSanPham(),
                            entity.getSanPhamChiTiet().getMauSac() != null ? entity.getSanPhamChiTiet().getMauSac().getTen() : "N/A",
                            entity.getSanPhamChiTiet().getKichCo() != null ? entity.getSanPhamChiTiet().getKichCo().getTenKichCo() : "N/A",
                            entity.getSoLuong(),
                            entity.getSanPhamChiTiet().getGiaSanPham(),
                            entity.getThanhTien(),
                            tenVoucher, // Thêm tên voucher
                            phanTramGiam, // Thêm phần trăm giảm giá
                            null
                    );

                    // Tính giá trị daGiam và set vào DTO
                    dto.setGiaSauKhiGiam(dto.getGiaSauKhiGiam());

                    return dto;
                })
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(chiTietDTOs);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi chuyển đổi sang JSON");
        }
    }


    @GetMapping("/getsanpham/{hoaDonId}")
    public ResponseEntity<String> getProductsByHoaDonId(@PathVariable("hoaDonId") String hoaDonIdStr) {
        UUID hoaDonId;
        try {
            hoaDonId = UUID.fromString(hoaDonIdStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Định dạng UUID không hợp lệ");
        }

        // Tìm danh sách chi tiết hóa đơn theo ID
        List<HoaDonChiTietEntity> chiTietEntities = hoaDonCTRepository.findByHoaDon_Id(hoaDonId);

        if (chiTietEntities == null || chiTietEntities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Tạo danh sách DTO
        List<HDCTDTO> chiTietDTOs = chiTietEntities.stream()
                .map(entity -> {
                    // Kiểm tra và lấy thông tin voucher nếu có
                    String tenVoucher = entity.getHoaDon().getVouCher() != null ? entity.getHoaDon().getVouCher().getTen() : "Không có voucher";
                    int phanTramGiam = entity.getHoaDon().getVouCher() != null ? entity.getHoaDon().getVouCher().getPhanTramGiam() : 0;

                    // Tạo DTO và tính toán giá trị daGiam
                    HDCTDTO dto = new HDCTDTO(
                            entity.getSanPhamChiTiet().getId(),
                            entity.getSanPhamChiTiet().getSanPham().getTenSanPham(),
                            entity.getSanPhamChiTiet().getMauSac() != null ? entity.getSanPhamChiTiet().getMauSac().getTen() : "N/A",
                            entity.getSanPhamChiTiet().getKichCo() != null ? entity.getSanPhamChiTiet().getKichCo().getTenKichCo() : "N/A",
                            entity.getSoLuong(),
                            entity.getSanPhamChiTiet().getGiaSanPham(),
                            entity.getThanhTien(),
                            tenVoucher, // Thêm tên voucher
                            phanTramGiam, // Thêm phần trăm giảm giá
                            null
                    );

                    // Tính giá trị daGiam và set vào DTO
                    dto.setGiaSauKhiGiam(dto.getGiaSauKhiGiam());

                    return dto;
                })
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(chiTietDTOs);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi chuyển đổi sang JSON");
        }
    }

    @GetMapping("/getCurrentAndRelated/{idSanPham}/{tenSanPham}")
    public ResponseEntity<String> getCurrentAndRelatedProducts(@PathVariable("idSanPham") UUID idSanPham, @PathVariable("tenSanPham") String tenSanPham) {
        // Call repository method to get current product and related products with quantity purchased
        List<Object[]> sanPhamChiTietList = sanPhamChiTietRepository.findByTenSanPhamAndNotIdWithSoLuong(tenSanPham, idSanPham);

        if (sanPhamChiTietList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy sản phẩm hoặc các sản phẩm liên quan.");
        }

        // Convert result into DTO or JSON
        List<SanPhamDTOO> productDetailDTOList = sanPhamChiTietList.stream()
                .map(result -> {
                    SanPhamChiTietEntity sanPhamChiTiet = (SanPhamChiTietEntity) result[0];
                    Long soLuongDaMua = (Long) result[1];  // May be null

                    // Handle null value by setting to 0 if null
                    int soLuongDaMuaInt = (soLuongDaMua != null) ? soLuongDaMua.intValue() : 0;

                    return convertToDTO(sanPhamChiTiet, soLuongDaMuaInt);
                })
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(productDetailDTOList);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi chuyển đổi sang JSON");
        }
    }

    private SanPhamDTOO convertToDTO(SanPhamChiTietEntity sanPhamChiTiet, int soLuongDaMua) {
            return new SanPhamDTOO(
                    sanPhamChiTiet.getId(),  // ID sản phẩm
                    sanPhamChiTiet.getSanPham().getTenSanPham(),
                    sanPhamChiTiet.getKichCo().getTenKichCo(),
                    sanPhamChiTiet.getMauSac().getTen(),
                    sanPhamChiTiet.getSoLuong(),  // Số lượng hiện tại
                    soLuongDaMua // Số lượng đã mua
            );
        }

    @GetMapping("/getProductDetail/{hoaDonId}/{sanPhamChiTietId}")
    public ResponseEntity<String> getProductDetail(
            @PathVariable("hoaDonId") String hoaDonIdStr,
            @PathVariable("sanPhamChiTietId") String sanPhamChiTietIdStr) {

        UUID hoaDonId;
        UUID sanPhamChiTietId;

        try {
            hoaDonId = UUID.fromString(hoaDonIdStr);
            sanPhamChiTietId = UUID.fromString(sanPhamChiTietIdStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Định dạng UUID không hợp lệ");
        }

        // Tìm chi tiết hóa đơn theo ID
        HoaDonChiTietId primaryKey = new HoaDonChiTietId(hoaDonId, sanPhamChiTietId);
        Optional<HoaDonChiTietEntity> chiTietOptional = hoaDonCTRepository.findById(primaryKey);

        if (!chiTietOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        HoaDonChiTietEntity chiTiet = chiTietOptional.get();
        SanPhamChiTietEntity sanPhamChiTiet = chiTiet.getSanPhamChiTiet();

        ProductDetailDTO productDetailDTO = new ProductDetailDTO(
        );

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(productDetailDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            return new ResponseEntity<>(json, headers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // In ra thông tin chi tiết lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi chuyển đổi sang JSON");
        }
    }

    @PutMapping("/updateProductDetail/{hoaDonId}/{sanPhamCuId}/{sanPhamMoiId}")
    public ResponseEntity<String> updateProductDetail(
            @PathVariable("hoaDonId") String hoaDonIdStr,
            @PathVariable("sanPhamCuId") String sanPhamCuIdStr,
            @PathVariable("sanPhamMoiId") String sanPhamMoiIdStr,
            @RequestBody UpdateProductDetailDTO updateRequest) {

        UUID hoaDonId;
        UUID sanPhamCuId;
        UUID sanPhamMoiId;

        try {
            hoaDonId = UUID.fromString(hoaDonIdStr);
            sanPhamCuId = UUID.fromString(sanPhamCuIdStr);
            sanPhamMoiId = UUID.fromString(sanPhamMoiIdStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Định dạng UUID không hợp lệ");
        }

        // Tìm hóa đơn chi tiết bằng ID sản phẩm cũ
        HoaDonChiTietId primaryKey = new HoaDonChiTietId(hoaDonId, sanPhamCuId);
        Optional<HoaDonChiTietEntity> chiTietOptional = hoaDonCTRepository.findById(primaryKey);

        if (!chiTietOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Lấy thông tin sản phẩm cũ và số lượng hiện tại
        HoaDonChiTietEntity chiTietCu = chiTietOptional.get();
        SanPhamChiTietEntity sanPhamCu = chiTietCu.getSanPhamChiTiet();
        int soLuongSanPhamCu = chiTietCu.getSoLuong();

        // Lấy thông tin sản phẩm mới từ request
        SanPhamChiTietEntity sanPhamMoi = sanPhamChiTietRepository.findById(sanPhamMoiId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm mới không tồn tại."));

        int soLuongSanPhamMoi = updateRequest.getSoLuong(); // Số lượng sản phẩm mới

        // Kiểm tra số lượng đổi
        if (soLuongSanPhamMoi > soLuongSanPhamCu) {
            return ResponseEntity.badRequest().body("Số lượng đổi không được lớn hơn số lượng sản phẩm cũ.");
        }

        // Cập nhật số lượng sản phẩm cũ
        chiTietCu.setSoLuong(soLuongSanPhamCu - soLuongSanPhamMoi);
        hoaDonCTRepository.save(chiTietCu); // Lưu hóa đơn chi tiết đã cập nhật

        // Cập nhật kho cho sản phẩm cũ
        int soLuongTonKhoCu = sanPhamCu.getSoLuong();
        sanPhamCu.setSoLuong(soLuongTonKhoCu + soLuongSanPhamMoi); // Cộng lại số lượng sản phẩm cũ vào kho
        sanPhamChiTietRepository.save(sanPhamCu); // Lưu lại thay đổi

        // Cập nhật kho cho sản phẩm mới
        int soLuongTonKhoMoi = sanPhamMoi.getSoLuong();
        if (soLuongTonKhoMoi < soLuongSanPhamMoi) {
            return ResponseEntity.badRequest().body("Số lượng sản phẩm mới không đủ trong kho.");
        }
        sanPhamMoi.setSoLuong(soLuongTonKhoMoi - soLuongSanPhamMoi); // Giảm số lượng sản phẩm mới
        sanPhamMoi.setDaDoi(true);
        // Tạo hóa đơn chi tiết mới cho sản phẩm mới
        HoaDonChiTietEntity chiTietMoi = new HoaDonChiTietEntity();
        chiTietMoi.setHoaDon(chiTietCu.getHoaDon()); // Gán hóa đơn cũ
        chiTietMoi.setSanPhamChiTiet(sanPhamMoi); // Gán sản phẩm mới
        chiTietMoi.setSoLuong(soLuongSanPhamMoi); // Gán số lượng sản phẩm mới
        chiTietMoi.setThanhTien(sanPhamMoi.getGiaSanPham().multiply(BigDecimal.valueOf(soLuongSanPhamMoi))); // Tính tổng tiền cho sản phẩm mới
        // Lưu hóa đơn chi tiết mới vào cơ sở dữ liệu
        hoaDonCTRepository.save(chiTietMoi);

        // Đánh dấu sản phẩm mới đã đổi
        sanPhamChiTietRepository.save(sanPhamMoi); // Lưu lại sản phẩm mới

        // Cập nhật ghi chú cho việc đổi sản phẩm
        String currentTime = java.time.LocalDateTime.now().toString(); // Lấy thời gian hiện tại
        String ghiChuMoi = "Vào lúc: " + currentTime + ", đã đổi: " + soLuongSanPhamMoi + " sản phẩm: "
                + sanPhamCu.getSanPham().getTenSanPham() + " "+"Màu sắc:"+sanPhamCu.getMauSac().getTen()+""+sanPhamCu.getKichCo().getTenKichCo()
                +" thành: " + soLuongSanPhamMoi + " sản phẩm: "
                + sanPhamMoi.getSanPham().getTenSanPham() + " "+"Màu sắc:"+sanPhamMoi.getMauSac().getTen()+""+sanPhamMoi.getKichCo().getTenKichCo()
                +".";

        // Cập nhật ghi chú trong hóa đơn
        HoaDonEntity hoaDon = chiTietCu.getHoaDon();
        String existingGhiChu = hoaDon.getGhiChu();
        if (existingGhiChu != null && !existingGhiChu.isEmpty()) {
            ghiChuMoi = existingGhiChu + "\n" + ghiChuMoi; // Thêm ghi chú mới vào ghi chú cũ
        }
        hoaDon.setGhiChu(ghiChuMoi);

        // Lưu hóa đơn đã cập nhật
        hoaDonRepository.save(hoaDon);

        // Trả về thông tin đã cập nhật
        return ResponseEntity.ok("Cập nhật sản phẩm chi tiết thành công: Đã đổi "+ soLuongSanPhamMoi + " sản phẩm: "
                + sanPhamCu.getSanPham().getTenSanPham() + " "+"Màu sắc:"+sanPhamCu.getMauSac().getTen()+""+sanPhamCu.getKichCo().getTenKichCo()
                +" thành: " + soLuongSanPhamMoi + " sản phẩm: "
                + sanPhamMoi.getSanPham().getTenSanPham() + " "+"Màu sắc:"+sanPhamMoi.getMauSac().getTen()+""+sanPhamMoi.getKichCo().getTenKichCo()
                +".");
    }
    @PostMapping("/addToCart/{hoaDonId}")
    public String addProductToHoaDon(@PathVariable(required = false) String hoaDonId,
                                     @RequestParam UUID sanPhamChiTietId,
                                     @RequestParam int quantity,
                                     RedirectAttributes redirectAttributes) {
        try {
            if (hoaDonId == null || hoaDonId.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không có ID hóa đơn. Vui lòng chọn hóa đơn trước.");
                return "redirect:/getAll";
            }
            UUID hoaDonUUID;
            try {
                hoaDonUUID = UUID.fromString(hoaDonId);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("errorMessage", " Vui lòng chọn hóa đơn trước");
                return "redirect:/getAll";
            }

            Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonUUID);
            if (!optionalHoaDon.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hóa đơn với ID: " + hoaDonUUID);
                return "redirect:/getAll";
            }
            HoaDonEntity hoaDon = optionalHoaDon.get();

            Optional<SanPhamChiTietEntity> optionalSanPhamChiTiet = chiTietSPRepository.findById(sanPhamChiTietId);
            if (!optionalSanPhamChiTiet.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy sản phẩm chi tiết với ID: " + sanPhamChiTietId);
                return "redirect:/addToCart/" + hoaDonUUID;
            }
            SanPhamChiTietEntity sanPhamChiTiet = optionalSanPhamChiTiet.get();

            if (quantity <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "Số lượng sản phẩm phải là số dương");
                return "redirect:/addToCart/" + hoaDonUUID;
            }

            // Lấy hoặc tạo mới HoaDonChiTietEntity
            HoaDonChiTietId primaryKey = new HoaDonChiTietId(hoaDonUUID, sanPhamChiTietId);
            Optional<HoaDonChiTietEntity> optionalHoaDonChiTiet = hoaDonCTRepository.findById(primaryKey);
            int existingQuantity = 0;
            if (optionalHoaDonChiTiet.isPresent()) {
                existingQuantity = optionalHoaDonChiTiet.get().getSoLuong();
            }

            // Kiểm tra nếu số lượng yêu cầu vượt quá số lượng còn lại trong kho
            if (quantity > sanPhamChiTiet.getSoLuong()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không đủ số lượng để thêm vào hóa đơn");
                return "redirect:/addToCart/" + hoaDonUUID;
            }
            // Cập nhật hoặc tạo mới HoaDonChiTietEntity
            HoaDonChiTietEntity hoaDonChiTiet;
            if (optionalHoaDonChiTiet.isPresent()) {
                // Cập nhật bản ghi đã tồn tại
                hoaDonChiTiet = optionalHoaDonChiTiet.get();
                hoaDonChiTiet.setSoLuong(existingQuantity + quantity);
                hoaDonChiTiet.setThanhTien(sanPhamChiTiet.getGiaSanPham().multiply(BigDecimal.valueOf(existingQuantity + quantity)));
            } else {
                // Tạo mới bản ghi
                hoaDonChiTiet = new HoaDonChiTietEntity();
                hoaDonChiTiet.setHoaDon(hoaDon);
                hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
                hoaDonChiTiet.setSoLuong(quantity);
                hoaDonChiTiet.setThanhTien(sanPhamChiTiet.getGiaSanPham().multiply(BigDecimal.valueOf(quantity)));
                hoaDonChiTiet.setCreateDate(LocalDate.now());
            }
            // Lưu hoặc cập nhật HoaDonChiTietEntity
            hoaDonCTRepository.save(hoaDonChiTiet);

            // Giảm số lượng sản phẩm trong kho (chỉ sau khi đã lưu vào HoaDonChiTiet)
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - quantity);

            chiTietSPRepository.save(sanPhamChiTiet);

            // Cập nhật tổng tiền trong HoaDonEntity
            BigDecimal tongTien = hoaDon.getTongTien().add(sanPhamChiTiet.getGiaSanPham().multiply(BigDecimal.valueOf(quantity)));
            hoaDon.setTongTien(tongTien);
            hoaDonRepository.save(hoaDon);

            redirectAttributes.addFlashAttribute("successMessage", "Đã thêm sản phẩm vào hóa đơn thành công");
            return "redirect:/addToCart/" + hoaDonUUID;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            return "redirect:/addToCart/" + hoaDonId;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteHoaDon(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(id);
        if (!optionalHoaDon.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Hóa đơn này không tồn tại");
            return "redirect:/getAll";
        }
        HoaDonEntity hoaDon = optionalHoaDon.get();

        // Retrieve all HoaDonChiTietEntities for this HoaDon
        List<HoaDonChiTietEntity> hoaDonChiTietList = hoaDonCTRepository.findByHoaDonId(id);

        // Cộng lại số lượng sản phẩm cho tất cả các chi tiết hóa đơn
        for (HoaDonChiTietEntity chiTiet : hoaDonChiTietList) {
            SanPhamChiTietEntity sanPhamChiTiet = chiTiet.getSanPhamChiTiet();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + chiTiet.getSoLuong());
            chiTietSPRepository.save(sanPhamChiTiet);
        }
        hoaDonCTRepository.deleteAll(hoaDonChiTietList);
        hoaDonRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("success", "Hóa đơn đã được xóa thành công và số lượng sản phẩm đã được cập nhật.");
        return "redirect:/getAll";
    }


    @PostMapping("/sanphamct/delete/{hoaDonId}/{sanPhamCTid}")
    public ResponseEntity<Map<String, String>> deleteSanPhamCT(@PathVariable("hoaDonId") UUID hoaDonId,
                                                               @PathVariable("sanPhamCTid") UUID sanPhamCTid) {
        Map<String, String> response = new HashMap<>();
        try {
            // Check if the invoice exists
            Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
            if (!optionalHoaDon.isPresent()) {
                response.put("message", "Không tìm thấy hóa đơn với ID: " + hoaDonId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            HoaDonEntity hoaDon = optionalHoaDon.get();

            // Check if the product detail exists in the invoice
            HoaDonChiTietId primaryKey = new HoaDonChiTietId(hoaDonId, sanPhamCTid);
            Optional<HoaDonChiTietEntity> optionalHoaDonChiTiet = hoaDonCTRepository.findById(primaryKey);
            if (!optionalHoaDonChiTiet.isPresent()) {
                response.put("message", "Không tìm thấy sản phẩm chi tiết với ID: " + sanPhamCTid + " trong hóa đơn này");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            HoaDonChiTietEntity hoaDonChiTiet = optionalHoaDonChiTiet.get();
            // Update the product quantity in stock
            SanPhamChiTietEntity sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + hoaDonChiTiet.getSoLuong());
            chiTietSPRepository.save(sanPhamChiTiet);
            // Delete the product detail from the invoice
            hoaDonCTRepository.delete(hoaDonChiTiet);
            // Update the total amount in the invoice
            BigDecimal tongTienMoi = hoaDon.getTongTien().subtract(hoaDonChiTiet.getThanhTien());
            hoaDon.setTongTien(tongTienMoi);
            hoaDonRepository.save(hoaDon);

            response.put("message", "Sản phẩm chi tiết đã được xóa thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi, vui lòng thử lại");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

//    @PostMapping("/trahang/{hoaDonId}/{sanPhamCTid}")
//    public ResponseEntity<Map<String, String>> traHang(@PathVariable("hoaDonId") UUID hoaDonId,
//                                                       @PathVariable("sanPhamCTid") UUID sanPhamCTid,
//                                                       @RequestBody Map<String, Integer> requestBody) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            // Lấy số lượng trả từ request
//            int soLuongTra = requestBody.get("soLuongTra");
//            if (soLuongTra <= 0) {
//                response.put("message", "Số lượng trả không hợp lệ");
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//            }
//
//            // Tìm kiếm hóa đơn
//            Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
//            if (!optionalHoaDon.isPresent()) {
//                response.put("message", "Không tìm thấy hóa đơn với ID: " + hoaDonId);
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//            HoaDonEntity hoaDon = optionalHoaDon.get();
//
//            // Tìm kiếm chi tiết hóa đơn
//            HoaDonChiTietId primaryKey = new HoaDonChiTietId(hoaDonId, sanPhamCTid);
//            Optional<HoaDonChiTietEntity> optionalHoaDonChiTiet = hoaDonCTRepository.findById(primaryKey);
//            if (!optionalHoaDonChiTiet.isPresent()) {
//                response.put("message", "Không tìm thấy sản phẩm chi tiết với ID: " + sanPhamCTid + " trong hóa đơn này");
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//            }
//
//            HoaDonChiTietEntity hoaDonChiTiet = optionalHoaDonChiTiet.get();
//            int soLuongDaMua = hoaDonChiTiet.getSoLuong();
//
//            // Kiểm tra nếu số lượng trả vượt quá số lượng đã mua
//            if (soLuongTra > soLuongDaMua) {
//                response.put("message", "Số lượng trả vượt quá số lượng đã mua");
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//            }
//
//            // Cập nhật lại tồn kho sản phẩm
//            SanPhamChiTietEntity sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
//            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + soLuongTra);
//            chiTietSPRepository.save(sanPhamChiTiet);
//
//            // Cập nhật lại số lượng sản phẩm chi tiết trong hóa đơn
//            if (soLuongTra == soLuongDaMua) {
//                // Nếu trả hết, xóa sản phẩm khỏi hóa đơn
//                hoaDonCTRepository.delete(hoaDonChiTiet);
//            } else {
//                // Nếu trả một phần, cập nhật số lượng còn lại
//                hoaDonChiTiet.setSoLuong(soLuongDaMua - soLuongTra);
//                // Cập nhật lại thành tiền theo số lượng còn lại
//                hoaDonChiTiet.setThanhTien(hoaDonChiTiet.getThanhTien().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
//                hoaDonCTRepository.save(hoaDonChiTiet);
//            }
//
//            // Cập nhật tổng tiền của hóa đơn
//            BigDecimal thanhTienTra = hoaDonChiTiet.getSanPhamChiTiet().getGiaSanPham().multiply(BigDecimal.valueOf(soLuongTra));
//            hoaDon.setTongTien(hoaDon.getTongTien().subtract(thanhTienTra));
//
//            // Cập nhật trạng thái hóa đơn thành "Đã trả hàng"
//            TrangThaiHDEntity trangThaiDaTraHang = trangThaiHDRepository.findByTen("Đã Thanh Toán");
//
//            // Lấy thời gian hiện tại
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//            String currentTime = now.format(dateFormatter);
//
//            // Ghi lại thông tin về sản phẩm đã trả vào ghi chú
//            // Tạo ghi chú mới
//            String ghiChuMoi = " vào lúc: " + currentTime + " Đã trả :" + soLuongTra + " sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham()
//                    + " size: " + sanPhamChiTiet.getKichCo().getTenKichCo()
//                    + " màu sắc: " + sanPhamChiTiet.getMauSac().getTen()
//                    + "\n";
//
//            if (hoaDon.getGhiChu() != null) {
//                hoaDon.setGhiChu(hoaDon.getGhiChu() + "\n" + ghiChuMoi);
//            } else {
//                hoaDon.setGhiChu(ghiChuMoi);
//            }
//            hoaDonRepository.save(hoaDon);
//
//
//            hoaDonRepository.save(hoaDon);
//
//            response.put("message", "Đã trả hàng thành công");
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            response.put("message", "Đã xảy ra lỗi, vui lòng thử lại");
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//        }
//    }
@PostMapping("/trahang/{hoaDonId}/{sanPhamCTid}")
public ResponseEntity<Map<String, String>> traHang(@PathVariable("hoaDonId") UUID hoaDonId,
                                                   @PathVariable("sanPhamCTid") UUID sanPhamCTid,
                                                   @RequestBody Map<String, Integer> requestBody) {
    Map<String, String> response = new HashMap<>();
    try {
        // Lấy số lượng trả từ request
        int soLuongTra = requestBody.get("soLuongTra");
        if (soLuongTra <= 0) {
            response.put("message", "Số lượng trả không hợp lệ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Tìm kiếm hóa đơn
        Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
        if (!optionalHoaDon.isPresent()) {
            response.put("message", "Không tìm thấy hóa đơn với ID: " + hoaDonId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        HoaDonEntity hoaDon = optionalHoaDon.get();

        // Tìm kiếm chi tiết hóa đơn
        HoaDonChiTietId primaryKey = new HoaDonChiTietId(hoaDonId, sanPhamCTid);
        Optional<HoaDonChiTietEntity> optionalHoaDonChiTiet = hoaDonCTRepository.findById(primaryKey);
        if (!optionalHoaDonChiTiet.isPresent()) {
            response.put("message", "Không tìm thấy sản phẩm chi tiết với ID: " + sanPhamCTid + " trong hóa đơn này");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        HoaDonChiTietEntity hoaDonChiTiet = optionalHoaDonChiTiet.get();
        int soLuongDaMua = hoaDonChiTiet.getSoLuong();

        // Kiểm tra nếu số lượng trả vượt quá số lượng đã mua
        if (soLuongTra > soLuongDaMua) {
            response.put("message", "Số lượng trả vượt quá số lượng đã mua");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Cập nhật lại tồn kho sản phẩm
        SanPhamChiTietEntity sanPhamChiTiet = hoaDonChiTiet.getSanPhamChiTiet();
        sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + soLuongTra);
        chiTietSPRepository.save(sanPhamChiTiet);

        // Tính toán giá trị hoàn trả
        BigDecimal giaBan = hoaDonChiTiet.getSanPhamChiTiet().getGiaSanPham();
        BigDecimal thanhTien = hoaDonChiTiet.getThanhTien();
        BigDecimal giaSauGiam = giaBan.multiply(BigDecimal.valueOf(1 - (hoaDon.getVouCher() != null ? hoaDon.getVouCher().getPhanTramGiam() : 0) / 100.0));

        BigDecimal thanhTienTra = giaSauGiam.multiply(BigDecimal.valueOf(soLuongTra));

        // Cập nhật tổng tiền của hóa đơn
        hoaDon.setTongTien(hoaDon.getTongTien().subtract(thanhTienTra));

        // Cập nhật trạng thái hóa đơn thành "Đã trả hàng"
        TrangThaiHDEntity trangThaiDaTraHang = trangThaiHDRepository.findByTen("Đã Thanh Toán");

        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String currentTime = now.format(dateFormatter);

        // Ghi lại thông tin về sản phẩm đã trả vào ghi chú
        String ghiChuMoi = " vào lúc: " + currentTime + " Đã trả :" + soLuongTra + " sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham()
                + " size: " + sanPhamChiTiet.getKichCo().getTenKichCo()
                + " màu sắc: " + sanPhamChiTiet.getMauSac().getTen()
                + "\n";

        if (hoaDon.getGhiChu() != null) {
            hoaDon.setGhiChu(hoaDon.getGhiChu() + "\n" + ghiChuMoi);
        } else {
            hoaDon.setGhiChu(ghiChuMoi);
        }
        hoaDonRepository.save(hoaDon);

        // Cập nhật lại số lượng sản phẩm chi tiết trong hóa đơn
        if (soLuongTra == soLuongDaMua) {
            // Nếu trả hết, xóa sản phẩm khỏi hóa đơn
            hoaDonCTRepository.delete(hoaDonChiTiet);
        } else {
            // Nếu trả một phần, cập nhật số lượng còn lại
            hoaDonChiTiet.setSoLuong(soLuongDaMua - soLuongTra);
            // Cập nhật lại thành tiền theo số lượng còn lại
            hoaDonChiTiet.setThanhTien(hoaDonChiTiet.getThanhTien().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
            hoaDonCTRepository.save(hoaDonChiTiet);
        }

        // Cập nhật tổng tiền của hóa đơn
        hoaDon.setTongTien(hoaDon.getTongTien().subtract(thanhTienTra));

        response.put("message", "Đã trả hàng thành công");
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        response.put("message", "Đã xảy ra lỗi, vui lòng thử lại");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

    @PostMapping("/updateTrangThai/{hoaDonId}")
    public ResponseEntity<Map<String, String>> updateTrangThai(@PathVariable("hoaDonId") UUID hoaDonId) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
            if (!optionalHoaDon.isPresent()) {
                response.put("message", "Không tìm thấy hóa đơn với ID: " + hoaDonId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            HoaDonEntity hoaDon = optionalHoaDon.get();
//            hoaDon.setTrangThai(hoaDon.getTrangThai() + 1);
            hoaDonRepository.save(hoaDon);

            response.put("message", "Trạng thái hóa đơn đã được cập nhật thành công");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Đã xảy ra lỗi, vui lòng thử lại");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/payment/{hoaDonId}")
    public String showPaymentForm(@PathVariable("hoaDonId") UUID hoaDonId, Model model, RedirectAttributes redirectAttributes) {
        Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
        if (optionalHoaDon.isEmpty()) {
            return "redirect:/getAll";
        }
        HoaDonEntity hoaDon = optionalHoaDon.get();
        List<HoaDonChiTietEntity> chiTietHoaDon = hoaDon.getHoaDonChiTiets();

        // Kiểm tra nếu hóa đơn không có sản phẩm
        if (chiTietHoaDon.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hóa đơn không có sản phẩm để thanh toán");
            return "redirect:/getAll";
        }
        BigDecimal tongTien = hoaDon.getTongTien();
        System.out.println("Tổng tiền của hóa đơn " + hoaDonId + ": " + tongTien);
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("tongTien", hoaDon.getTongTien());

        return "admin/adminWeb/ThanhToan";
    }

@PostMapping("/payment/{hoaDonId}")
public String processPayment(
        @PathVariable("hoaDonId") UUID hoaDonId,
        @RequestParam(required = false) String tenKhachHang,
        @RequestParam(required = false) String sdt,
        @RequestParam(required = false) UUID voucherId, // Tham số cho ID voucher
        RedirectAttributes redirectAttributes) {
    try {
        // Tìm kiếm HoaDonEntity theo ID
        Optional<HoaDonEntity> optionalHoaDon = hoaDonRepository.findById(hoaDonId);
        if (optionalHoaDon.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy hóa đơn với ID: " + hoaDonId);
            return "redirect:/getAll";
        }
        HoaDonEntity hoaDon = optionalHoaDon.get();

        // Tìm kiếm TrangThaiHDEntity theo tên "Đã Thanh Toán"
        Optional<TrangThaiHDEntity> daThanhToanOpt = Optional.ofNullable(trangThaiHDRepository.findByTrangThai("1"));
        if (daThanhToanOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Trạng thái 'Đã Thanh Toán' không tìm thấy.");
            return "redirect:/getAll";
        }
        TrangThaiHDEntity daThanhToan = daThanhToanOpt.get();

        // Lấy tổng số tiền hóa đơn ban đầu
        BigDecimal originalTotal = hoaDon.getTongTien();
        System.out.println("Giá tiền ban đầu: " + originalTotal);

        BigDecimal discountedTotal = originalTotal; // Tổng tiền sau giảm giá

        // Kiểm tra voucher nếu voucherId không null
        if (voucherId != null) {
            Optional<VouCherEntity> voucherOpt = vouCherRepository.findById(voucherId);
            if (voucherOpt.isPresent()) {
                VouCherEntity voucher = voucherOpt.get();
                int discountPercentage = voucher.getPhanTramGiam();

                System.out.println("ID Voucher đã chọn: " + voucherId);
                System.out.println("Phần trăm giảm: " + discountPercentage);

                // Tính số tiền giảm giá
                BigDecimal discountAmount = originalTotal.multiply(BigDecimal.valueOf(discountPercentage)).divide(BigDecimal.valueOf(100));
                System.out.println("Giá trị giảm giá: " + discountAmount);

                discountedTotal = originalTotal.subtract(discountAmount); // Tính tổng tiền đã giảm
                System.out.println("Tổng tiền sau khi giảm: " + discountedTotal);

                // Cập nhật voucher vào hóa đơn
                hoaDon.setVouCher(voucher); // Cập nhật voucher vào hóa đơn
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Voucher không hợp lệ.");
                return "redirect:/getAll";
            }
        }

        // Cập nhật thông tin vào hóa đơn
        hoaDon.setTongTien(discountedTotal); // Cập nhật tổng tiền đã giảm
        hoaDon.setTrangThaiHD(daThanhToan); // Cập nhật trạng thái hóa đơn thành 'Đã Thanh Toán'
        hoaDon.setNgayThanhToan(LocalDate.now()); // Thiết lập ngày thanh toán
        hoaDonRepository.save(hoaDon); // Lưu hóa đơn vào cơ sở dữ liệu

        // Xử lý khách hàng
        UserEntity userEntity;
        if (tenKhachHang == null || tenKhachHang.trim().isEmpty()) {
            userEntity = userRepository.findByTen("Khách lẻ").orElseGet(() -> {
                UserEntity defaultUser = new UserEntity();
                defaultUser.setTen("Khách lẻ");
                defaultUser.setTrangThai(0);
                return userRepository.save(defaultUser);
            });
        } else {
            List<UserEntity> userEntities = userRepository.findByTenAndSdt(tenKhachHang, sdt);
            if (userEntities.isEmpty()) {
                userEntity = new UserEntity();
                userEntity.setTaiKhoan("");
                userEntity.setNgaySinh(LocalDate.parse("2024-01-01")); // Thiết lập ngày sinh mặc định
                userEntity.setHo("/");
                userEntity.setTenDem("/");
                userEntity.setTen(tenKhachHang);
                userEntity.setSdt(sdt);
                userEntity.setGioiTinh(1);
                userEntity.setTrangThai(1);
                userEntity = userRepository.save(userEntity);
            } else if (userEntities.size() == 1) {
                userEntity = userEntities.get(0);
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Có nhiều khách hàng với cùng tên và số điện thoại.");
                return "redirect:/getAll";
            }
        }
        hoaDon.setUser(userEntity); // Cập nhật thông tin người dùng
        hoaDonRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật

        redirectAttributes.addFlashAttribute("successMessage", "Đã thanh toán hóa đơn thành công");
    } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Chuỗi UUID không hợp lệ cho hoaDonId");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi, vui lòng thử lại: " + e.getMessage());
    }
    return "redirect:/hoadon/hoan-thanh";
}


}