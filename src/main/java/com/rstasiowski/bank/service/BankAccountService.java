package com.rstasiowski.bank.service;

import com.rstasiowski.bank.config.AcceptedCurrenciesConfig;
import com.rstasiowski.bank.dto.BankAccountRegisterDto;
import com.rstasiowski.bank.factory.DefaultBankAccountFactory;
import com.rstasiowski.bank.interfaces.BankAccount;
import com.rstasiowski.bank.factory.MoneyFactory;
import com.rstasiowski.bank.model.StandardBankAccount;
import com.rstasiowski.bank.model.User;
import com.rstasiowski.bank.repository.StandardBankAccountRepository;
import com.rstasiowski.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BankAccountService {
    private StandardBankAccountRepository standardBankAccountRepository;
    private UserRepository userRepository;
    private MoneyFactory moneyFactory;
    private DefaultBankAccountFactory bankAccountFactory;

    public List<StandardBankAccount> findAllBankAccountsByEmail(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return standardBankAccountRepository.findAllByUserId(user.getId(), pageable);
    }

    public BankAccount createBankAccount(BankAccountRegisterDto bankAccountRegister) {
        User user = getUser(bankAccountRegister.getUserEmail());
        BankAccount bankAccount = bankAccountFactory.createAccount(
                bankAccountRegister.getType(),
                moneyFactory.createEmpty(AcceptedCurrenciesConfig.DEFAULT_CURRENCY),
                user
        );
        bankAccount = saveInAppropriateRepository(bankAccount);
        user.addBankAccount(bankAccount);
        return bankAccount;
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with specific email does not exists"));
    }

    public BankAccount saveInAppropriateRepository(BankAccount bankAccount) {
        if (bankAccount instanceof StandardBankAccount) {
            return standardBankAccountRepository.save((StandardBankAccount) bankAccount);
        } else {
            throw new IllegalArgumentException("Unsupported bank account type " + bankAccount.getClass());
        }
    }
}
