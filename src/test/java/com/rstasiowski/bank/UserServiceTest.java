package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testRegisterUser() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setEmail("test@test.com");
        registerDto.setPassword("test123");
        registerDto.setFirstName("Test");
        registerDto.setLastName("Testowy");

        User user = userService.registerUser(registerDto);
        assert  user != null;
    }
}
