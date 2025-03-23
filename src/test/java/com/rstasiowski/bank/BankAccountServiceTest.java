package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.StandardBankAccountRepository;
import com.rstasiowski.bank.repository.UserRepository;
import com.rstasiowski.bank.service.BankAccountService;
import com.rstasiowski.bank.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

@SpringBootTest
@ActiveProfiles("test")
public class BankAccountServiceTest {
    @Autowired
    private TestUtils testUtils;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private StandardBankAccountRepository standardBankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        standardBankAccountRepository.deleteAll();;

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        standardBankAccountRepository.deleteAll();
    }

    @Test
    void testBankAccountCreate() {
        UserRegisterDto registerDto =  testUtils.getTestUserRegisterDto("1");
        User user = userService.registerUser(registerDto);
        BankAccountRegisterDto bankAccountRegisterDto = BankAccountRegisterDto.builder()
                .userEmail(registerDto.getEmail())
                .type(BankAccountType.CHECKING)
                .build();
        BankAccount account = bankAccountService.createBankAccount(bankAccountRegisterDto);
        user = userRepository.findById(user.getId()).orElseThrow();
        Pageable pageable = Pageable.unpaged();
        assert !bankAccountService.findAllBankAccountsByEmail(user.getEmail(), pageable).isEmpty();
        assert user.getAccounts().stream()
                .filter(userAccount -> Objects.equals(userAccount.getId(), account.getId()))
                .findFirst()
                .orElse(null) != null;

    }

}
