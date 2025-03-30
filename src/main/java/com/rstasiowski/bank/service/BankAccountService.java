package com.rstasiowski.bank.service;

import com.rstasiowski.bank.config.AcceptedCurrenciesConfig;
import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.factory.BankAccountFactory;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.BankAccountRepository;
import com.rstasiowski.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankAccountService {
    private BankAccountRepository bankAccountRepository;
    private UserRepository userRepository;
    private MoneyFactory moneyFactory;
    private BankAccountFactory bankAccountFactory;

    public List<BankAccount> findAllBankAccountsByEmail(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return bankAccountRepository.findAllByUserId(user.getId(), pageable);
    }

    public BankAccount createBankAccount(BankAccountRegisterDto bankAccountRegister) {
        User user = getUser(bankAccountRegister.getUserEmail());
        BankAccount bankAccount = bankAccountFactory.createAccount(
                bankAccountRegister.getType(),
                moneyFactory.createEmpty(AcceptedCurrenciesConfig.DEFAULT_CURRENCY),
                user
        );
        bankAccount = bankAccountRepository.save(bankAccount);
        user.addBankAccount(bankAccount);
        return bankAccount;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with specific email does not exists"));
    }

}
