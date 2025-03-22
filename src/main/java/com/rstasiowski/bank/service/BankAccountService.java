package com.rstasiowski.bank.service;

import com.rstasiowski.bank.config.AcceptedCurrenciesConfig;
import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import com.rstasiowski.bank.model.MoneyFactory;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private UserRepository userRepository;
    private MoneyFactory moneyFactory;

    public List<BankAccount> findAllBankAccountsByEmail(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return bankAccountRepository.findAllByUserId(user.getId(), pageable);
    }

    public BankAccount createBankAccount(BankAccountRegisterDto bankAccountRegister) {
        User user = userRepository.findByEmail(bankAccountRegister.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with specific email does not exists"));

        BankAccount bankAccount = BankAccount.builder()
                .accountNumber(generateAccountNumber())
                .user(user)
                .type(bankAccountRegister.getType())
                .balance(moneyFactory.createEmpty(AcceptedCurrenciesConfig.DEFAULT_CURRENCY))
                .build();
        bankAccountRepository.save(bankAccount);
        user.addBankAccount(bankAccount);
        return bankAccount;
    }

    public String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
