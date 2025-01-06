package com.lixin.firstSpring.common;


import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");    //1.设置访问源地址
        corsConfiguration.addAllowedHeader("*");    //2.设置访问源请求头
        corsConfiguration.addAllowedHeader("*");    //3.设置访问源请求方法
        source.registerCorsConfiguration("/**",corsConfiguration);  //4.对接口配置跨域设置
        return new CorsFilter(source);
    }
}
