package com.example.datn.service.impl;

import com.example.datn.dto.SanPhamCtFiterDTO;
import com.example.datn.dto.SanPhamFiterDTO;
import com.example.datn.entity.SanPhamChiTietEntity;
import com.example.datn.entity.SanPhamEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

public class SpectificationSpct {
    public static Specification<SanPhamChiTietEntity> buildWhereCT(SanPhamCtFiterDTO form) {
        Specification<SanPhamChiTietEntity> where = Specification.where(null); // Khởi tạo là một Specification trống

        if (form != null) {
            if (form.getTrangThai() != null) {
                SpectificationSpct.CustomSpecification trangThaiSpec = new SpectificationSpct.CustomSpecification("trangThai", form.getTrangThai());
                where = where.and(trangThaiSpec); // Thêm điều kiện tìm kiếm vào Specification
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
            if (field.equals("trangThai") && value instanceof String) {
                return criteriaBuilder.equal(root.get(field), value);
            }

            return null;
        }
    }
}
