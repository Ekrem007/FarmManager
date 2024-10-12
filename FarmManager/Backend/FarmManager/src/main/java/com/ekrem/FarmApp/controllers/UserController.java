package com.ekrem.FarmApp.controllers;

import com.ekrem.FarmApp.entities.concretes.User;
import com.ekrem.FarmApp.entities.dtos.UserLogin;
import com.ekrem.FarmApp.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {
        if (userRepository.existsByUsername(userLogin.getUsername())) {
            User user = userRepository.findByUsername(userLogin.getUsername());
            String decodedPassword = new String(java.util.Base64.getDecoder().decode(user.getPassword()));
            if (decodedPassword.equals(userLogin.getPassword())) {
                return ResponseEntity.ok("Login successful!");
            }
            return ResponseEntity.ok("Login failed!");
        }
        return ResponseEntity.ok("Login failed!");
    }
}
