package com.example.datn.restcontroller;

import com.example.datn.dto.ShippingCostResponse;
import com.example.datn.service.impl.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShippingController {
    private final ShippingService shippingService;

    @GetMapping("/calculate-shipping")
    public ResponseEntity<ShippingCostResponse> calculateShipping(
            @RequestParam String province
    ) {

        double shippingCost = shippingService.calculateShippingCost(province);

        ShippingCostResponse response = new ShippingCostResponse(shippingCost);
        return ResponseEntity.ok(response);
    }
}
