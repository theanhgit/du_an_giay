package com.example.datn.service.impl;

import com.example.datn.Repository.UserRepository;
import com.example.datn.dto.UserDTO;
import com.example.datn.entity.UserEntity;
import com.example.datn.service.UserService;
import lombok.RequiredArgsConstructor;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserStatusFilter implements Filter {


    private final UserRepository userService;

    @Autowired
    public UserStatusFilter(UserRepository userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();

        // Kiểm tra nếu người dùng đang truy cập vào trang admin
        if (requestURI.startsWith("/admin")) {
            // Nếu chưa có session hoặc session không chứa thông tin user
            if (session == null || session.getAttribute("user") == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dang-nhap");
                return;
            }
        }
        if (requestURI.startsWith("/BanHangOff")||
                requestURI.startsWith("/thongke")|| requestURI.startsWith("/quanly-user")
                ||
                requestURI.startsWith("/voucher")) {
            // Nếu chưa có session hoặc session không chứa thông tin user
            if (session == null || session.getAttribute("user") == null) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dang-nhap");
                return;
            }
        }


        if (session != null && session.getAttribute("user") != null) {
            UserDTO user = (UserDTO) session.getAttribute("user");

            // Tải lại thông tin của user từ cơ sở dữ liệu để đảm bảo trạng thái mới nhất
            Optional<UserEntity> updatedUser = userService.findById(user.getId());

            System.out.println("Quang sss " + updatedUser.get().getId());

            if (updatedUser.get().getTrangThai() == 0) {
                System.out.println("Quang TT " + updatedUser.get().getTrangThai());

                session.invalidate();
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dang-nhap");
                return;
            }

            String userRole = updatedUser.get().getVaiTro().getTenVaiTro(); // Giả sử bạn có phương thức getTenVaiTro() trong entity
            if ("USER".equals(userRole) && (requestURI.startsWith("/admin")
                    || requestURI.startsWith("/BanHangOff")
                    || requestURI.startsWith("/thongke")
                    || requestURI.startsWith("/quanly-user")
                    ||requestURI.startsWith("/voucher"))
             ) {
                session.invalidate();
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/dang-nhap");
                return;
            }



        }

        chain.doFilter(request, response);
    }
}
