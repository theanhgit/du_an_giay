package com.example.datn.service.impl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class ShippingService {
    private static final Map<String, Double> shippingRates = new HashMap<>();

    static {
        shippingRates.put("Thành phố Hà Nội", 0.0); // Free shipping in Hanoi
        shippingRates.put("Tỉnh Bắc Ninh", 5000.0);
        shippingRates.put("Tỉnh Hà Nam", 10000.0);
        shippingRates.put("Tỉnh Hưng Yên", 15000.0);
        shippingRates.put("Tỉnh Vĩnh Phúc", 20000.0);
        shippingRates.put("Tỉnh Nam Định", 25000.0);
        shippingRates.put("Tỉnh Hải Dương", 31000.0);
        shippingRates.put("Thành phố Hải Phòng", 30000.0);
        shippingRates.put("Tỉnh Thái Bình", 35000.0);
        shippingRates.put("Tỉnh Bắc Giang", 40000.0);
        shippingRates.put("Tỉnh Hà Giang", 45000.0);
        shippingRates.put("Tỉnh Lào Cai", 50000.0);
        shippingRates.put("Tỉnh Tuyên Quang", 55000.0);
        shippingRates.put("Tỉnh Cao Bằng", 60000.0);
        shippingRates.put("Tỉnh Yên Bái", 65000.0);
        shippingRates.put("Tỉnh Bắc Kạn", 70000.0);
        shippingRates.put("Tỉnh Phú Thọ", 75000.0);
        shippingRates.put("Tỉnh Thái Nguyên", 80000.0);
        shippingRates.put("Tỉnh Lạng Sơn", 85000.0);
        shippingRates.put("Tỉnh Quảng Ninh", 90000.0);
        shippingRates.put("Tỉnh Hòa Bình", 95000.0);
        shippingRates.put("Tỉnh Sơn La", 100000.0);
        shippingRates.put("Tỉnh Điện Biên", 105000.0);
        shippingRates.put("Tỉnh Lai Châu", 110000.0);
        shippingRates.put("Tỉnh Ninh Bình", 115000.0);
        shippingRates.put("Tỉnh Thanh Hóa", 120000.0);
        shippingRates.put("Tỉnh Nghệ An", 125000.0);
        shippingRates.put("Tỉnh Hà Tĩnh", 130000.0);
        shippingRates.put("Tỉnh Quảng Bình", 135000.0);
        shippingRates.put("Tỉnh Quảng Trị", 140000.0);
        shippingRates.put("Tỉnh Thừa Thiên Huế", 145000.0);
        shippingRates.put("Tỉnh Đà Nẵng", 150000.0);
        shippingRates.put("Tỉnh Quảng Nam", 155000.0);
        shippingRates.put("Tỉnh Quảng Ngãi", 160000.0);
        shippingRates.put("Tỉnh Bình Định", 165000.0);
        shippingRates.put("Tỉnh Phú Yên", 170000.0);
        shippingRates.put("Tỉnh Khánh Hòa", 175000.0);
        shippingRates.put("Tỉnh Ninh Thuận", 180000.0);
        shippingRates.put("Tỉnh Bình Thuận", 185000.0);
        shippingRates.put("Tỉnh Kon Tum", 190000.0);
        shippingRates.put("Tỉnh Gia Lai", 195000.0);
        shippingRates.put("Tỉnh Đắk Lắk", 200000.0);
        shippingRates.put("Tỉnh Đắk Nông", 205000.0);
        shippingRates.put("Tỉnh Lâm Đồng", 210000.0);
        shippingRates.put("Tỉnh Bình Phước", 215000.0);
        shippingRates.put("Tỉnh Tây Ninh", 220000.0);
        shippingRates.put("Tỉnh Bình Dương", 225000.0);
        shippingRates.put("Tỉnh Đồng Nai", 230000.0);
        shippingRates.put("Tỉnh Bà Rịa - Vũng Tàu", 235000.0);
        shippingRates.put("Thành phố Hồ Chí Minh", 240000.0);
        shippingRates.put("Tỉnh Long An", 245000.0);
        shippingRates.put("Tỉnh Tiền Giang", 250000.0);
        shippingRates.put("Tỉnh Bến Tre", 255000.0);
        shippingRates.put("Tỉnh Trà Vinh", 260000.0);
        shippingRates.put("Tỉnh Vĩnh Long", 265000.0);
        shippingRates.put("Tỉnh Đồng Tháp", 270000.0);
        shippingRates.put("Tỉnh An Giang", 275000.0);
        shippingRates.put("Tỉnh Kiên Giang", 280000.0);
        shippingRates.put("Thành phố Cần Thơ", 285000.0);
        shippingRates.put("Tỉnh Hậu Giang", 290000.0);
        shippingRates.put("Tỉnh Sóc Trăng", 295000.0);
        shippingRates.put("Tỉnh Bạc Liêu", 300000.0);
        shippingRates.put("Tỉnh Cà Mau", 305000.0);
    }

    public double calculateShippingCost(String province) {
        return shippingRates.getOrDefault(province, 10000.0); // Default shipping cost if province not found
    }
}

