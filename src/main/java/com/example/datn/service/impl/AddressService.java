package com.example.datn.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    private final RestTemplate restTemplate;

    private final String PROVINCES_URL = "https://vapi.vnappmob.com/api/province/";
    private final String DISTRICTS_URL = "https://vapi.vnappmob.com/api/province/district/";
    private final String WARDS_URL = "https://vapi.vnappmob.com/api/province/ward/";

    @Autowired
    public AddressService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getProvinces() {
        try {
            // Lấy phản hồi dưới dạng chuỗi
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(PROVINCES_URL, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();
                if (responseBody != null && responseBody.trim().startsWith("{")) {
                    // Chuyển đổi chuỗi JSON thành đối tượng Map
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                    return (List<Map<String, Object>>) responseMap.get("results");
                } else {
                    throw new RuntimeException("Invalid JSON response");
                }
            } else {
                throw new RuntimeException("Failed to fetch provinces, status code: " + responseEntity.getStatusCodeValue());
            }
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("Provinces endpoint not found: " + ex.getMessage());
            } else {
                throw new RuntimeException("Failed to fetch provinces: " + ex.getMessage(), ex);
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Rest client exception: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected exception: " + ex.getMessage(), ex);
        }
    }

    public List<Map<String, Object>> getDistricts(String provinceId) {
        try {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(DISTRICTS_URL + provinceId, Map.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return (List<Map<String, Object>>) responseEntity.getBody().get("results");
            } else {
                throw new RuntimeException("Failed to fetch districts");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Districts not found for provinceId: " + provinceId);
        }
    }

    public List<Map<String, Object>> getWards(String districtId) {
        try {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(WARDS_URL + districtId, Map.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return (List<Map<String, Object>>) responseEntity.getBody().get("results");
            } else {
                throw new RuntimeException("Failed to fetch wards");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Wards not found for districtId: " + districtId);
        }
    }

}
