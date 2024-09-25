package com.example.datn.service.impl;

import com.example.datn.dto.SanPhamCtFiterDTO;
import com.example.datn.dto.UsersFiterDTO;
import com.example.datn.entity.SanPhamChiTietEntity;
import com.example.datn.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

public class SpectifileCationUser {
    public static Specification<UserEntity> buildWhereCT( UsersFiterDTO form) {
        Specification<UserEntity> where = Specification.where(null); // Khởi tạo là một Specification trống

        if (form != null) {
            if (form.getTrangThai() != null) {
                SpectifileCationUser.CustomSpecification trangThaiSpec = new SpectifileCationUser.CustomSpecification("trangThai", form.getTrangThai());
                where = where.and(trangThaiSpec); // Thêm điều kiện tìm kiếm vào Specification
            }
        }

        return where;
    }

    @RequiredArgsConstructor
    static class CustomSpecification implements Specification<UserEntity> {
        @NonNull
        private String field;
        @NonNull
        private Object value;

        @Override
        public Predicate toPredicate(
                Root<UserEntity> root,
                CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder
        ){
            if (field.equals("trangThai") && value instanceof Integer) {
                return criteriaBuilder.equal(root.get(field), value);
            }

            return null;
        }
    }
}
