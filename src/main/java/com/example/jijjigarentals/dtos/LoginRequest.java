package com.example.jijjigarentals.dtos;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}