package com.example.datn.service.config;//package com.example.datn.service.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    @Value("${jwt.signerKey}")
////    private String signerKey;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(requests ->
//                        requests
//                                .requestMatchers(HttpMethod.GET, "/trang-chu/**","/api/SPCT/**")
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated());
////        httpSecurity.oauth2ResourceServer(oauth2 ->
////                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));
//
//        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
//
//        return httpSecurity.build();
//    }
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        SecretKeySpec spec = new SecretKeySpec(signerKey.getBytes(),"HS512");
//        return  NimbusJwtDecoder.withSecretKey(spec).macAlgorithm(MacAlgorithm.HS512).build();
//    }
//}
