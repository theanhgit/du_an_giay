package com.example.datn.restcontroller;

import com.example.datn.dto.PaymentRestDTO;
import com.example.datn.dto.TrainsactionDTO;
import com.example.datn.service.config.VnPageConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController

@RequestMapping("/api/vnp")
public class PagementController {

    @GetMapping("/create-pagement")
    public ResponseEntity<?> CreatePayment(@RequestParam String customerName, @RequestParam long totalAmount) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = totalAmount * 100;
        System.out.println(customerName);

        String vnp_TxnRef = VnPageConfig.getRandomNumber(8);
        String vnp_TmnCode = VnPageConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_Bill_FirstName", URLEncoder.encode(customerName, StandardCharsets.UTF_8.toString()));  // Encode customerName
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_ReturnUrl", VnPageConfig.vnp_ReturnUrl);
        String vnp_IpAddr = "127.0.0.1";
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnPageConfig.hmacSHA512(VnPageConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnPageConfig.vnp_PayUrl + "?" + queryUrl;

        // Print out the payment URL to check
        System.out.println("Payment URL: " + paymentUrl);

        PaymentRestDTO paymentRestDTO = new PaymentRestDTO();
        paymentRestDTO.setStatus("Ok");
        paymentRestDTO.setMessage("Successfully");
        paymentRestDTO.setURL(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentRestDTO);
    }

    @GetMapping("/payment-infor")
    public ResponseEntity<Map<String, String>> transaction(
            @RequestParam(value = "vnp_TxnRef") String txnRef,
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String orderInfo,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "customerName") String customerName // Add this parameter
    ) {
        Map<String, String> response = new HashMap<>();
        response.put("txnRef", txnRef);
        response.put("amount", amount);
        response.put("bankCode", bankCode);
        response.put("orderInfo", orderInfo);
        response.put("responseCode", responseCode);
        response.put("customerName", customerName);

        return ResponseEntity.ok(response);
    }
}
