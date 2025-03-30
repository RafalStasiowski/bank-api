package com.rstasiowski.bank.factory;

import com.rstasiowski.bank.enums.BankAccountType;
import com.rstasiowski.bank.model.*;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class BankAccountFactory implements com.rstasiowski.bank.interfaces.BankAccountFactory {
    private final Map<BankAccountType, Supplier<BankAccount>> accountSuppliers;

    public BankAccountFactory() {
        accountSuppliers = new EnumMap<>(BankAccountType.class);
        accountSuppliers.put(BankAccountType.CHECKING, CheckingAccount::new);
        accountSuppliers.put(BankAccountType.SAVING, SavingAccount::new);
    }
    @Override
    public BankAccount createAccount(BankAccountType type, Money balance, User owner) {
        Supplier<BankAccount> supplier = accountSuppliers.get(type);
        validateSupplier(supplier, type);
        BankAccount account = supplier.get();
        account.initialize(balance, owner, type);
        return account;
    }

    private void validateSupplier(Supplier<BankAccount> supplier, BankAccountType type) {
        if (supplier == null) {
            throw new IllegalArgumentException("Account type is not supported: " + type);
        }
    }
}
