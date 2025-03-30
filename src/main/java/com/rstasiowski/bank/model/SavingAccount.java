package com.rstasiowski.bank.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("SAVINGS")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SavingAccount extends BankAccount {
    public SavingAccount(Money balance, User user) {
        super(balance, user);
    }

}
