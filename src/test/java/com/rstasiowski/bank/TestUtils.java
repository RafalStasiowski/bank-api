package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.UserRegisterDto;

public class TestUtils {
    public static UserRegisterDto getTestUserRegisterDto() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setEmail("test@test.com");
        registerDto.setPassword("test123");
        registerDto.setFirstName("Test");
        registerDto.setLastName("Testowy");
        return registerDto;
    }
}
