package com.rstasiowski.bank.service;

import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private UserRepository userRepository;

    public List<BankAccount> findAllBankAccountsByUserId(Long userId) {
        return bankAccountRepository.findAllByUserId(userId);
    }

    public BankAccount createBankAccount(BankAccountRegisterDto bankAccountRegister) {
        User user = userRepository.findByEmail(bankAccountRegister.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with specific email does not exists"));
        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(generateAccountNumber())
                .user(user)
                .type(bankAccountRegister.getType())
                .balance(BigDecimal.ZERO)
                .build();
        return  bankAccountRepository.save(bankAccount);
    }

    public String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
