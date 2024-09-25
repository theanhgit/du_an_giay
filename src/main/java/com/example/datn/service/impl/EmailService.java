package com.example.datn.service.impl;


import com.example.datn.Repository.UsersRepository;
import com.example.datn.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
@RequiredArgsConstructor
@Service
public class EmailService {
    private final UsersRepository usersRepository;

    final String username = "quanglmph29882@fpt.edu.vn";
    final String password = "ubsg fmhg anuq fstz";

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public void sendPasswordResetEmail(String taiKhoan, String SDT) {
        try {
            UserEntity user = usersRepository.findByTkAndSDT(taiKhoan, SDT);

            if (user == null) {
                throw new RuntimeException("Tài khoản hoặc số điện thoại không hợp lệ");
            }
            Session session = createSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            System.out.println("Email :"+user.getEmail().toString());
            message.setSubject("Password Reset Request");
            message.setText(": mật khẩu là " + user.getMatKhau());

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            // Xử lý ngoại lệ khi có vấn đề với việc gửi email
            throw new RuntimeException("Đã xảy ra lỗi khi gửi email", e);
        }
    }

}
