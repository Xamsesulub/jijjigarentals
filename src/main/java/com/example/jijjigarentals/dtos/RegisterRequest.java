package com.example.jijjigarentals.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role; // leietaker eller utleier
}