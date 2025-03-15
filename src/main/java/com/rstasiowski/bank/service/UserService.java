package com.rstasiowski.bank.service;

import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerUser(UserRegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = buildUser(registerDto);
        return userRepository.save(user);
    }

    private User buildUser(UserRegisterDto registerDto) {
        return User.builder()
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .roles(Set.of("USER"))
                .build();
    }
}
