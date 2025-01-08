package com.rstasiowski.bank;

import com.rstasiowski.bank.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class JwtUtillTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testGenerate() {
        String username = "test";
        String token = jwtUtil.generateToken(username);
        assertNotNull(token);
        assertEquals(username, jwtUtil.extractUsername(token));
    }
}
