package com.rstasiowski.bank.controller;

import com.rstasiowski.bank.dto.AuthRequest;
import com.rstasiowski.bank.dto.AuthResponse;
import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.service.UserService;
import com.rstasiowski.bank.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Invalid login credentials");
        }
        String token = jwtUtil.generateToken(authRequest.getUsername());
        return new AuthResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto registerDto) {
        userService.registerUser(registerDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
