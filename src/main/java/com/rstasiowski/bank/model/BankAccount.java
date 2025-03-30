package com.rstasiowski.bank.model;


import com.rstasiowski.bank.enums.BankAccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String accountNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private BankAccountType type;
    @Column(nullable = false)
    @Embedded
    private Money balance;

    protected BankAccount(Money initialBalance, User owner) {
        this.balance = initialBalance;
        this.user = owner;
        this.accountNumber = generateAccountNumber();
    }

    public User getOwner() {
        return user;
    }


    public void initialize(Money balance, User owner, BankAccountType type) {
        this.balance = balance;
        this.user = owner;
        this.type = type;
    }

    public void deposit(Money amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        this.balance = this.balance.substract(amount);
    }

    public String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

}
