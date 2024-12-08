package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.service.BankAccountService;
import com.rstasiowski.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BankAccountServiceTest {
    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private UserService userService;

    @Test
    void testBankAccountCreate() {
        UserRegisterDto registerDto =  TestUtils.getTestUserRegisterDto();
        User user = userService.registerUser(registerDto);
        BankAccountRegisterDto bankAccountRegisterDto = new BankAccountRegisterDto();
        bankAccountRegisterDto.setUserEmail(registerDto.getEmail());
        bankAccountRegisterDto.setType(BankAccountType.CHECKING);
        bankAccountService.createBankAccount(bankAccountRegisterDto);
        assert !bankAccountService.findAllBankAccountsByUserId(user.getId()).isEmpty();
    }

}
