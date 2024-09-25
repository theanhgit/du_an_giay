package com.example.datn.service.impl;

import com.example.datn.dto.SanPhamChiTietFiterDTO;
import com.example.datn.entity.SanPhamChiTietEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationProduct {
    public static Specification<SanPhamChiTietEntity> buildWhere(SanPhamChiTietFiterDTO form) {
        Specification<SanPhamChiTietEntity> where = Specification.where(null); // Khởi tạo là một Specification trống

        if (form != null) {
            if (form.getMoTa() != null && !StringUtils.isEmpty(form.getMoTa().trim())) {
                String search = form.getMoTa().trim();
                CustomSpecification productNameSpec = new CustomSpecification("moTa", search);
                where = where.and(productNameSpec); // Thêm điều kiện tìm kiếm vào Specification
            }
        }

        return where;
    }


    @RequiredArgsConstructor
    static class CustomSpecification implements Specification<SanPhamChiTietEntity> {
        @NonNull
        private String field;
        @NonNull
        private Object value;
        @Override
        public Predicate toPredicate(
                Root<SanPhamChiTietEntity> root,
                CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder
        ){
            if(field.equals("moTa")){
                return  criteriaBuilder.like(root.get("moTa"), "%" + value.toString() + "%");
            }

            return null;

        }

    }
}

