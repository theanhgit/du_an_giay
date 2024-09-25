package com.example.datn.service.config;

import com.example.datn.Repository.UserRepository;
import com.example.datn.service.impl.UserStatusFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class WebConfig {

    @Bean(name = "customUserStatusFilter")
    public FilterRegistrationBean<UserStatusFilter> userStatusFilter(UserRepository userRepository) {
        UserStatusFilter userStatusFilter = new UserStatusFilter(userRepository);
        FilterRegistrationBean<UserStatusFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(userStatusFilter);
        registrationBean.addUrlPatterns("/*"); // Áp dụng cho tất cả các URL
        return registrationBean;
    }

}