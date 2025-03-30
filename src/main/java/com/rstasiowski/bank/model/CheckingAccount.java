package com.rstasiowski.bank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("CHECKING")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CheckingAccount extends BankAccount {
    public CheckingAccount(Money balance, User user) {
        super(balance, user);
    }
}
