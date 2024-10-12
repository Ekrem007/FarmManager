package com.ekrem.FarmApp.utils;

import com.ekrem.FarmApp.entities.concretes.User;
import com.ekrem.FarmApp.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class UserDataInit {

    private final UserRepository userRepository;

    public UserDataInit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        try {
            if (!userRepository.existsByUsername("admin")) {
                String password = "admins";
                String encryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());

                User user = new User();
                user.setName("Ekrem");
                user.setSurname("Uğur");
                user.setUsername("admin");
                user.setPassword(encryptedPassword);

                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
