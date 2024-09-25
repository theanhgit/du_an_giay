package com.example.datn.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImagesUploadController {

  @RequestMapping(value = "getimage/{anhsanPham}", method = RequestMethod.GET)
  @ResponseBody
  public ResponseEntity<ByteArrayResource> getImage(@PathVariable("anhsanPham") String photo) {
    System.out.println("Tên file nhận được: " + photo);

    if (photo != null && !photo.isEmpty()) {
      try {
        Path path = Paths.get("src/main/resources/img/" + photo);
        System.out.println("Đường dẫn đầy đủ: " + path.toAbsolutePath());

        if (Files.exists(path)) {
          byte[] buffer = Files.readAllBytes(path);
          ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
          return ResponseEntity.ok()
                  .contentLength(buffer.length)
                  .contentType(MediaType.parseMediaType("image/png"))
                  .body(byteArrayResource);
        } else {
          System.out.println("File không tồn tại: " + path.toAbsolutePath());
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
      } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      }
    } else {
      System.out.println("Lỗi: Tên file không hợp lệ");
      return ResponseEntity.badRequest().build();
    }
  }

}
