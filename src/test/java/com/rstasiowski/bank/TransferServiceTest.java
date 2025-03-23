package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.impl.DailyLimitRule;
import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.interfaces.Transfer;
import com.rstasiowski.bank.model.StandardTransfer;
import com.rstasiowski.bank.repository.StandardBankAccountRepository;
import com.rstasiowski.bank.repository.StandardTransferRepository;
import com.rstasiowski.bank.repository.UserRepository;
import com.rstasiowski.bank.service.BankAccountService;
import com.rstasiowski.bank.service.TransferService;
import com.rstasiowski.bank.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TransferServiceTest {
    @Autowired
    private TestUtils testUtils;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StandardBankAccountRepository standardBankAccountRepository;

    @Autowired
    private StandardTransferRepository standardTransferRepository;

    @Autowired
    private MoneyFactory moneyFactory;

    @BeforeEach
    void setUp() {
        standardTransferRepository.deleteAll();;
        standardBankAccountRepository.deleteAll();;
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        standardTransferRepository.deleteAll();
        standardBankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testTransferRegister() {
        BankAccount bankAccount1 = testUtils.createBankAccountWithUser("1", BigDecimal.valueOf(100), "PLN");
        BankAccount bankAccount2 = testUtils.createBankAccountWithUser("2", BigDecimal.valueOf(55.55), "PLN");
        TransferDto transferDto = TransferDto.builder()
                .senderId(bankAccount1.getId())
                .receiverId(bankAccount2.getId())
                .amount(BigDecimal.valueOf(100))
                .currencyCode("PLN")
                .description("Transfer 1")
                .build();
        StandardTransfer transfer1 = (StandardTransfer) transferService.transfer(transferDto);
        bankAccount1 = standardBankAccountRepository.findById(bankAccount1.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
        bankAccount2 = standardBankAccountRepository.findById(bankAccount2.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
        assertNotNull(transfer1);
        assertEquals(transfer1.getAccountFrom().getId(), bankAccount1.getId());
        assertEquals(transfer1.getAccountTo().getId(), bankAccount2.getId());
        assertEquals(transfer1.getAmount(), moneyFactory.create(BigDecimal.valueOf(100), "PLN"));
        assert bankAccount2.getBalance().equals(moneyFactory.create(BigDecimal.valueOf(155.55), "PLN"));
        assert bankAccount1.getBalance().compareTo(moneyFactory.create(BigDecimal.ZERO, "PLN")) == 0;
    }

    @Test
    void dailyLimitRuleTest() {
        BigDecimal amountOverLimit = DailyLimitRule.MAX_DAILY_LIMIT.getAmount().add(BigDecimal.valueOf(100));
        BankAccount bankAccount1 = testUtils.createBankAccountWithUser("1", amountOverLimit, "PLN");
        BankAccount bankAccount2 = testUtils.createBankAccountWithUser("2", BigDecimal.ZERO, "PLN");
        TransferDto transferDto = TransferDto.builder()
                .senderId(bankAccount1.getId())
                .receiverId(bankAccount2.getId())
                .amount(amountOverLimit)
                .currencyCode("PLN")
                .description("Transfer 1")
                .build();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(transferDto));
        assertEquals("Transfer exceeds daily limit", exception.getMessage());
    }

    @Test
    void enoughFundsRule() {
        BankAccount bankAccount1 = testUtils.createBankAccountWithUser("1", BigDecimal.valueOf(100), "PLN");
        BankAccount bankAccount2 = testUtils.createBankAccountWithUser("2", BigDecimal.ZERO, "PLN");
        TransferDto transferDto = TransferDto.builder()
                .senderId(bankAccount1.getId())
                .receiverId(bankAccount2.getId())
                .amount(BigDecimal.valueOf(110))
                .currencyCode("PLN")
                .description("Transfer 1")
                .build();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(transferDto));
        assertEquals("Not enough funds to transfer", exception.getMessage());
    }

}
