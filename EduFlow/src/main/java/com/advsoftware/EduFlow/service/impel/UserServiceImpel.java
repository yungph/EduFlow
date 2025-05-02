package com.advsoftware.EduFlow.service.impel;

import com.advsoftware.EduFlow.models.User;
import com.advsoftware.EduFlow.repo.UserRepo;
import com.advsoftware.EduFlow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpel implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public User getUserById(String id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void makeUserAdmin(String id) {
        userRepo.makeUserRoleAdmin(id);
    }

    @Override
    public List<User> getAllUsersByRole(String role) {
        role = role.toLowerCase();
        if(role.equals("instructor")){
            role = "ROLE_INSTRUCTOR";
            return userRepo.findByRoleName(role);
        }
        else if (role.equals("student")) {
            role = "ROLE_STUDENT";
            return userRepo.findByRoleName(role);
        }
        return null;


    }
}
