package com.advsoftware.EduFlow.controllers;

import com.advsoftware.EduFlow.DTO.LoginRequest;
import com.advsoftware.EduFlow.DTO.RegisterRequest;
import com.advsoftware.EduFlow.models.User;
import com.advsoftware.EduFlow.service.impel.AuthService;
import com.advsoftware.EduFlow.service.impel.UserServiceImpel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserServiceImpel userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User registered = authService.register(request);
        return "success";
    }

//    @PostMapping("/api/auth/new-login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        try {
//            User user = authService.login(request);
//
//
//            // Optional JWT generation
//            // String token = jwtUtil.generateToken(user);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Login successful",
//                    "name", user.getName(),
//                    "email", user.getEmail()
//                    // , "token", token // if JWT is used
//            ));
//
//        } catch (RuntimeException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("message", ex.getMessage()));
//        }
//    }






}

