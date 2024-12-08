package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.UserRepository;
import com.rstasiowski.bank.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testRegisterUser() {
        UserRegisterDto registerDto = TestUtils.getTestUserRegisterDto();
        User user = userService.registerUser(registerDto);
        assertNotNull(user);
    }

    @Test
    void shouldThrowExceptionWhenDuplicate() {
        UserRegisterDto registerDto = TestUtils.getTestUserRegisterDto();
        UserRegisterDto duplicateRegisterDto = TestUtils.getTestUserRegisterDto();
        userService.registerUser(registerDto);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(duplicateRegisterDto));
        assertEquals("Email already in use", exception.getMessage());
    }
}
