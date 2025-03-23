package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.UserRegisterDto;
import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.factory.DefaultBankAccountFactory;
import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.StandardBankAccountRepository;
import com.rstasiowski.bank.service.BankAccountService;
import com.rstasiowski.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestUtils {
    @Autowired
    private UserService userService;

    @Autowired
    private MoneyFactory moneyFactory;

    @Autowired
    private DefaultBankAccountFactory bankAccountFactory;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private StandardBankAccountRepository standardBankAccountRepository;

    public UserRegisterDto getTestUserRegisterDto(String idn) {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setEmail("test%s@test.com".formatted(idn));
        registerDto.setPassword("test123");
        registerDto.setFirstName("Test%s".formatted(idn));
        registerDto.setLastName("Testowy");
        return registerDto;
    }

    public BankAccountRegisterDto getBankAccountRegisterDto(String email) {
        return BankAccountRegisterDto.builder()
                .type(BankAccountType.CHECKING)
                .userEmail(email)
                .build();
    }

    public BankAccount createBankAccountWithUser(String idn, BigDecimal balance, String currencyCode) {
        User user = userService.registerUser(getTestUserRegisterDto(idn));
        Money accountBalance = moneyFactory.create(balance, currencyCode);
        BankAccount bankAccount = bankAccountFactory.createAccount(BankAccountType.CHECKING, accountBalance, user);
        bankAccountService.saveInAppropriateRepository(bankAccount);
        return bankAccount;
    }

}
