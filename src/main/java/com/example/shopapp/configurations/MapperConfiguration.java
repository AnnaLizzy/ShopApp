package com.example.shopapp.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    // tạo một bean của ModelMapper trong một lớp
    // cấu hình Java của Spring hoặc bằng cách
    // sử dụng annotation @Bean trực tiếp trong một lớp cấu hình.
}
