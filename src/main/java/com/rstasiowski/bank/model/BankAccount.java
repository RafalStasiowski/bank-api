package com.rstasiowski.bank.model;

import com.rstasiowski.bank.enums.BankAccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    private String accountNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BankAccountType type;
    private BigDecimal balance;
}
