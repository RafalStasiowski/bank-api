package com.rstasiowski.bank.service;

import com.rstasiowski.bank.interfaces.TransferValidationRule;
import com.rstasiowski.bank.model.BankAccount;
import com.rstasiowski.bank.model.Money;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransferValidationService {
    private final List<TransferValidationRule> validationRules;

    public void validate(BankAccount sender, Money amount) {
        for (TransferValidationRule rule : validationRules) {
            rule.validate(sender, amount);
        }
    }
}
