package com.rstasiowski.bank.controller;

import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/v1/api/users")
public class UserController {
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto registerDto) {
        userService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
