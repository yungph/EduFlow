package com.advsoftware.EduFlow.controllers;

import com.advsoftware.EduFlow.models.User;
import com.advsoftware.EduFlow.service.impel.UserServiceImpel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpel userService;

    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{role}")
    public ResponseEntity<?> getUserByRole(@PathVariable String role) {
        return ResponseEntity.ok().body(userService.getAllUsersByRole(role));
    }

    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }



    @PutMapping("/admin/make-user-admin/{id}")
    public ResponseEntity<?> makeUserAdmin(@PathVariable String id) {
        userService.makeUserAdmin(id);
        return ResponseEntity.ok().build();
    }

}
