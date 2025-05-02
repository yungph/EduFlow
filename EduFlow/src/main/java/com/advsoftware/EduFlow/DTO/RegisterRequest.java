package com.advsoftware.EduFlow.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role; // e.g. ["ROLE_STUDENT"]
}

