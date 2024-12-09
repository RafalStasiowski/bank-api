package com.rstasiowski.bank;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.dto.TransferDto;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Transfer;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.TransferRepository;
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
    private TransferService transferService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransferRepository transferRepository;

    @BeforeEach
    void setUp() {
        transferRepository.deleteAll();;
        bankAccountRepository.deleteAll();;
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        transferRepository.deleteAll();
        bankAccountRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testTransferRegister() {
        User user1 = userService.registerUser(TestUtils.getTestUserRegisterDto("1"));
        User user2 = userService.registerUser(TestUtils.getTestUserRegisterDto("2"));
        BankAccount bankAccount1 = bankAccountService
                .createBankAccount(TestUtils.getBankAccountRegisterDto(user1.getEmail()));
        BankAccount bankAccount2 = bankAccountService
                .createBankAccount(TestUtils.getBankAccountRegisterDto(user2.getEmail()));
        bankAccount1.setBalance(BigDecimal.valueOf(100));
        bankAccountRepository.save(bankAccount1);
        bankAccount2.setBalance(BigDecimal.valueOf(55.55));
        bankAccountRepository.save(bankAccount2);
        TransferDto transferDto = TransferDto.builder()
                .senderId(bankAccount1.getId())
                .receiverId(bankAccount2.getId())
                .amount(BigDecimal.valueOf(100))
                .description("Transfer 1")
                .build();
        Transfer transfer1 = transferService.transfer(transferDto);
        bankAccount1 = bankAccountRepository.findById(bankAccount1.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
        bankAccount2 = bankAccountRepository.findById(bankAccount2.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
        assertNotNull(transfer1);
        assertEquals(transfer1.getAccountFrom().getId(), bankAccount1.getId());
        assertEquals(transfer1.getAccountTo().getId(), bankAccount2.getId());
        assertEquals(transfer1.getAmount(), BigDecimal.valueOf(100));
        assert bankAccount2.getBalance().equals(BigDecimal.valueOf(155.55));
        assert bankAccount1.getBalance().compareTo(BigDecimal.ZERO) == 0;
    }

}
