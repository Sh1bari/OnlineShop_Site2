package com.example.onlineshop_site2.models.dtos;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
