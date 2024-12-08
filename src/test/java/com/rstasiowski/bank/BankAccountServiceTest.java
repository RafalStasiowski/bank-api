package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.UserRepository;
import com.rstasiowski.bank.service.BankAccountService;
import com.rstasiowski.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class BankAccountServiceTest {
    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testBankAccountCreate() {
        UserRegisterDto registerDto =  TestUtils.getTestUserRegisterDto();
        User user = userService.registerUser(registerDto);
        BankAccountRegisterDto bankAccountRegisterDto = new BankAccountRegisterDto();
        bankAccountRegisterDto.setUserEmail(registerDto.getEmail());
        bankAccountRegisterDto.setType(BankAccountType.CHECKING);
        BankAccount account = bankAccountService.createBankAccount(bankAccountRegisterDto);
        user = userRepository.findById(user.getId()).orElseThrow();
        assert !bankAccountService.findAllBankAccountsByUserId(user.getId()).isEmpty();
        assert user.getAccounts().stream()
                .filter(userAccount -> Objects.equals(userAccount.getId(), account.getId()))
                .findFirst()
                .orElse(null) != null;

    }

}
