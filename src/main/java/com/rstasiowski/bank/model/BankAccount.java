package com.rstasiowski.bank.model;

import com.rstasiowski.bank.enums.BankAccountType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class BankAccount {
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
}
