package com.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLOutput;

public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin"));
        if(passwordEncoder.matches("admin","$2a$10$zw0DaBIuRiT6RMhKNZT32O33uH7IL7MlnZ/0DREpFU/zVTJsonIlm")){
            System.out.println("ture password");
        }
    }
}
