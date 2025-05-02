package com.advsoftware.EduFlow.service.impel;

import com.advsoftware.EduFlow.DTO.LoginRequest;
import com.advsoftware.EduFlow.DTO.RegisterRequest;
import com.advsoftware.EduFlow.config.HashPassword;
import com.advsoftware.EduFlow.models.Role;
import com.advsoftware.EduFlow.models.User;
import com.advsoftware.EduFlow.repo.RoleRepo;
import com.advsoftware.EduFlow.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepo userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    public String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


    public User register(RegisterRequest request) {
        User user = new User();
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setUser_id(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));

        // Assign roles based on input (e.g., "STUDENT" or "INSTRUCTOR")
        Set<Role> userRoles = new HashSet<>();

        String roleInput = request.getRole(); // Make sure this is a single string like "STUDENT" or "INSTRUCTOR"
        String roleName = "ROLE_" + roleInput.toUpperCase(); // e.g., ROLE_STUDENT

        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        userRoles.add(role);

        user.setRoles(userRoles);

        return userRepository.save(user);
    }


//    public User login(LoginRequest request) {
//        User user = userRepository.findByEmail(request.getEmail());
//        try {
//            if (user.getPassword().equals(hashPassword(request.getPassword()))) {
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                request.getEmail(),
//                                request.getPassword()
//                        )
//                );
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                return user;
//            }
//        }
//        catch (BadCredentialsException ex) {
//            throw new RuntimeException("Invalid email or password");
//            }
//        return user;
//    }

    public boolean login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            return false;
        }

        try {
            if (user.getPassword().equals(hashPassword(request.getPassword()))) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
        } catch (BadCredentialsException ex) {
            return false;
        }

        return false;
    }

}

