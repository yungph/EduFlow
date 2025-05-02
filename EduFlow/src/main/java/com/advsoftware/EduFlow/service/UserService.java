package com.advsoftware.EduFlow.service;

import com.advsoftware.EduFlow.models.User;

import java.util.List;

public interface UserService {

    User getUserById(String id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    void makeUserAdmin(String id);

    List<User> getAllUsersByRole(String role);
}
