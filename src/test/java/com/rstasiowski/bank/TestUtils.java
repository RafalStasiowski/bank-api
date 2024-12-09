package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.model.BankAccount;

public class TestUtils {
    public static UserRegisterDto getTestUserRegisterDto(String idn) {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setEmail("test%s@test.com".formatted(idn));
        registerDto.setPassword("test123");
        registerDto.setFirstName("Test%s".formatted(idn));
        registerDto.setLastName("Testowy");
        return registerDto;
    }

    public static BankAccountRegisterDto getBankAccountRegisterDto(String email) {
        return BankAccountRegisterDto.builder()
                .type(BankAccountType.CHECKING)
                .userEmail(email)
                .build();
    }
}
